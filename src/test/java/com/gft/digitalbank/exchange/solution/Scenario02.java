package com.gft.digitalbank.exchange.solution;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.gft.digitalbank.exchange.model.OrderBook;
import com.gft.digitalbank.exchange.model.OrderDetails;
import com.gft.digitalbank.exchange.model.Transaction;
import com.gft.digitalbank.exchange.model.orders.PositionOrder;
import com.gft.digitalbank.exchange.model.orders.Side;
import com.google.common.collect.Sets;

public class Scenario02 extends Scenario {

	public List<PositionOrder> orders() {
		return Arrays.asList(new PositionOrder[] {
				PositionOrder.builder().id(1).timestamp(1L).client("100").broker("1").side(Side.SELL).product("A")
						.details(OrderDetails.builder().amount(100).price(100).build()).build(),
				PositionOrder.builder().id(2).timestamp(2L).client("101").broker("2").side(Side.BUY).product("A")
						.details(OrderDetails.builder().amount(100).price(100).build()).build() });
	}

	public Set<Transaction> transactions() {
		return Sets.newHashSet(new Transaction[] { Transaction.builder().id(1).amount(100).price(100).brokerBuy("2")
				.brokerSell("1").clientBuy("101").clientSell("100").product("A").build() });
	}

	public Set<OrderBook> orderBooks() {
		return Collections.emptySet();
	}

}
