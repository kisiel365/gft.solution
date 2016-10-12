package com.gft.digitalbank.exchange.solution.order.dto;

import com.gft.digitalbank.exchange.model.orders.CancellationOrder;
import com.gft.digitalbank.exchange.model.orders.PositionOrder;
import com.google.common.base.Objects;

public class ExtendedCancellationOrder extends CancellationOrder {

	private PositionOrder positionOrder;

	public ExtendedCancellationOrder(CancellationOrder cancellationOrder, PositionOrder positionOrder) {
		super(cancellationOrder.getMessageType(), cancellationOrder.getTimestamp(), cancellationOrder.getId(),
				cancellationOrder.getBroker(), cancellationOrder.getCancelledOrderId());
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
		ExtendedCancellationOrder other = (ExtendedCancellationOrder) obj;
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
