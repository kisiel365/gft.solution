package com.gft.digitalbank.exchange.solution.order.processors;

import com.gft.digitalbank.exchange.model.orders.BrokerMessage;
import com.gft.digitalbank.exchange.solution.order.ProcessingState;

@FunctionalInterface
public interface SingleOrderProcessor {

	void processOrder(BrokerMessage message, ProcessingState processingState);

}
