package com.gft.digitalbank.exchange.solution.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gft.digitalbank.exchange.solution.order.dto.ProductRelatedOrders;

public class SingleProductOrderProcessorDelegator {

	private static final Logger LOGGER = LoggerFactory.getLogger(SingleProductOrderProcessorDelegator.class);

	private Set<ProductRelatedOrders> ordersGroupedByProducts;
	private int numberOfProducts;
	private CompletionService<ProcessingState> pool;

	public SingleProductOrderProcessorDelegator(Set<ProductRelatedOrders> ordersGroupedByProducts) {
		this.ordersGroupedByProducts = ordersGroupedByProducts;
		numberOfProducts = ordersGroupedByProducts.size();
	}

	public List<ProcessingState> delegate() {
		if (numberOfProducts > 0) {
			ExecutorService executor = Executors.newFixedThreadPool(numberOfProducts);
			pool = new ExecutorCompletionService<>(executor);
			submitProcessing();
			return awaitResults();
		}
		return Collections.emptyList();
	}

	private List<ProcessingState> awaitResults() {
		List<ProcessingState> processingResults = Collections.synchronizedList(new ArrayList<>());
		for (int i = 0; i < numberOfProducts; i++)
			try {
				processingResults.add(pool.take().get());
			} catch (Exception e) {
				LOGGER.error("Could not get processing results", e);
			}
		return processingResults;
	}

	private void submitProcessing() {
		for (ProductRelatedOrders productRelatedOrders : ordersGroupedByProducts)
			pool.submit(() -> new SingleProductOrderProcessor().processOrdersOfTheSameProduct(productRelatedOrders));
	}

}
