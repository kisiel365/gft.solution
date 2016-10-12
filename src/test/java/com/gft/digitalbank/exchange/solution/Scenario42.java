package com.gft.digitalbank.exchange.solution;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.gft.digitalbank.exchange.model.OrderBook;
import com.gft.digitalbank.exchange.model.OrderDetails;
import com.gft.digitalbank.exchange.model.Transaction;
import com.gft.digitalbank.exchange.model.orders.CancellationOrder;
import com.gft.digitalbank.exchange.model.orders.PositionOrder;
import com.gft.digitalbank.exchange.model.orders.Side;
import com.google.common.collect.Sets;

public class Scenario42 extends Scenario {
	public List<PositionOrder> orders() {
		return java.util.Arrays.asList(
				PositionOrder.builder().id(1).timestamp(1L).client("100").broker("1").side(Side.SELL).product("A")
						.details(OrderDetails.builder().amount(100).price(80).build()).build(),
				PositionOrder.builder().id(2).timestamp(2L).client("101").broker("2").side(Side.SELL).product("A")
						.details(OrderDetails.builder().amount(200).price(75).build()).build(),
				PositionOrder.builder().id(3).timestamp(3L).client("102").broker("1").side(Side.BUY).product("A")
						.details(OrderDetails.builder().amount(300).price(75).build()).build());
	}

	@Override
	public List<CancellationOrder> cancellations() {
		return Arrays.asList(com.gft.digitalbank.exchange.model.orders.CancellationOrder.builder().id(4).timestamp(4L)
				.cancelledOrderId(3).broker("1").build());
	}

	public Set<Transaction> transactions() {
		return Sets.newHashSet(new Transaction[] { Transaction.builder().id(1).amount(200).price(75).brokerBuy("1")
				.brokerSell("2").clientBuy("102").clientSell("101").product("A").build() });
	}

	public Set<OrderBook> orderBooks() {
		return Sets
				.newHashSet(
						new OrderBook[] { OrderBook
								.builder().product("A").sellEntry(com.gft.digitalbank.exchange.model.OrderEntry
										.builder().id(1).amount(100).price(80).broker("1").client("100").build())
								.build() });
	}
}
