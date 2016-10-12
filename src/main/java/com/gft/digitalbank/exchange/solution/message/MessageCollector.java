package com.gft.digitalbank.exchange.solution.message;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gft.digitalbank.exchange.model.orders.CancellationOrder;
import com.gft.digitalbank.exchange.model.orders.ModificationOrder;
import com.gft.digitalbank.exchange.model.orders.PositionOrder;
import com.gft.digitalbank.exchange.solution.order.OrderProcessor;
import com.gft.digitalbank.exchange.solution.order.dto.OrdersDto;

public class MessageCollector {
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageCollector.class);

	private List<PositionOrder> positionOrders;
	private List<ModificationOrder> modificationOrders;
	private List<CancellationOrder> cancellationOrders;
	private List<String> activeBrokers;
	private OrderProcessor orderProcessor;

	public MessageCollector(OrderProcessor orderProcessor, List<String> queueNames) {
		this.orderProcessor = orderProcessor;
		positionOrders = new ArrayList<>();
		modificationOrders = new ArrayList<>();
		cancellationOrders = new ArrayList<>();
		activeBrokers = new ArrayList<>(queueNames);
	}

	public synchronized void addCancellationOrder(CancellationOrder message) {
		cancellationOrders.add(message);
	}

	public synchronized void addModifcationOrder(ModificationOrder message) {
		modificationOrders.add(message);
	}

	public synchronized void addPositionOrder(PositionOrder message) {
		positionOrders.add(message);
	}

	public synchronized void brokerFinished(String broker) {
		LOGGER.info("Finished broker: " + broker);
		activeBrokers.remove(broker);
		if (activeBrokers.isEmpty()) {
			LOGGER.info("Starts processing...");
			OrdersDto ordersDto = new OrdersDto(positionOrders, modificationOrders, cancellationOrders);
			orderProcessor.startProcessing(ordersDto);
		}
	}

}
