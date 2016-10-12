package com.gft.digitalbank.exchange.solution;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

import com.gft.digitalbank.exchange.model.OrderBook;
import com.gft.digitalbank.exchange.model.OrderDetails;
import com.gft.digitalbank.exchange.model.SolutionResult;
import com.gft.digitalbank.exchange.model.Transaction;
import com.gft.digitalbank.exchange.model.orders.CancellationOrder;
import com.gft.digitalbank.exchange.model.orders.ModificationOrder;
import com.gft.digitalbank.exchange.model.orders.PositionOrder;
import com.gft.digitalbank.exchange.model.orders.Side;
import com.gft.digitalbank.exchange.solution.order.OrderProcessor;
import com.gft.digitalbank.exchange.solution.order.dto.OrdersDto;
import com.gft.digitalbank.exchange.solution.result.ResultReporter;

public class MessageProcessorOrderPerformanceTest {

	Scenario scenario = new Scenario() {

		private final static int SIZE = 10000;

		@Override
		public Set<OrderBook> orderBooks() {
			return Collections.emptySet();
		}

		@Override
		public List<PositionOrder> orders() {
			List<PositionOrder> collect1 = IntStream.range(1, SIZE + 1)
					.mapToObj(i -> PositionOrder.builder().id(i).timestamp(Long.valueOf(i)).client("100").broker("1")
							.side(Side.BUY).product("A").details(OrderDetails.builder().amount(100).price(100).build())
							.build())
					.collect(Collectors.toList());
			List<PositionOrder> collect2 = IntStream.range(1, SIZE + 1)
					.mapToObj(i -> PositionOrder.builder().id(i).timestamp(Long.valueOf(i + SIZE)).client("101")
							.broker("2").side(Side.SELL).product("A")
							.details(OrderDetails.builder().amount(100).price(100).build()).build())
					.collect(Collectors.toList());
			collect1.addAll(collect2);
			return collect1;
		}

		@Override
		public Set<Transaction> transactions() {

			return IntStream.range(1, SIZE + 1)
					.mapToObj(i -> Transaction.builder().id(i).amount(100).brokerBuy("1").brokerSell("2")
							.clientBuy("100").clientSell("101").price(100).product("A").build())
					.collect(Collectors.toSet());
		}

	};

	@Test
	public void test() {
		OrderProcessor orderProcessor = new OrderProcessor(new ResultReporter(null) {

			@Override
			public void transactionsCalculated(SolutionResult solutionResult) {
				Set<OrderBook> actualOrderBooks = solutionResult.getOrderBooks();
				Set<Transaction> actualTransactions = solutionResult.getTransactions();
				Set<Transaction> expectedTransactions = scenario.transactions();
				Set<OrderBook> exptectedOrderBooks = scenario.orderBooks();
				Assert.assertTrue(CollectionUtils.isEqualCollection(expectedTransactions, actualTransactions));
				Assert.assertTrue(CollectionUtils.isEqualCollection(exptectedOrderBooks, actualOrderBooks));
			}
		});
		List<PositionOrder> positionOrders = scenario.orders();
		List<ModificationOrder> modificationOrders = scenario.modifications();
		List<CancellationOrder> cancellationOrders = scenario.cancellations();
		orderProcessor.startProcessing(new OrdersDto(positionOrders, modificationOrders, cancellationOrders));
	}
}
