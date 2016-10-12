package com.gft.digitalbank.exchange.solution.order.comparators;

import java.util.Comparator;

import com.gft.digitalbank.exchange.model.orders.BrokerMessage;

public class TimestampOrderComparator implements Comparator<BrokerMessage> {

	@Override
	public int compare(BrokerMessage message1, BrokerMessage message2) {
		return Long.valueOf(message1.getTimestamp()).compareTo(Long.valueOf(message2.getTimestamp()));
	}
}
