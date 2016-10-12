package com.gft.digitalbank.exchange.solution;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.gft.digitalbank.exchange.model.OrderBook;
import com.gft.digitalbank.exchange.model.OrderDetails;
import com.gft.digitalbank.exchange.model.OrderEntry;
import com.gft.digitalbank.exchange.model.Transaction;
import com.gft.digitalbank.exchange.model.orders.CancellationOrder;
import com.gft.digitalbank.exchange.model.orders.PositionOrder;
import com.gft.digitalbank.exchange.model.orders.Side;
import com.google.common.collect.Sets;

public class Scenario21 extends Scenario {
	public List<PositionOrder> orders() {
		return Arrays.asList(
				PositionOrder.builder().id(1).timestamp(1L).client("100").broker("1").side(Side.SELL).product("A")
						.details(OrderDetails.builder().amount(100).price(100).build()).build(),
				PositionOrder.builder().id(2).timestamp(2L).client("101").broker("2").side(Side.SELL).product("A")
						.details(OrderDetails.builder().amount(200).price(90).build()).build(),
				PositionOrder.builder().id(3).timestamp(3L).client("102").broker("1").side(Side.SELL).product("A")
						.details(OrderDetails.builder().amount(300).price(100).build()).build(),
				PositionOrder.builder().id(6).timestamp(6L).client("104").broker("1").side(Side.SELL).product("A")
						.details(OrderDetails.builder().amount(150).price(100).build()).build());
	}

	@Override
	public List<CancellationOrder> cancellations() {
		return Arrays.asList(

				CancellationOrder.builder().id(4).timestamp(4L).cancelledOrderId(2).broker("2").build(),
				CancellationOrder.builder().id(5).timestamp(5L).cancelledOrderId(1).broker("1").build());
	}

	public Set<Transaction> transactions() {
		return Collections.emptySet();
	}

	public Set<OrderBook> orderBooks() {
		return Sets.newHashSet(new OrderBook[] { OrderBook.builder().product("A")
				.sellEntry(OrderEntry.builder().id(1).amount(300).price(100).broker("1").client("102").build())
				.sellEntry(OrderEntry.builder().id(2).amount(150).price(100).broker("1").client("104").build())
				.build() });
	}
}
