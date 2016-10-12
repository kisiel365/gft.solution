package com.gft.digitalbank.exchange.solution;

import java.util.List;
import java.util.Set;

import com.gft.digitalbank.exchange.model.OrderBook;
import com.gft.digitalbank.exchange.model.OrderDetails;
import com.gft.digitalbank.exchange.model.OrderEntry;
import com.gft.digitalbank.exchange.model.Transaction;
import com.gft.digitalbank.exchange.model.orders.ModificationOrder;
import com.gft.digitalbank.exchange.model.orders.PositionOrder;
import com.gft.digitalbank.exchange.model.orders.Side;

public class Scenario19 extends Scenario {
	public List<PositionOrder> orders() {
		return java.util.Arrays.asList(
				PositionOrder.builder().id(1).timestamp(1L).client("100").broker("1").side(Side.SELL).product("A")
						.details(OrderDetails.builder().amount(100).price(100).build()).build(),
				PositionOrder.builder().id(2).timestamp(2L).client("101").broker("2").side(Side.SELL).product("A")
						.details(OrderDetails.builder().amount(200).price(90).build()).build(),
				PositionOrder.builder().id(3).timestamp(3L).client("102").broker("1").side(Side.SELL).product("A")
						.details(OrderDetails.builder().amount(300).price(90).build()).build());
	}

	public List<ModificationOrder> modifications() {
		return java.util.Arrays.asList(
				ModificationOrder.builder().id(4).timestamp(4L).broker("2").modifiedOrderId(2)
						.details(OrderDetails.builder().amount(1000).price(90).build()).build(),
				ModificationOrder.builder().id(5).timestamp(5L).broker("2").modifiedOrderId(2)
						.details(OrderDetails.builder().amount(150).price(100).build()).build(),
				ModificationOrder.builder().id(6).timestamp(6L).broker("2").modifiedOrderId(2)
						.details(OrderDetails.builder().amount(300).price(101).build()).build(),
				ModificationOrder.builder().id(7).timestamp(7L).broker("2").modifiedOrderId(2)
						.details(OrderDetails.builder().amount(300).price(100).build()).build());
	}

	public Set<Transaction> transactions() {
		return java.util.Collections.emptySet();
	}

	public Set<OrderBook> orderBooks() {
		return com.google.common.collect.Sets.newHashSet(new OrderBook[] { OrderBook.builder().product("A")
				.sellEntry(OrderEntry.builder().id(1).amount(300).price(90).broker("1").client("102").build())
				.sellEntry(OrderEntry.builder().id(2).amount(100).price(100).broker("1").client("100").build())
				.sellEntry(OrderEntry.builder().id(3).amount(300).price(100).broker("2").client("101").build())
				.build() });
	}
}
