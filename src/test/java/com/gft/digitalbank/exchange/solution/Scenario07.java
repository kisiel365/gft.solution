package com.gft.digitalbank.exchange.solution;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.gft.digitalbank.exchange.model.OrderBook;
import com.gft.digitalbank.exchange.model.OrderDetails;
import com.gft.digitalbank.exchange.model.OrderEntry;
import com.gft.digitalbank.exchange.model.Transaction;
import com.gft.digitalbank.exchange.model.orders.PositionOrder;
import com.gft.digitalbank.exchange.model.orders.Side;
import com.google.common.collect.Sets;

public class Scenario07 extends Scenario {

	public List<PositionOrder> orders() {
		return Arrays.asList(
				PositionOrder.builder().id(1).timestamp(1L).client("100").broker("1").side(Side.BUY).product("A")
						.details(OrderDetails.builder().amount(100).price(100).build()).build(),
				PositionOrder.builder().id(2).timestamp(2L).client("101").broker("2").side(Side.BUY).product("A")
						.details(OrderDetails.builder().amount(200).price(90).build()).build(),
				PositionOrder.builder().id(3).timestamp(3L).client("102").broker("1").side(Side.BUY).product("A")
						.details(OrderDetails.builder().amount(300).price(110).build()).build(),
				PositionOrder.builder().id(4).timestamp(4L).client("103").broker("2").side(Side.BUY).product("A")
						.details(OrderDetails.builder().amount(1000).price(120).build()).build());
	}

	public Set<Transaction> transactions() {
		return Collections.emptySet();
	}

	public Set<OrderBook> orderBooks() {
		return Sets.newHashSet(new OrderBook[] { OrderBook.builder().product("A")
				.buyEntry(OrderEntry.builder().id(1).amount(1000).price(120).broker("2").client("103").build())
				.buyEntry(OrderEntry.builder().id(2).amount(300).price(110).broker("1").client("102").build())
				.buyEntry(OrderEntry.builder().id(3).amount(100).price(100).broker("1").client("100").build())
				.buyEntry(OrderEntry.builder().id(4).amount(200).price(90).broker("2").client("101").build())
				.build() });
	}

}
