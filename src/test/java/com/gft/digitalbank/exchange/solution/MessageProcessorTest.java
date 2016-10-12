package com.gft.digitalbank.exchange.solution;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.gft.digitalbank.exchange.model.OrderBook;
import com.gft.digitalbank.exchange.model.SolutionResult;
import com.gft.digitalbank.exchange.model.Transaction;
import com.gft.digitalbank.exchange.model.orders.CancellationOrder;
import com.gft.digitalbank.exchange.model.orders.ModificationOrder;
import com.gft.digitalbank.exchange.model.orders.PositionOrder;
import com.gft.digitalbank.exchange.solution.order.OrderProcessor;
import com.gft.digitalbank.exchange.solution.order.dto.OrdersDto;
import com.gft.digitalbank.exchange.solution.result.ResultReporter;

@RunWith(value = Parameterized.class)
public class MessageProcessorTest {

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { // just for formatting
				{ new Scenario01() }, { new Scenario02() }, { new Scenario03() }, { new Scenario04() },
				{ new Scenario05() }, { new Scenario06() }, { new Scenario07() }, { new Scenario08() },
				{ new Scenario09() }, { new Scenario10() }, { new Scenario11() }, { new Scenario12() },
				{ new Scenario13() }, { new Scenario14() }, { new Scenario15() }, { new Scenario16() },
				{ new Scenario17() }, { new Scenario18() }, { new Scenario19() }, { new Scenario20() },
				{ new Scenario21() }, { new Scenario22() }, { new Scenario23() }, { new Scenario24() },
				{ new Scenario25() }, { new Scenario26() }, { new Scenario27() }, { new Scenario28() },
				{ new Scenario29() }, { new Scenario30() }, { new Scenario31() }, { new Scenario32() },
				{ new Scenario33() }, { new Scenario34() }, { new Scenario35() }, { new Scenario36() },
				{ new Scenario37() }, { new Scenario38() }, { new Scenario39() }, { new Scenario40() },
				{ new Scenario41() }, { new Scenario42() }, { new Scenario43() }, { new Scenario44() },
				{ new Scenario45() }, { new Scenario46() }, { new Scenario47() }, { new Scenario48() } });
	}

	@Parameter(value = 0)
	public Scenario scenario;

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
