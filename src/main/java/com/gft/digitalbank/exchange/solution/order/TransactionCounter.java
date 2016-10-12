package com.gft.digitalbank.exchange.solution.order;

public class TransactionCounter {
	private int counter = 1;

	public int getTransactionId() {
		return counter++;
	}
}
