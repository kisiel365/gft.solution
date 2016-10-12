package com.gft.digitalbank.exchange.solution;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.gft.digitalbank.exchange.model.OrderBook;
import com.gft.digitalbank.exchange.model.Transaction;
import com.gft.digitalbank.exchange.model.orders.PositionOrder;

// not needed
public class Scenario18 extends Scenario {
	public List<PositionOrder> orders() {
		return Collections.emptyList();
	}

	public Set<Transaction> transactions() {
		return Collections.emptySet();
	}

	public Set<OrderBook> orderBooks() {
		return Collections.emptySet();
	}
}
