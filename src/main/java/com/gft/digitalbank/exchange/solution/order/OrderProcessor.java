package com.gft.digitalbank.exchange.solution.order;

import java.util.List;
import java.util.Set;

import com.gft.digitalbank.exchange.model.SolutionResult;
import com.gft.digitalbank.exchange.solution.order.dto.ExtendedOrdersDto;
import com.gft.digitalbank.exchange.solution.order.dto.OrdersDto;
import com.gft.digitalbank.exchange.solution.order.dto.ProductRelatedOrders;
import com.gft.digitalbank.exchange.solution.result.ResultReporter;
import com.gft.digitalbank.exchange.solution.result.SolutionResultCreator;

public class OrderProcessor {

	private ResultReporter resultReporter;

	public OrderProcessor(ResultReporter resultReporter) {
		this.resultReporter = resultReporter;
	}

	public void startProcessing(OrdersDto ordersDto) {
		ExtendedOrdersDto extendedOrdersDto = new OrderMapper(ordersDto).map();
		Set<ProductRelatedOrders> ordersGroupedByProducts = new OrderGroupper(extendedOrdersDto)
				.groupOrdersByProducts();
		SingleProductOrderProcessorDelegator singleProductOrderProcessorDelegator = new SingleProductOrderProcessorDelegator(
				ordersGroupedByProducts);
		List<ProcessingState> processingResults = singleProductOrderProcessorDelegator.delegate();
		SolutionResult solutionResult = new SolutionResultCreator().createSolutionResult(processingResults);
		resultReporter.transactionsCalculated(solutionResult);
	}

}
