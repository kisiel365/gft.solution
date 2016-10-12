package com.gft.digitalbank.exchange.solution.result;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gft.digitalbank.exchange.model.OrderBook;
import com.gft.digitalbank.exchange.model.OrderEntry;
import com.gft.digitalbank.exchange.model.SolutionResult;
import com.gft.digitalbank.exchange.model.Transaction;
import com.gft.digitalbank.exchange.model.orders.PositionOrder;
import com.gft.digitalbank.exchange.solution.order.ProcessingState;

public class SolutionResultCreator {
	private static final Logger LOGGER = LoggerFactory.getLogger(SolutionResultCreator.class);

	public SolutionResult createSolutionResult(List<ProcessingState> processingResults) {
		Set<Transaction> transactions = new HashSet<>();
		Set<OrderBook> orderBooks = new HashSet<>();
		for (ProcessingState processingResult : processingResults) {
			transactions.addAll(processingResult.getTransactions());
			List<OrderEntry> notMatchedBuyOrders = new ArrayList<>();
			int sellOrderId = 1;
			int buyOrderId = 1;

			for (PositionOrder order : processingResult.getAwaitingBuyOffers()) {
				OrderEntry orderEntry = OrderEntry.builder().id(buyOrderId++).amount(order.getDetails().getAmount())
						.price(order.getDetails().getPrice()).broker(order.getBroker()).client(order.getClient())
						.build();
				notMatchedBuyOrders.add(orderEntry);
			}
			List<OrderEntry> notMatchedSellOrders = new ArrayList<>();
			for (PositionOrder order : processingResult.getAwaitingSellOffers()) {
				OrderEntry orderEntry = OrderEntry.builder().id(sellOrderId++).amount(order.getDetails().getAmount())
						.price(order.getDetails().getPrice()).broker(order.getBroker()).client(order.getClient())
						.build();
				notMatchedSellOrders.add(orderEntry);
			}
			if (!notMatchedBuyOrders.isEmpty() || !notMatchedSellOrders.isEmpty()) {
				OrderBook orderBook = OrderBook.builder().product(processingResult.getProductName())
						.buyEntries(notMatchedBuyOrders).sellEntries(notMatchedSellOrders).build();
				orderBooks.add(orderBook);
			}
		}
		LOGGER.debug("Constructed solution:\n" + transactions + "\n" + orderBooks);
		return new SolutionResult(new HashSet<Transaction>(transactions), orderBooks);
	}
}
