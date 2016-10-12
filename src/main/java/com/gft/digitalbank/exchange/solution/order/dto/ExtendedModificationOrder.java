package com.gft.digitalbank.exchange.solution.order.dto;

import com.gft.digitalbank.exchange.model.orders.ModificationOrder;
import com.gft.digitalbank.exchange.model.orders.PositionOrder;
import com.google.common.base.Objects;

public class ExtendedModificationOrder extends ModificationOrder {

	private PositionOrder positionOrder;

	public ExtendedModificationOrder(ModificationOrder modificationOrder, PositionOrder positionOrder) {
		super(modificationOrder.getTimestamp(), modificationOrder.getId(), modificationOrder.getBroker(),
				modificationOrder.getMessage(), modificationOrder.getModifiedOrderId(), modificationOrder.getDetails());
		this.positionOrder = positionOrder;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExtendedModificationOrder other = (ExtendedModificationOrder) obj;
		return Objects.equal(positionOrder, other.positionOrder);
	}

	public PositionOrder getPositionOrder() {
		return positionOrder;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(super.hashCode(), positionOrder);
	}

	public void setPositionOrder(PositionOrder positionOrder) {
		this.positionOrder = positionOrder;
	}

}
