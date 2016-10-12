package com.gft.digitalbank.exchange.solution.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.gft.digitalbank.exchange.model.orders.BrokerMessage;
import com.gft.digitalbank.exchange.model.orders.PositionOrder;
import com.gft.digitalbank.exchange.solution.order.comparators.TimestampOrderComparator;
import com.gft.digitalbank.exchange.solution.order.dto.ExtendedCancellationOrder;
import com.gft.digitalbank.exchange.solution.order.dto.ExtendedModificationOrder;
import com.gft.digitalbank.exchange.solution.order.dto.ProductRelatedOrders;
import com.gft.digitalbank.exchange.solution.order.processors.CancellationOrderProcessor;
import com.gft.digitalbank.exchange.solution.order.processors.SingleOrderProcessor;
import com.gft.digitalbank.exchange.solution.order.processors.ModifyOrderProcessor;
import com.gft.digitalbank.exchange.solution.order.processors.PositionOrderProcessor;

public class SingleProductOrderProcessor {

	private Map<Class<? extends BrokerMessage>, SingleOrderProcessor> processors;

	public SingleProductOrderProcessor() {
		processors = new HashMap<>();
		processors.put(PositionOrder.class, new PositionOrderProcessor());
		processors.put(ExtendedModificationOrder.class, new ModifyOrderProcessor());
		processors.put(ExtendedCancellationOrder.class, new CancellationOrderProcessor());
	}

	public ProcessingState processOrdersOfTheSameProduct(ProductRelatedOrders productRelatedOrders) {
		List<BrokerMessage> orders = new ArrayList<>();
		orders.addAll(productRelatedOrders.getPositionOrders());
		orders.addAll(productRelatedOrders.getCancellationOrders());
		orders.addAll(productRelatedOrders.getModificationOrders());
		List<BrokerMessage> ordersSortedByTimestamp = orders.stream().sorted(new TimestampOrderComparator())
				.collect(Collectors.toList());

		ProcessingState processingState = new ProcessingState(productRelatedOrders.getProduct());
		for (BrokerMessage order : ordersSortedByTimestamp) {
			SingleOrderProcessor orderProcessor = processors.get(order.getClass());
			orderProcessor.processOrder(order, processingState);
		}
		return processingState;
	}
}
