package com.gft.digitalbank.exchange.solution.order.processors;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gft.digitalbank.exchange.model.OrderDetails;
import com.gft.digitalbank.exchange.model.Transaction;
import com.gft.digitalbank.exchange.model.orders.PositionOrder;
import com.gft.digitalbank.exchange.model.orders.Side;
import com.gft.digitalbank.exchange.solution.order.ProcessingState;
import com.gft.digitalbank.exchange.solution.order.TransactionCounter;

public abstract class AbstractPositionOrderProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPositionOrderProcessor.class);

	private static final String format(PositionOrder positionOrder) {
		StringBuilder sb = new StringBuilder();
		sb.append(positionOrder.getSide() == Side.BUY ? "BUY " : "SELL ");
		OrderDetails details = positionOrder.getDetails();
		sb.append(details.getAmount());
		sb.append(" ");
		sb.append(details.getPrice());
		sb.append("$");
		return sb.toString();
	}

	public final void handlePositionOrder(PositionOrder mainOrder, ProcessingState processingState) {
		LOGGER.debug(format(mainOrder));
		SortedSet<PositionOrder> completingOrders = findCompletingOrders(mainOrder, processingState);
		PositionOrder remainings = countRemainings(mainOrder, completingOrders);
		List<Transaction> transactions = performTransactions(mainOrder, completingOrders,
				processingState.getTransactionCounter());
		processingState.getTransactions().addAll(transactions);
		handleAwaitingOrders(mainOrder, processingState, completingOrders);
		if (remainings != null)
			if (remainings.getSide() == Side.BUY)
				processingState.getAwaitingBuyOffers().add(remainings);
			else
				processingState.getAwaitingSellOffers().add(remainings);
	}

	protected final PositionOrder countRemainings(PositionOrder mainOrder, SortedSet<PositionOrder> completingOrders) {
		if (!completingOrders.isEmpty()) {
			int amountOfCompletingOrders = completingOrders.stream().mapToInt(o -> o.getDetails().getAmount()).sum();
			int amountOfMainOrder = mainOrder.getDetails().getAmount();
			if (amountOfCompletingOrders > amountOfMainOrder) {
				int difference = amountOfCompletingOrders - amountOfMainOrder;
				PositionOrder lastCompletingOrder = completingOrders.last();
				PositionOrder remainings = decreaseAmount(lastCompletingOrder,
						lastCompletingOrder.getDetails().getAmount() - difference);
				LOGGER.debug("remainings: " + format(remainings));
				return remainings;
			} else if (amountOfMainOrder > amountOfCompletingOrders) {
				PositionOrder remainings = decreaseAmount(mainOrder, amountOfCompletingOrders);
				LOGGER.debug("remainings: " + remainings);
				return remainings;
			}
			LOGGER.debug("no remainings");
			return null;
		}
		LOGGER.debug("no match found");
		return mainOrder;
	}

	protected abstract Transaction createTransaction(PositionOrder mainOrder, TransactionCounter transactionCounter,
			PositionOrder completingOrder, int amount);

	protected final PositionOrder decreaseAmount(PositionOrder base, int amount) {
		OrderDetails details = new OrderDetails(base.getDetails().getAmount() - amount, base.getDetails().getPrice());
		return PositionOrder.builder().timestamp(base.getTimestamp()).id(base.getId()).broker(base.getBroker())
				.client(base.getClient()).product(base.getProduct()).side(base.getSide()).details(details).build();
	}

	protected abstract PositionOrder findCompletingOrder(PositionOrder mainOrder, ProcessingState processingState);

	protected final SortedSet<PositionOrder> findCompletingOrders(PositionOrder mainOrder,
			ProcessingState processingState) {
		int amountOfMainOrder = mainOrder.getDetails().getAmount();
		int amountOfCompletingOrders = 0;
		SortedSet<PositionOrder> completingOrders = new TreeSet<>(getOpositeComparator());
		PositionOrder completingOrder = findCompletingOrder(mainOrder, processingState);
		while ((amountOfMainOrder > amountOfCompletingOrders) && (completingOrder != null)) {
			int amount = completingOrder.getDetails().getAmount();
			amountOfCompletingOrders += amount;
			completingOrders.add(completingOrder);
			handleRemovalOfUsedOrder(processingState, completingOrder);
			completingOrder = findCompletingOrder(mainOrder, processingState);
		}
		return completingOrders;
	}

	protected abstract Comparator<PositionOrder> getOpositeComparator();

	protected abstract void handleAwaitingOrders(PositionOrder mainOrder, ProcessingState processingState,
			SortedSet<PositionOrder> completingOrders);

	protected abstract void handleRemovalOfUsedOrder(ProcessingState processingState, PositionOrder completingOrder);

	protected final List<Transaction> performTransactions(PositionOrder mainOrder,
			SortedSet<PositionOrder> completingOrders, TransactionCounter transactionCounter) {
		int amountOfCompletingOrders = mainOrder.getDetails().getAmount();
		List<Transaction> transactions = new ArrayList<>();
		for (PositionOrder completingOrder : completingOrders) {
			int amount = Math.min(amountOfCompletingOrders, completingOrder.getDetails().getAmount());
			Transaction transaction = createTransaction(mainOrder, transactionCounter, completingOrder, amount);
			amountOfCompletingOrders -= amount;
			LOGGER.debug(transaction.toString());
			transactions.add(transaction);
		}
		return transactions;
	}

}
