package com.gft.digitalbank.exchange.solution;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gft.digitalbank.exchange.Exchange;
import com.gft.digitalbank.exchange.listener.ProcessingListener;
import com.gft.digitalbank.exchange.solution.message.MessageCollector;
import com.gft.digitalbank.exchange.solution.message.MessageReceiver;
import com.gft.digitalbank.exchange.solution.order.OrderProcessor;
import com.gft.digitalbank.exchange.solution.result.ResultReporter;

public class StockExchange implements Exchange {

	private static final Logger LOGGER = LoggerFactory.getLogger(StockExchange.class);
	private List<String> queueNames;
	private ProcessingListener processingListener;
	private List<MessageReceiver> receivers = new ArrayList<>();

	@Override
	public void register(ProcessingListener processingListener) {
		this.processingListener = processingListener;
	}

	@Override
	public void setDestinations(List<String> list) {
		queueNames = new ArrayList<>(list);
		final ResultReporter resultReporter = new ResultReporter(processingListener);
		final OrderProcessor orderProcessor = new OrderProcessor(resultReporter);
		final MessageCollector messageCollector = new MessageCollector(orderProcessor, queueNames);
		for (final String queueName : queueNames)
			receivers.add(new MessageReceiver(queueName, messageCollector));
	}

	@Override
	public void start() {
		LOGGER.debug("start");
		ExecutorService pool = Executors.newFixedThreadPool(queueNames.size());
		receivers.forEach(messageReceiver -> pool.submit(messageReceiver::start));
	}

}