package com.gft.digitalbank.exchange.solution.order;

import java.util.SortedSet;
import java.util.TreeSet;

import com.gft.digitalbank.exchange.model.Transaction;
import com.gft.digitalbank.exchange.model.orders.PositionOrder;
import com.gft.digitalbank.exchange.solution.order.comparators.BuyOrderComparator;
import com.gft.digitalbank.exchange.solution.order.comparators.SellOrderComparator;
import com.gft.digitalbank.exchange.solution.order.comparators.TransactionComparator;

public class ProcessingState {

	private SortedSet<Transaction> transactions = new TreeSet<>(new TransactionComparator());
	private SortedSet<PositionOrder> awaitingSellOffers = new TreeSet<>(new SellOrderComparator());
	private SortedSet<PositionOrder> awaitingBuyOffers = new TreeSet<>(new BuyOrderComparator());
	private TransactionCounter transactionCounter = new TransactionCounter();
	private String productName;

	public ProcessingState(String productName) {
		this.productName = productName;
	}

	public SortedSet<PositionOrder> getAwaitingBuyOffers() {
		return awaitingBuyOffers;
	}

	public SortedSet<PositionOrder> getAwaitingSellOffers() {
		return awaitingSellOffers;
	}

	public String getProductName() {
		return productName;
	}

	public TransactionCounter getTransactionCounter() {
		return transactionCounter;
	}

	public SortedSet<Transaction> getTransactions() {
		return transactions;
	}

	public void setAwaitingBuyOffers(SortedSet<PositionOrder> awaitingBuyOffers) {
		this.awaitingBuyOffers = awaitingBuyOffers;
	}

	public void setAwaitingSellOffers(SortedSet<PositionOrder> awaitingSellOffers) {
		this.awaitingSellOffers = awaitingSellOffers;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setTransactionCounter(TransactionCounter transactionCounter) {
		this.transactionCounter = transactionCounter;
	}

	public void setTransactions(SortedSet<Transaction> transactions) {
		this.transactions = transactions;
	}

}
