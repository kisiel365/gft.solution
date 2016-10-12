package com.gft.digitalbank.exchange.solution;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.gft.digitalbank.exchange.model.OrderDetails;
import com.gft.digitalbank.exchange.model.Transaction;
import com.gft.digitalbank.exchange.model.orders.ModificationOrder;
import com.gft.digitalbank.exchange.model.orders.PositionOrder;
import com.gft.digitalbank.exchange.model.orders.Side;
import com.google.common.collect.Sets;

public class Scenario40 extends Scenario {
	public List<PositionOrder> orders() {
		return Arrays.asList(
				PositionOrder.builder().id(1).timestamp(1L).client("100").broker("1").side(Side.BUY).product("A")
						.details(OrderDetails.builder().amount(100).price(80).build()).build(),
				PositionOrder.builder().id(2).timestamp(2L).client("101").broker("2").side(Side.BUY).product("A")
						.details(OrderDetails.builder().amount(200).price(75).build()).build(),
				PositionOrder.builder().id(3).timestamp(3L).client("102").broker("1").side(Side.SELL).product("A")
						.details(OrderDetails.builder().amount(300).price(80).build()).build());
	}

	@Override
	public List<ModificationOrder> modifications() {
		return Arrays.asList(ModificationOrder.builder().id(4).timestamp(4L).broker("1").modifiedOrderId(3)
				.details(OrderDetails.builder().amount(200).price(75).build()).build());
	}

	public Set<Transaction> transactions() {
		return Sets.newHashSet(new Transaction[] {
				Transaction.builder().id(1).amount(100).price(80).brokerBuy("1").brokerSell("1").clientBuy("100")
						.clientSell("102").product("A").build(),
				Transaction.builder().id(2).amount(200).price(75).brokerBuy("2").brokerSell("1").clientBuy("101")
						.clientSell("102").product("A").build() });
	}

	public Set<com.gft.digitalbank.exchange.model.OrderBook> orderBooks() {
		return java.util.Collections.emptySet();
	}
}
