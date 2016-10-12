package com.gft.digitalbank.exchange.solution;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.gft.digitalbank.exchange.model.OrderBook;
import com.gft.digitalbank.exchange.model.OrderDetails;
import com.gft.digitalbank.exchange.model.OrderEntry;
import com.gft.digitalbank.exchange.model.Transaction;
import com.gft.digitalbank.exchange.model.orders.CancellationOrder;
import com.gft.digitalbank.exchange.model.orders.ModificationOrder;
import com.gft.digitalbank.exchange.model.orders.PositionOrder;
import com.gft.digitalbank.exchange.model.orders.Side;
import com.google.common.collect.Sets;

public class Scenario46 extends Scenario {
	public List<PositionOrder> orders() {
		return java.util.Arrays.asList(
				PositionOrder.builder().id(1).timestamp(1L).client("100").broker("1").side(Side.SELL).product("A")
						.details(OrderDetails.builder().amount(1000000).price(5).build()).build(),
				PositionOrder.builder().id(2).timestamp(2L).client("101").broker("2").side(Side.SELL).product("A")
						.details(OrderDetails.builder().amount(20000000).price(3).build()).build(),
				PositionOrder.builder().id(3).timestamp(3L).client("102").broker("1").side(Side.SELL).product("A")
						.details(OrderDetails.builder().amount(300000).price(4).build()).build(),
				PositionOrder.builder().id(6).timestamp(6L).client("103").broker("1").side(Side.BUY).product("A")
						.details(OrderDetails.builder().amount(10000000).price(4).build()).build(),
				PositionOrder.builder().id(7).timestamp(7L).client("104").broker("3").side(Side.BUY).product("A")
						.details(OrderDetails.builder().amount(20000000).price(10).build()).build());

	}

	@Override
	public List<ModificationOrder> modifications() {
		return Arrays.asList(
				com.gft.digitalbank.exchange.model.orders.ModificationOrder.builder().id(5).timestamp(5L).broker("1")
						.modifiedOrderId(1).details(OrderDetails.builder().amount(200000000).price(6).build()).build());
	}

	@Override
	public List<CancellationOrder> cancellations() {
		return Arrays.asList(com.gft.digitalbank.exchange.model.orders.CancellationOrder.builder().id(4).timestamp(4L)
				.cancelledOrderId(2).broker("2").build());
	}

	public Set<Transaction> transactions() {
		return Sets.newHashSet(new Transaction[] {
				Transaction.builder().id(1).amount(300000).price(4).brokerBuy("1").brokerSell("1").clientBuy("103")
						.clientSell("102").product("A").build(),
				Transaction.builder().id(2).amount(20000000).price(6).brokerBuy("3").brokerSell("1").clientBuy("104")
						.clientSell("100").product("A").build() });
	}

	public Set<OrderBook> orderBooks() {
		return Sets.newHashSet(new OrderBook[] { OrderBook.builder().product("A")
				.sellEntry(OrderEntry.builder().id(1).amount(180000000).price(6).client("100").broker("1").build())
				.buyEntry(OrderEntry.builder().id(1).amount(9700000).price(4).client("103").broker("1").build())
				.build() });
	}
}
