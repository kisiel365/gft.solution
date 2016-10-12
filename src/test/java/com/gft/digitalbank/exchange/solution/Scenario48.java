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

public class Scenario48 extends Scenario {
	public List<PositionOrder> orders() {
		return java.util.Arrays.asList(
				PositionOrder.builder().id(1).timestamp(1L).client("100").broker("1").side(Side.SELL).product("A")
						.details(OrderDetails.builder().amount(100).price(500200).build()).build(),
				PositionOrder.builder().id(2).timestamp(2L).client("101").broker("2").side(Side.SELL).product("A")
						.details(OrderDetails.builder().amount(2000).price(500100).build()).build(),
				PositionOrder.builder().id(3).timestamp(3L).client("102").broker("1").side(Side.SELL).product("A")
						.details(OrderDetails.builder().amount(30).price(500150).build()).build(),
				PositionOrder.builder().id(6).timestamp(6L).client("103").broker("1").side(Side.BUY).product("A")
						.details(OrderDetails.builder().amount(1000).price(500150).build()).build(),
				PositionOrder.builder().id(7).timestamp(7L).client("104").broker("3").side(Side.BUY).product("A")
						.details(OrderDetails.builder().amount(2000).price(510000).build()).build());

	}

	@Override
	public List<ModificationOrder> modifications() {
		return Arrays.asList(
				com.gft.digitalbank.exchange.model.orders.ModificationOrder.builder().id(5).timestamp(5L).broker("1")
						.modifiedOrderId(1).details(OrderDetails.builder().amount(200).price(500200).build()).build());
	}

	@Override
	public List<CancellationOrder> cancellations() {
		return Arrays.asList(com.gft.digitalbank.exchange.model.orders.CancellationOrder.builder().id(4).timestamp(4L)
				.cancelledOrderId(2).broker("2").build());
	}

	public Set<Transaction> transactions() {
		return Sets.newHashSet(new Transaction[] {
				Transaction.builder().id(1).amount(30).price(500150).brokerBuy("1").brokerSell("1").clientBuy("103")
						.clientSell("102").product("A").build(),
				Transaction.builder().id(2).amount(200).price(500200).brokerBuy("3").brokerSell("1").clientBuy("104")
						.clientSell("100").product("A").build() });
	}

	public Set<OrderBook> orderBooks() {
		return Sets.newHashSet(new OrderBook[] { OrderBook.builder().product("A")
				.buyEntry(OrderEntry.builder().id(1).amount(1800).price(510000).client("104").broker("3").build())
				.buyEntry(OrderEntry.builder().id(2).amount(970).price(500150).client("103").broker("1").build())
				.build() });
	}
}
