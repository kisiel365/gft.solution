package com.gft.digitalbank.exchange.solution.order.processors;

import java.util.SortedSet;

import com.gft.digitalbank.exchange.model.Transaction;
import com.gft.digitalbank.exchange.model.orders.PositionOrder;
import com.gft.digitalbank.exchange.solution.order.ProcessingState;
import com.gft.digitalbank.exchange.solution.order.TransactionCounter;
import com.gft.digitalbank.exchange.solution.order.comparators.SellOrderComparator;

public class BuyOrderProcessor extends AbstractPositionOrderProcessor {

	private PositionOrder findMatchingSeller(PositionOrder positionOrder, SortedSet<PositionOrder> treeSet) {
		return treeSet.stream().filter(m -> m.getDetails().getPrice() <= positionOrder.getDetails().getPrice())
				.sorted(getOpositeComparator()).findFirst().orElse(null);
	}

	@Override
	protected Transaction createTransaction(PositionOrder mainOrder, TransactionCounter transactionCounter,
			PositionOrder completingOrder, int amount) {
		return Transaction.builder().id(transactionCounter.getTransactionId()).amount(amount)
				.price(completingOrder.getDetails().getPrice()).product(completingOrder.getProduct())
				.brokerBuy(mainOrder.getBroker()).brokerSell(completingOrder.getBroker())
				.clientBuy(mainOrder.getClient()).clientSell(completingOrder.getClient()).build();
	}

	@Override
	protected final PositionOrder findCompletingOrder(PositionOrder mainOrder, ProcessingState processingState) {
		return findMatchingSeller(mainOrder, processingState.getAwaitingSellOffers());
	}

	@Override
	protected final SellOrderComparator getOpositeComparator() {
		return new SellOrderComparator();
	}

	@Override
	protected final void handleAwaitingOrders(PositionOrder mainOrder, ProcessingState processingState,
			SortedSet<PositionOrder> completingOrders) {
		processingState.getAwaitingSellOffers().removeAll(completingOrders);
		processingState.getAwaitingBuyOffers().remove(mainOrder);
	}

	@Override
	protected final void handleRemovalOfUsedOrder(ProcessingState processingState, PositionOrder completingOrder) {
		processingState.getAwaitingSellOffers().remove(completingOrder);
	}

}
