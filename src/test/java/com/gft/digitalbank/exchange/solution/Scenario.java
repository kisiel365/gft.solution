package com.gft.digitalbank.exchange.solution;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.gft.digitalbank.exchange.model.OrderBook;
import com.gft.digitalbank.exchange.model.Transaction;
import com.gft.digitalbank.exchange.model.orders.CancellationOrder;
import com.gft.digitalbank.exchange.model.orders.ModificationOrder;
import com.gft.digitalbank.exchange.model.orders.PositionOrder;

public abstract class Scenario {

	public abstract List<PositionOrder> orders();

	public abstract Set<Transaction> transactions();

	public abstract Set<OrderBook> orderBooks();

	public List<ModificationOrder> modifications() {
		return Collections.emptyList();
	}

	public List<CancellationOrder> cancellations() {
		return Collections.emptyList();
	}

}
