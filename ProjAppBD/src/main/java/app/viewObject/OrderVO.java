package app.viewObject;

import java.time.LocalDateTime;
import java.util.List;

import app.model.Customer;
import app.model.Order;
import app.model.ProductList;
import app.operations.OrderStatusType;
import app.operations.ShipmentType;
import lombok.Data;

public class OrderVO {

	public OrderVO(Order currentOrder) {
		if (currentOrder != null) {
			this.orderId = currentOrder.getOrderId();
			this.customer = currentOrder.getCustomer();
			this.productList = currentOrder.getProductList();
			this.orderStatus = currentOrder.getOrderStatus();
			this.shipmentType = currentOrder.getShipmentType();
			this.creationTime = currentOrder.getCreationTime();
		}
	}

	private Integer orderId;
	private Customer customer;
	private List<ProductList> productList;
	private OrderStatusType orderStatus;
	private ShipmentType shipmentType;
	private LocalDateTime creationTime;

	private String errorMsg;
	private boolean invalidOverall;
}
