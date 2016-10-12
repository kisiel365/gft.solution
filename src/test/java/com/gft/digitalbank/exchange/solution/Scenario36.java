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

public class Scenario36 extends Scenario {
	public List<PositionOrder> orders() {
		return java.util.Arrays.asList(
				PositionOrder.builder().id(1).timestamp(1L).client("100").broker("1").side(Side.SELL).product("A")
						.details(OrderDetails.builder().amount(100).price(100).build()).build(),
				PositionOrder.builder().id(2).timestamp(2L).client("101").broker("2").side(Side.BUY).product("B")
						.details(OrderDetails.builder().amount(200).price(100).build()).build(),
				PositionOrder.builder().id(3).timestamp(3L).client("102").broker("1").side(Side.SELL).product("C")
						.details(OrderDetails.builder().amount(300).price(100).build()).build(),
				PositionOrder.builder().id(4).timestamp(4L).client("103").broker("3").side(Side.BUY).product("D")
						.details(OrderDetails.builder().amount(400).price(100).build()).build(),
				PositionOrder.builder().id(5).timestamp(5L).client("104").broker("1").side(Side.SELL).product("A")
						.details(OrderDetails.builder().amount(200).price(100).build()).build(),
				PositionOrder.builder().id(6).timestamp(6L).client("105").broker("2").side(Side.BUY).product("B")
						.details(OrderDetails.builder().amount(300).price(100).build()).build(),
				PositionOrder.builder().id(7).timestamp(7L).client("106").broker("1").side(Side.SELL).product("C")
						.details(OrderDetails.builder().amount(400).price(100).build()).build(),
				PositionOrder.builder().id(8).timestamp(8L).client("107").broker("3").side(Side.BUY).product("D")
						.details(OrderDetails.builder().amount(500).price(100).build()).build(),
				PositionOrder.builder().id(9).timestamp(9L).client("108").broker("1").side(Side.SELL).product("A")
						.details(OrderDetails.builder().amount(100).price(95).build()).build(),
				PositionOrder.builder().id(10).timestamp(10L).client("109").broker("2").side(Side.BUY).product("B")
						.details(OrderDetails.builder().amount(200).price(105).build()).build(),
				PositionOrder.builder().id(11).timestamp(11L).client("110").broker("1").side(Side.SELL).product("C")
						.details(OrderDetails.builder().amount(300).price(95).build()).build(),
				PositionOrder.builder().id(12).timestamp(12L).client("111").broker("3").side(Side.BUY).product("D")
						.details(OrderDetails.builder().amount(400).price(105).build()).build(),
				PositionOrder.builder().id(13).timestamp(13L).client("112").broker("1").side(Side.BUY).product("A")
						.details(OrderDetails.builder().amount(100).price(90).build()).build(),
				PositionOrder.builder().id(14).timestamp(14L).client("113").broker("2").side(Side.SELL).product("B")
						.details(OrderDetails.builder().amount(200).price(110).build()).build(),
				PositionOrder.builder().id(15).timestamp(15L).client("114").broker("1").side(Side.BUY).product("C")
						.details(OrderDetails.builder().amount(300).price(90).build()).build(),
				PositionOrder.builder().id(16).timestamp(16L).client("115").broker("3").side(Side.SELL).product("D")
						.details(OrderDetails.builder().amount(400).price(110).build()).build());
	}

	@Override
	public List<ModificationOrder> modifications() {
		return Arrays.asList(
				ModificationOrder.builder().id(17).timestamp(17L).broker("1").modifiedOrderId(13)
						.details(OrderDetails.builder().amount(100).price(91).build()).build(),
				ModificationOrder.builder().id(18).timestamp(18L).broker("2").modifiedOrderId(10)
						.details(OrderDetails.builder().amount(200).price(99).build()).build());
	}

	@Override
	public List<CancellationOrder> cancellations() {
		return Arrays.asList(CancellationOrder.builder().id(19).timestamp(19L).cancelledOrderId(3).broker("1").build(),
				CancellationOrder.builder().id(20).timestamp(20L).cancelledOrderId(12).broker("3").build());
	}

	public Set<Transaction> transactions() {
		return java.util.Collections.emptySet();
	}

	public Set<OrderBook> orderBooks() {
		return com.google.common.collect.Sets.newHashSet(
				new OrderBook[] {
						OrderBook.builder().product("A")
								.sellEntry(OrderEntry.builder().id(1).amount(100).price(95).broker("1").client("108")
										.build())
								.sellEntry(OrderEntry.builder().id(2).amount(100).price(100).broker("1").client("100")
										.build())
								.sellEntry(
										OrderEntry.builder().id(3).amount(200).price(100).broker("1").client("104")
												.build())
								.buyEntry(OrderEntry.builder().id(1).amount(100).price(91).broker("1").client("112")
										.build())
								.build(),
						OrderBook.builder().product("B")
								.sellEntry(OrderEntry.builder().id(1).amount(200).price(110).broker("2").client("113")
										.build())
								.buyEntry(OrderEntry.builder().id(1).amount(200).price(100).broker("2").client("101")
										.build())
								.buyEntry(OrderEntry.builder().id(2).amount(300).price(100).broker("2").client("105")
										.build())
								.buyEntry(OrderEntry.builder().id(3).amount(200).price(99).broker("2").client("109")
										.build())
								.build(),
						OrderBook.builder().product("C")
								.sellEntry(OrderEntry.builder().id(1).amount(300).price(95).broker("1").client("110")
										.build())
								.sellEntry(OrderEntry.builder().id(2).amount(400).price(100).broker("1").client("106")
										.build())
								.buyEntry(OrderEntry.builder().id(1).amount(300).price(90).broker("1").client("114")
										.build())
								.build(),
						OrderBook.builder().product("D")
								.sellEntry(OrderEntry.builder().id(1).amount(400).price(110).broker("3").client("115")
										.build())
								.buyEntry(OrderEntry.builder().id(1).amount(400).price(100).broker("3").client("103")
										.build())
								.buyEntry(OrderEntry.builder().id(2).amount(500).price(100).broker("3").client("107")
										.build())
								.build() });
	}
}
