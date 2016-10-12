package com.gft.digitalbank.exchange.solution.order.processors;

import java.util.Comparator;
import java.util.SortedSet;

import com.gft.digitalbank.exchange.model.Transaction;
import com.gft.digitalbank.exchange.model.orders.PositionOrder;
import com.gft.digitalbank.exchange.solution.order.ProcessingState;
import com.gft.digitalbank.exchange.solution.order.TransactionCounter;
import com.gft.digitalbank.exchange.solution.order.comparators.BuyOrderComparator;

public final class SellOrderProcessor extends AbstractPositionOrderProcessor {

	private PositionOrder findMatchingBuyer(PositionOrder positionOrder, SortedSet<PositionOrder> treeSet) {
		return treeSet.stream().filter(m -> m.getDetails().getPrice() >= positionOrder.getDetails().getPrice())
				.sorted(getOpositeComparator()).findFirst().orElse(null);
	}

	@Override
	protected Transaction createTransaction(PositionOrder mainOrder, TransactionCounter transactionCounter,
			PositionOrder completingOrder, int amount) {
		return Transaction.builder().id(transactionCounter.getTransactionId()).amount(amount)
				.price(completingOrder.getDetails().getPrice()).product(completingOrder.getProduct())
				.brokerBuy(completingOrder.getBroker()).brokerSell(mainOrder.getBroker())
				.clientBuy(completingOrder.getClient()).clientSell(mainOrder.getClient()).build();
	}

	@Override
	protected final PositionOrder findCompletingOrder(PositionOrder mainOrder, ProcessingState processingState) {
		return findMatchingBuyer(mainOrder, processingState.getAwaitingBuyOffers());
	}

	@Override
	protected final Comparator<PositionOrder> getOpositeComparator() {
		return new BuyOrderComparator();
	}

	@Override
	protected final void handleAwaitingOrders(PositionOrder mainOrder, ProcessingState processingState,
			SortedSet<PositionOrder> completingOrders) {
		processingState.getAwaitingBuyOffers().removeAll(completingOrders);
		processingState.getAwaitingSellOffers().remove(mainOrder);
	}

	@Override
	protected final void handleRemovalOfUsedOrder(ProcessingState processingState, PositionOrder completingOrder) {
		processingState.getAwaitingBuyOffers().remove(completingOrder);
	}

}
