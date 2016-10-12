package com.gft.digitalbank.exchange.solution.order.processors;

import com.gft.digitalbank.exchange.model.orders.BrokerMessage;
import com.gft.digitalbank.exchange.model.orders.ModificationOrder;
import com.gft.digitalbank.exchange.model.orders.PositionOrder;
import com.gft.digitalbank.exchange.model.orders.Side;
import com.gft.digitalbank.exchange.solution.order.ProcessingState;
import com.gft.digitalbank.exchange.solution.order.dto.ExtendedModificationOrder;

public class ModifyOrderProcessor implements SingleOrderProcessor {

	@Override
	public void processOrder(BrokerMessage message, ProcessingState processingState) {
		ExtendedModificationOrder modificationOrder = (ExtendedModificationOrder) message;
		Side side = modificationOrder.getPositionOrder().getSide();
		if (side.equals(Side.BUY)) {
			PositionOrder positionOrder = processingState.getAwaitingBuyOffers().stream()
					.filter(b -> b.getId() == modificationOrder.getModifiedOrderId()).findFirst().get();
			PositionOrder build = modify(positionOrder, modificationOrder);
			processingState.getAwaitingBuyOffers().remove(positionOrder);
			new BuyOrderProcessor().handlePositionOrder(build, processingState);
		} else {
			PositionOrder positionOrder = processingState.getAwaitingSellOffers().stream()
					.filter(b -> b.getId() == modificationOrder.getModifiedOrderId()).findFirst().get();
			PositionOrder build = modify(positionOrder, modificationOrder);
			processingState.getAwaitingSellOffers().remove(positionOrder);
			new SellOrderProcessor().handlePositionOrder(build, processingState);
		}
	}

	public PositionOrder modify(PositionOrder base, ModificationOrder modificationOrder) {
		return PositionOrder.builder().timestamp(modificationOrder.getTimestamp()).id(base.getId())
				.broker(base.getBroker()).client(base.getClient()).product(base.getProduct()).side(base.getSide())
				.details(modificationOrder.getDetails()).build();
	}

}
