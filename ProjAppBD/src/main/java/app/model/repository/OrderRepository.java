package app.model.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.model.Customer;
import app.model.Order;
import app.operations.OrderStatusType;


@Transactional
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

	public List<Order> findByOrderStatusAndCustomer(OrderStatusType orderStatus, Customer customer);

	public Order findByOrderIdAndOrderStatusAndCustomer(Integer orderId, OrderStatusType orderStatus, Customer customer);
	

}
