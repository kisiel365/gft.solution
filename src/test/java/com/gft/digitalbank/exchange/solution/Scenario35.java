package com.gft.digitalbank.exchange.solution;

import java.util.Arrays;
import java.util.List;

import com.gft.digitalbank.exchange.model.OrderBook;
import com.gft.digitalbank.exchange.model.OrderDetails;
import com.gft.digitalbank.exchange.model.OrderEntry;
import com.gft.digitalbank.exchange.model.Transaction;
import com.gft.digitalbank.exchange.model.orders.ModificationOrder;
import com.gft.digitalbank.exchange.model.orders.PositionOrder;
import com.gft.digitalbank.exchange.model.orders.Side;
import com.google.common.collect.Sets;

public class Scenario35 extends Scenario {
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
						.details(OrderDetails.builder().amount(400).price(155).build()).build(),

				PositionOrder.builder().id(12).timestamp(12L).client("108").broker("4").side(Side.SELL).product("D")
						.details(OrderDetails.builder().amount(50).price(205).build()).build(),
				PositionOrder.builder().id(13).timestamp(13L).client("108").broker("4").side(Side.SELL).product("D")
						.details(OrderDetails.builder().amount(400).price(211).build()).build());
	}

	@Override
	public List<ModificationOrder> modifications() {
		return Arrays.asList(
				ModificationOrder.builder().id(9).timestamp(9L).broker("1").modifiedOrderId(1)
						.details(OrderDetails.builder().amount(200).price(100).build()).build(),
				ModificationOrder.builder().id(10).timestamp(10L).broker("2").modifiedOrderId(2)
						.details(OrderDetails.builder().amount(200).price(151).build()).build(),
				ModificationOrder.builder().id(11).timestamp(11L).broker("3").modifiedOrderId(3)
						.details(OrderDetails.builder().amount(200).price(100).build()).build(),
				ModificationOrder.builder().id(14).timestamp(14L).broker("4").modifiedOrderId(4)
						.details(OrderDetails.builder().amount(500).price(210).build()).build());
	}

	public java.util.Set<Transaction> transactions() {
		return Sets.newHashSet(new Transaction[] {
				Transaction.builder().id(1).amount(100).price(190).brokerBuy("4").brokerSell("1").clientBuy("104")
						.clientSell("100").product("A").build(),
				Transaction.builder().id(1).amount(200).price(141).brokerBuy("2").brokerSell("3").clientBuy("101")
						.clientSell("105").product("B").build(),
				Transaction.builder().id(1).amount(200).price(140).brokerBuy("2").brokerSell("3").clientBuy("106")
						.clientSell("102").product("C").build(),
				Transaction.builder().id(1).amount(400).price(155).brokerBuy("4").brokerSell("1").clientBuy("103")
						.clientSell("107").product("D").build(),
				Transaction.builder().id(2).amount(50).price(205).brokerBuy("4").brokerSell("4").clientBuy("103")
						.clientSell("108").product("D").build() });
	}

	public java.util.Set<OrderBook> orderBooks() {
		return Sets.newHashSet(new OrderBook[] { OrderBook.builder().product("A")
				.sellEntry(OrderEntry.builder().id(1).amount(100).price(100).broker("1").client("100").build()).build(),

				OrderBook.builder().product("D")
						.sellEntry(OrderEntry.builder().id(1).amount(400).price(211).broker("4").client("108").build())
						.buyEntry(OrderEntry.builder().id(1).amount(50).price(210).broker("4").client("103").build())
						.build(),

				OrderBook.builder().product("C")
						.buyEntry(OrderEntry.builder().id(1).amount(100).price(140).broker("2").client("106").build())
						.build() });
	}
}
