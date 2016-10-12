package com.gft.digitalbank.exchange.solution.message.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gft.digitalbank.exchange.model.orders.PositionOrder;
import com.gft.digitalbank.exchange.solution.message.MessageCollector;
import com.google.gson.Gson;

public class PositionOrderMessageHandler implements MessageHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(PositionOrderMessageHandler.class);

	private MessageCollector messageCollector;
	private Gson gson;

	public PositionOrderMessageHandler(MessageCollector messageCollector) {
		this.messageCollector = messageCollector;
		gson = new Gson();
	}

	@Override
	public boolean handle(String text) {
		PositionOrder message = gson.fromJson(text, PositionOrder.class);
		LOGGER.debug("ORDER: " + message);
		messageCollector.addPositionOrder(message);
		return true;
	}

}
