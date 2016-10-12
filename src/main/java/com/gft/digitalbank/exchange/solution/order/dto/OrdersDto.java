package com.gft.digitalbank.exchange.solution.order.dto;

import java.util.Collections;
import java.util.List;

import com.gft.digitalbank.exchange.model.orders.CancellationOrder;
import com.gft.digitalbank.exchange.model.orders.ModificationOrder;
import com.gft.digitalbank.exchange.model.orders.PositionOrder;

public class OrdersDto {

	private List<PositionOrder> positionOrders;
	private List<ModificationOrder> modificationOrders;
	private List<CancellationOrder> cancellationOrders;

	public OrdersDto(List<PositionOrder> positionOrders, List<ModificationOrder> modificationOrders,
			List<CancellationOrder> cancellationOrders) {
		this.positionOrders = positionOrders;
		this.modificationOrders = modificationOrders;
		this.cancellationOrders = cancellationOrders;
		if (this.modificationOrders == null)
			this.modificationOrders = Collections.emptyList();
		if (this.cancellationOrders == null)
			this.cancellationOrders = Collections.emptyList();
	}

	public List<CancellationOrder> getCancellationOrders() {
		return cancellationOrders;
	}

	public List<ModificationOrder> getModificationOrders() {
		return modificationOrders;
	}

	public List<PositionOrder> getPositionOrders() {
		return positionOrders;
	}

	public void setCancellationOrders(List<CancellationOrder> cancellationOrders) {
		this.cancellationOrders = cancellationOrders;
	}

	public void setModificationOrders(List<ModificationOrder> modificationOrders) {
		this.modificationOrders = modificationOrders;
	}

	public void setPositionOrders(List<PositionOrder> positionOrders) {
		this.positionOrders = positionOrders;
	}

}
