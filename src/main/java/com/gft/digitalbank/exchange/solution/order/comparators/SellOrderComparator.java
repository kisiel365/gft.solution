package com.gft.digitalbank.exchange.solution.order.comparators;

import java.util.Comparator;

import com.gft.digitalbank.exchange.model.orders.PositionOrder;

public class SellOrderComparator implements Comparator<PositionOrder> {

	@Override
	public int compare(PositionOrder o1, PositionOrder o2) {
		int compare = Integer.compare(o1.getDetails().getPrice(), o2.getDetails().getPrice());
		if (compare != 0)
			return compare;
		return Long.compare(o1.getTimestamp(), o2.getTimestamp());
	}

}
