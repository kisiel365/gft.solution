package com.gft.digitalbank.exchange.solution.message.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gft.digitalbank.exchange.model.orders.ShutdownNotification;
import com.google.gson.Gson;

public class ShutdownNotificationMessageHandler implements MessageHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(ShutdownNotificationMessageHandler.class);

	private Gson gson;

	public ShutdownNotificationMessageHandler() {
		gson = new Gson();
	}

	@Override
	public boolean handle(String text) {
		ShutdownNotification fromJson = gson.fromJson(text, ShutdownNotification.class);
		LOGGER.debug("SHUT:  " + fromJson);
		return false;
	}

}
