package com.gft.digitalbank.exchange.solution.order.processors;

import java.util.EnumMap;

import com.gft.digitalbank.exchange.model.orders.BrokerMessage;
import com.gft.digitalbank.exchange.model.orders.PositionOrder;
import com.gft.digitalbank.exchange.model.orders.Side;
import com.gft.digitalbank.exchange.solution.order.ProcessingState;

public class PositionOrderProcessor implements SingleOrderProcessor {

	private EnumMap<Side, AbstractPositionOrderProcessor> concreteProcessors = new EnumMap<>(Side.class);

	public PositionOrderProcessor() {
		concreteProcessors.put(Side.BUY, new BuyOrderProcessor());
		concreteProcessors.put(Side.SELL, new SellOrderProcessor());
	}

	@Override
	public void processOrder(BrokerMessage message, ProcessingState processingState) {
		PositionOrder positionOrder = (PositionOrder) message;
		AbstractPositionOrderProcessor concreteProcessor = concreteProcessors.get(positionOrder.getSide());
		concreteProcessor.handlePositionOrder(positionOrder, processingState);
	}

}
