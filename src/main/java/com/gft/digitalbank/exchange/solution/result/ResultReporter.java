package com.gft.digitalbank.exchange.solution.result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gft.digitalbank.exchange.listener.ProcessingListener;
import com.gft.digitalbank.exchange.model.SolutionResult;

public class ResultReporter {
	private static final Logger LOGGER = LoggerFactory.getLogger(ResultReporter.class);

	private ProcessingListener processingListener;

	public ResultReporter(ProcessingListener processingListener) {
		this.processingListener = processingListener;
	}

	public void transactionsCalculated(SolutionResult solutionResult) {
		LOGGER.debug("Sending finish to " + processingListener);
		processingListener.processingDone(solutionResult);
	}

}
