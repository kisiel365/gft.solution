package com.gft.digitalbank.exchange.solution.message.handlers;

@FunctionalInterface
public interface MessageHandler {

	public boolean handle(String text);

}
