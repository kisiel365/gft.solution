package com.gft.digitalbank.exchange.solution.order.comparators;

import java.util.Comparator;

import com.gft.digitalbank.exchange.model.Transaction;

public class TransactionComparator implements Comparator<Transaction> {

	@Override
	public int compare(Transaction o1, Transaction o2) {
		return Integer.compare(o1.getId(), o2.getId());
	}
}
