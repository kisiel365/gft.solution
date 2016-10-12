package com.gft.digitalbank.exchange.solution;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.gft.digitalbank.exchange.model.OrderBook;
import com.gft.digitalbank.exchange.model.OrderDetails;
import com.gft.digitalbank.exchange.model.Transaction;
import com.gft.digitalbank.exchange.model.orders.ModificationOrder;
import com.gft.digitalbank.exchange.model.orders.PositionOrder;
import com.gft.digitalbank.exchange.model.orders.Side;
import com.google.common.collect.Sets;

public class Scenario24 extends Scenario {
	public List<PositionOrder> orders() {
		return java.util.Arrays.asList(
				PositionOrder.builder().id(1).timestamp(1L).client("100").broker("1").side(Side.BUY).product("A")
						.details(OrderDetails.builder().amount(100).price(100).build()).build(),
				PositionOrder.builder().id(2).timestamp(2L).client("101").broker("2").side(Side.BUY).product("A")
						.details(OrderDetails.builder().amount(200).price(90).build()).build(),
				PositionOrder.builder().id(3).timestamp(3L).client("102").broker("1").side(Side.BUY).product("A")
						.details(OrderDetails.builder().amount(300).price(100).build()).build(),

				PositionOrder.builder().id(5).timestamp(5L).client("103").broker("3").side(Side.SELL).product("A")
						.details(OrderDetails.builder().amount(300).price(100).build()).build());
	}

	@Override
	public List<ModificationOrder> modifications() {
		return Arrays.asList(com.gft.digitalbank.exchange.model.orders.ModificationOrder.builder().id(4).timestamp(4L)
				.broker("2").modifiedOrderId(2).details(OrderDetails.builder().amount(150).price(101).build()).build());
	}

	public Set<Transaction> transactions() {
		return Sets.newHashSet(new Transaction[] {
				Transaction.builder().id(1).amount(150).price(101).brokerBuy("2").brokerSell("3").clientBuy("101")
						.clientSell("103").product("A").build(),
				Transaction.builder().id(2).amount(100).price(100).brokerBuy("1").brokerSell("3").clientBuy("100")
						.clientSell("103").product("A").build(),
				Transaction.builder().id(3).amount(50).price(100).brokerBuy("1").brokerSell("3").clientBuy("102")
						.clientSell("103").product("A").build() });
	}

	public Set<OrderBook> orderBooks() {
		return Sets
				.newHashSet(
						new OrderBook[] {
								OrderBook.builder()
										.product("A").buyEntry(com.gft.digitalbank.exchange.model.OrderEntry.builder()
												.id(1).amount(250).price(100).broker("1").client("102").build())
										.build() });
	}
}
