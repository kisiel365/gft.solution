package com.gft.digitalbank.exchange.solution.order.processors;

import com.gft.digitalbank.exchange.model.orders.BrokerMessage;
import com.gft.digitalbank.exchange.model.orders.PositionOrder;
import com.gft.digitalbank.exchange.model.orders.Side;
import com.gft.digitalbank.exchange.solution.order.ProcessingState;
import com.gft.digitalbank.exchange.solution.order.dto.ExtendedCancellationOrder;

public class CancellationOrderProcessor implements SingleOrderProcessor {

	@Override
	public void processOrder(BrokerMessage message, ProcessingState processingState) {
		ExtendedCancellationOrder cancellationOrder = (ExtendedCancellationOrder) message;
		Side side = cancellationOrder.getPositionOrder().getSide();
		if (side.equals(Side.BUY)) {
			PositionOrder positionOrder = processingState.getAwaitingBuyOffers().stream()
					.filter(b -> b.getId() == cancellationOrder.getCancelledOrderId()).findFirst().get();
			processingState.getAwaitingBuyOffers().remove(positionOrder);
		} else {
			PositionOrder positionOrder = processingState.getAwaitingSellOffers().stream()
					.filter(b -> b.getId() == cancellationOrder.getCancelledOrderId()).findFirst().get();
			processingState.getAwaitingSellOffers().remove(positionOrder);
		}
	}

}
