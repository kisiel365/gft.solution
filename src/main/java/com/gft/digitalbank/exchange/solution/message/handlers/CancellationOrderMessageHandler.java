package com.gft.digitalbank.exchange.solution.message.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gft.digitalbank.exchange.model.orders.CancellationOrder;
import com.gft.digitalbank.exchange.solution.message.MessageCollector;
import com.google.gson.Gson;

public class CancellationOrderMessageHandler implements MessageHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(CancellationOrderMessageHandler.class);

	private Gson gson;
	private MessageCollector messageCollector;

	public CancellationOrderMessageHandler(MessageCollector messageCollector) {
		this.messageCollector = messageCollector;
		gson = new Gson();
	}

	@Override
	public boolean handle(String text) {
		CancellationOrder message = gson.fromJson(text, CancellationOrder.class);
		LOGGER.debug("CANCEL: " + message);
		messageCollector.addCancellationOrder(message);
		return true;
	}

}
