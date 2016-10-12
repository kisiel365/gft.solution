package com.gft.digitalbank.exchange.solution.order.dto;

import java.util.List;

import com.gft.digitalbank.exchange.model.orders.PositionOrder;

public class ProductRelatedOrders extends ExtendedOrdersDto {

	private String product;

	public ProductRelatedOrders(String product, List<PositionOrder> positionOrders,
			List<ExtendedModificationOrder> modificationOrders, List<ExtendedCancellationOrder> cancellationOrders) {
		super(positionOrders, modificationOrders, cancellationOrders);
		this.product = product;
	}

	public String getProduct() {
		return product;
	}

}
