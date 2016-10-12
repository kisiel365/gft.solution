package com.gft.digitalbank.exchange.solution;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.gft.digitalbank.exchange.model.OrderBook;
import com.gft.digitalbank.exchange.model.OrderDetails;
import com.gft.digitalbank.exchange.model.OrderEntry;
import com.gft.digitalbank.exchange.model.Transaction;
import com.gft.digitalbank.exchange.model.orders.ModificationOrder;
import com.gft.digitalbank.exchange.model.orders.PositionOrder;
import com.gft.digitalbank.exchange.model.orders.Side;
import com.google.common.collect.Sets;

public class Scenario25 extends Scenario {
	public List<PositionOrder> orders() {
		return java.util.Arrays.asList(
				PositionOrder.builder().id(1).timestamp(1L).client("100").broker("1").side(Side.SELL).product("A")
						.details(OrderDetails.builder().amount(100).price(100).build()).build(),
				PositionOrder.builder().id(2).timestamp(2L).client("101").broker("2").side(Side.SELL).product("A")
						.details(OrderDetails.builder().amount(200).price(90).build()).build(),
				PositionOrder.builder().id(3).timestamp(3L).client("102").broker("1").side(Side.SELL).product("A")
						.details(OrderDetails.builder().amount(300).price(100).build()).build(),

				PositionOrder.builder().id(5).timestamp(5L).client("103").broker("3").side(Side.BUY).product("A")
						.details(OrderDetails.builder().amount(150).price(100).build()).build());
	}

	@Override
	public List<ModificationOrder> modifications() {
		return Arrays.asList(com.gft.digitalbank.exchange.model.orders.ModificationOrder.builder().id(4).timestamp(4L)
				.broker("2").modifiedOrderId(2).details(OrderDetails.builder().amount(100).price(90).build()).build());
	}

	public Set<Transaction> transactions() {
		return Sets.newHashSet(new Transaction[] {
				Transaction.builder().id(1).amount(100).price(90).brokerBuy("3").brokerSell("2").clientBuy("103")
						.clientSell("101").product("A").build(),
				Transaction.builder().id(2).amount(50).price(100).brokerBuy("3").brokerSell("1").clientBuy("103")
						.clientSell("100").product("A").build() });
	}

	public Set<OrderBook> orderBooks() {
		return Sets.newHashSet(new OrderBook[] { OrderBook.builder().product("A")
				.sellEntry(OrderEntry.builder().id(1).amount(50).price(100).broker("1").client("100").build())
				.sellEntry(OrderEntry.builder().id(2).amount(300).price(100).broker("1").client("102").build())
				.build() });
	}
}
