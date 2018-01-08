package app.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import app.operations.OrderStatusType;
import app.operations.ShipmentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

	public Order(Customer customer, OrderStatusType orderStatusType) {
		this.customer = customer;
		this.orderStatus = orderStatusType;
		this.productList = new ArrayList<ProductList>();
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "order_id", unique = true, nullable = false)
	private Integer orderId;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
	private List<ProductList> productList;

	@Column(name = "order_status")
	private OrderStatusType orderStatus;

	@Column(name = "shipment_type")
	private ShipmentType shipmentType;

	@Column(name = "creation_time")
	private LocalDateTime creationTime;
	
	public BigDecimal getTotalPrice() {
	   	 List<ProductList> orderProductList = this.getProductList();
	   	 BigDecimal totalCost = new BigDecimal(0);
	   	 if (orderProductList.size() > 0) {
	   		 for (ProductList productList : orderProductList) {
	   			 if (productList.getNumberOfProducts() <= productList.getProduct().getStockSize()) {
	   				 totalCost = totalCost.add(productList.getTotalPrice());
	   			 }
	   		 }
	   	 }
	   	 return totalCost;
	    }


}
