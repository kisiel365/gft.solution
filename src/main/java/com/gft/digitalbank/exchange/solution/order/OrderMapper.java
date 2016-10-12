package com.gft.digitalbank.exchange.solution.order;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.gft.digitalbank.exchange.model.orders.CancellationOrder;
import com.gft.digitalbank.exchange.solution.order.dto.ExtendedCancellationOrder;
import com.gft.digitalbank.exchange.solution.order.dto.ExtendedModificationOrder;
import com.gft.digitalbank.exchange.solution.order.dto.ExtendedOrdersDto;
import com.gft.digitalbank.exchange.solution.order.dto.OrdersDto;

public class OrderMapper {

	private OrdersDto ordersDto;

	public OrderMapper(OrdersDto ordersDto) {
		this.ordersDto = ordersDto;
	}

	public ExtendedOrdersDto map() {
		List<ExtendedCancellationOrder> extendedCancellationOrders = mapCancellationOrders();
		List<ExtendedModificationOrder> extendedModificationOrders = mapModificationOrders();
		return new ExtendedOrdersDto(ordersDto.getPositionOrders(), extendedModificationOrders,
				extendedCancellationOrders);
	}

	private List<ExtendedCancellationOrder> mapCancellationOrders() {
		Map<Integer, CancellationOrder> cancellationOrdersMap = ordersDto.getCancellationOrders().stream()
				.collect(Collectors.toMap(cancellationOrder -> cancellationOrder.getCancelledOrderId(),
						cancellationOrder -> cancellationOrder));
		return ordersDto.getPositionOrders().stream()
				.filter(positionOrder -> cancellationOrdersMap.containsKey(positionOrder.getId()))
				.map(po -> new ExtendedCancellationOrder(cancellationOrdersMap.get(po.getId()), po))
				.collect(Collectors.toList());
	}

	private List<ExtendedModificationOrder> mapModificationOrders() {
		return ordersDto.getModificationOrders().stream()
				.map(m -> new ExtendedModificationOrder(m, ordersDto.getPositionOrders().stream()
						.filter(po -> po.getId() == m.getModifiedOrderId()).findAny().orElse(null)))
				.collect(Collectors.toList());
	}

}
