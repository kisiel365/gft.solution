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

public class MessageProcessorCancellationPerformanceTest {

	Scenario scenario = new Scenario() {

		private final static int SIZE = 50000;

		@Override
		public List<CancellationOrder> cancellations() {
			return IntStream
					.range(1, SIZE + 1).mapToObj(i -> CancellationOrder.builder().id(i)
							.timestamp(Long.valueOf((2 * i) + SIZE)).broker("2").cancelledOrderId(i).build())
					.collect(Collectors.toList());
		}

		@Override
		public Set<OrderBook> orderBooks() {
			return Collections.emptySet();
		}

		@Override
		public List<PositionOrder> orders() {
			return IntStream.range(1, SIZE + 1)
					.mapToObj(i -> PositionOrder.builder().id(i).timestamp(Long.valueOf(i)).client("100").broker("1")
							.side(Side.BUY).product("A").details(OrderDetails.builder().amount(100).price(100).build())
							.build())
					.collect(Collectors.toList());
		};

		@Override
		public Set<Transaction> transactions() {
			return Collections.emptySet();
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
