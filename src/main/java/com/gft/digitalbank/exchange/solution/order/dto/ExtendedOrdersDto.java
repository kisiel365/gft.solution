package com.gft.digitalbank.exchange.solution.order.dto;

import java.util.Collections;
import java.util.List;

import com.gft.digitalbank.exchange.model.orders.PositionOrder;

public class ExtendedOrdersDto {

	private List<PositionOrder> positionOrders;
	private List<ExtendedModificationOrder> modificationOrders;
	private List<ExtendedCancellationOrder> cancellationOrders;

	public ExtendedOrdersDto(List<PositionOrder> positionOrders, List<ExtendedModificationOrder> modificationOrders,
			List<ExtendedCancellationOrder> cancellationOrders) {
		this.positionOrders = positionOrders;
		this.modificationOrders = modificationOrders;
		this.cancellationOrders = cancellationOrders;
		if (this.modificationOrders == null)
			this.modificationOrders = Collections.emptyList();
		if (this.cancellationOrders == null)
			this.cancellationOrders = Collections.emptyList();
	}

	public List<ExtendedCancellationOrder> getCancellationOrders() {
		return cancellationOrders;
	}

	public List<ExtendedModificationOrder> getModificationOrders() {
		return modificationOrders;
	}

	public List<PositionOrder> getPositionOrders() {
		return positionOrders;
	}

}
