package com.gft.digitalbank.exchange.solution.order;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import com.gft.digitalbank.exchange.model.orders.PositionOrder;
import com.gft.digitalbank.exchange.solution.order.dto.ExtendedCancellationOrder;
import com.gft.digitalbank.exchange.solution.order.dto.ExtendedModificationOrder;
import com.gft.digitalbank.exchange.solution.order.dto.ExtendedOrdersDto;
import com.gft.digitalbank.exchange.solution.order.dto.ProductRelatedOrders;

public class OrderGroupper {

	private ExtendedOrdersDto extendedOrdersDto;

	public OrderGroupper(ExtendedOrdersDto extendedOrdersDto) {
		this.extendedOrdersDto = extendedOrdersDto;
	}

	public Set<ProductRelatedOrders> groupOrdersByProducts() {
		Map<String, List<ExtendedCancellationOrder>> extendedCancellationOrdersGruppedByProduct = groupCancellationOrdersByProduct(
				extendedOrdersDto.getCancellationOrders());
		Map<String, List<ExtendedModificationOrder>> extendedModificationOrdersGruppedByProduct = groupModificationOrdersByProduct(
				extendedOrdersDto.getModificationOrders());
		Map<String, List<PositionOrder>> positionOrdersGruppedByProduct = groupPositionOrdersByProduct(
				extendedOrdersDto.getPositionOrders());
		return constructSingleDto(extendedCancellationOrdersGruppedByProduct,
				extendedModificationOrdersGruppedByProduct, positionOrdersGruppedByProduct);
	}

	private Set<ProductRelatedOrders> constructSingleDto(
			Map<String, List<ExtendedCancellationOrder>> extendedCancellationOrdersGruppedByProduct,
			Map<String, List<ExtendedModificationOrder>> extendedModificationOrdersGruppedByProduct,
			Map<String, List<PositionOrder>> positionOrdersGruppedByProduct) {
		Set<ProductRelatedOrders> productRelatedOrdersSet = new HashSet<>();
		for (Entry<String, List<PositionOrder>> positionOrdersOfTheSameProductEntry : positionOrdersGruppedByProduct
				.entrySet()) {
			String product = positionOrdersOfTheSameProductEntry.getKey();
			List<PositionOrder> positionOrdersOfTheSameProduct = positionOrdersOfTheSameProductEntry.getValue();

			List<ExtendedModificationOrder> extendedModificationOrdersOfTheSameProduct = extendedModificationOrdersGruppedByProduct
					.get(product);
			List<ExtendedCancellationOrder> extendedCancellationOrdersOfTheSameProduct = extendedCancellationOrdersGruppedByProduct
					.get(product);
			ProductRelatedOrders productRelatedOrders = new ProductRelatedOrders(product,
					positionOrdersOfTheSameProduct, extendedModificationOrdersOfTheSameProduct,
					extendedCancellationOrdersOfTheSameProduct);
			productRelatedOrdersSet.add(productRelatedOrders);
		}
		return productRelatedOrdersSet;
	}

	private Map<String, List<ExtendedCancellationOrder>> groupCancellationOrdersByProduct(
			List<ExtendedCancellationOrder> extendedCancellationOrders) {
		return extendedCancellationOrders.stream().collect(Collectors
				.groupingBy(extendedCancellationOrder -> extendedCancellationOrder.getPositionOrder().getProduct()));
	}

	private Map<String, List<ExtendedModificationOrder>> groupModificationOrdersByProduct(
			List<ExtendedModificationOrder> extendedModificationOrders) {
		return extendedModificationOrders.stream().collect(Collectors
				.groupingBy(extendedModificationOrder -> extendedModificationOrder.getPositionOrder().getProduct()));
	}

	private Map<String, List<PositionOrder>> groupPositionOrdersByProduct(List<PositionOrder> positionOrders) {
		return positionOrders.stream().collect(Collectors.groupingBy(positionOrder -> positionOrder.getProduct()));
	}
}
