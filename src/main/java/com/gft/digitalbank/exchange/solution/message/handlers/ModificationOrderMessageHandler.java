package com.gft.digitalbank.exchange.solution.message.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gft.digitalbank.exchange.model.orders.ModificationOrder;
import com.gft.digitalbank.exchange.solution.message.MessageCollector;
import com.google.gson.Gson;

public class ModificationOrderMessageHandler implements MessageHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(ModificationOrderMessageHandler.class);

	private MessageCollector messageCollector;
	private Gson gson;

	public ModificationOrderMessageHandler(MessageCollector messageCollector) {
		this.messageCollector = messageCollector;
		gson = new Gson();
	}

	@Override
	public boolean handle(String text) {
		ModificationOrder message = gson.fromJson(text, ModificationOrder.class);
		LOGGER.debug("MODIF: " + message);
		messageCollector.addModifcationOrder(message);
		return true;
	}

}
