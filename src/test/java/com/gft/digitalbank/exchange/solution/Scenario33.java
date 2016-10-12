package com.gft.digitalbank.exchange.solution;

import java.util.List;
import java.util.Set;

import com.gft.digitalbank.exchange.model.OrderBook;
import com.gft.digitalbank.exchange.model.OrderDetails;
import com.gft.digitalbank.exchange.model.OrderEntry;
import com.gft.digitalbank.exchange.model.Transaction;
import com.gft.digitalbank.exchange.model.orders.PositionOrder;
import com.gft.digitalbank.exchange.model.orders.Side;
import com.google.common.collect.Sets;

public class Scenario33 extends Scenario {
	public List<PositionOrder> orders() {
		return java.util.Arrays.asList(
				PositionOrder.builder().id(1).timestamp(1L).client("100").broker("1").side(Side.SELL).product("A")
						.details(OrderDetails.builder().amount(100).price(199).build()).build(),
				PositionOrder.builder().id(2).timestamp(2L).client("101").broker("2").side(Side.BUY).product("B")
						.details(OrderDetails.builder().amount(200).price(111).build()).build(),
				PositionOrder.builder().id(3).timestamp(3L).client("102").broker("3").side(Side.SELL).product("C")
						.details(OrderDetails.builder().amount(300).price(144).build()).build(),
				PositionOrder.builder().id(4).timestamp(4L).client("103").broker("4").side(Side.BUY).product("D")
						.details(OrderDetails.builder().amount(400).price(150).build()).build(),

				PositionOrder.builder().id(5).timestamp(5L).client("104").broker("4").side(Side.BUY).product("A")
						.details(OrderDetails.builder().amount(100).price(190).build()).build(),
				PositionOrder.builder().id(6).timestamp(6L).client("105").broker("3").side(Side.SELL).product("B")
						.details(OrderDetails.builder().amount(200).price(141).build()).build(),
				PositionOrder.builder().id(7).timestamp(7L).client("106").broker("2").side(Side.BUY).product("C")
						.details(OrderDetails.builder().amount(300).price(140).build()).build(),
				PositionOrder.builder().id(8).timestamp(8L).client("107").broker("1").side(Side.SELL).product("D")
						.details(OrderDetails.builder().amount(400).price(155).build()).build());
	}

	public Set<Transaction> transactions() {
		return java.util.Collections.emptySet();
	}

	public Set<OrderBook> orderBooks() {
		return Sets.newHashSet(new OrderBook[] { OrderBook.builder().product("A")
				.sellEntry(OrderEntry.builder().id(1).amount(100).price(199).broker("1").client("100").build())
				.buyEntry(OrderEntry.builder().id(1).amount(100).price(190).broker("4").client("104").build()).build(),
				OrderBook.builder().product("B")
						.buyEntry(OrderEntry.builder().id(1).amount(200).price(111).broker("2").client("101").build())
						.sellEntry(OrderEntry.builder().id(1).amount(200).price(141).broker("3").client("105").build())
						.build(),
				OrderBook.builder().product("C")
						.sellEntry(OrderEntry.builder().id(1).amount(300).price(144).broker("3").client("102").build())
						.buyEntry(OrderEntry.builder().id(1).amount(300).price(140).broker("2").client("106").build())
						.build(),
				OrderBook.builder().product("D")
						.buyEntry(OrderEntry.builder().id(1).amount(400).price(150).broker("4").client("103").build())
						.sellEntry(OrderEntry.builder().id(1).amount(400).price(155).broker("1").client("107").build())
						.build() });
	}
}
