package app.operations;

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import app.dataTransportObject.BuyProductsDTO;
import app.dataTransportObject.ConsumerProductDTO;
import app.dataTransportObject.OrderDTO;
import app.model.Customer;
import app.model.Order;
import app.model.Product;
import app.model.ProductList;
import app.model.User;
import app.model.repository.CustomerRepository;
import app.model.repository.OrderRepository;
import app.model.repository.ProductListRepository;
import app.model.repository.ProductRepository;
import app.model.repository.UserRepository;
import app.viewObject.ConsumerProductVO;
import app.viewObject.OrderVO;
import groovy.transform.Synchronized;

@Service
public class OrderService {


	private static int MAX_ROWS_PER_PAGE = 10;
	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	private DatabaseLogService databaseLogService;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductListRepository productListRepository;

	public OrderDTO addProductToOrder(ConsumerProductDTO consumerProductDTO) {
		Order currentOrder = getCurrentOrder();
		if (currentOrder != null) {
			List<ProductList> orderProductList = currentOrder.getProductList();
			ConsumerProductVO consumerProductVO = consumerProductDTO.getViewObject();
			Boolean present = false;
			for (ProductList productList : orderProductList) {
				if (productList.getProduct().getProductId() == consumerProductVO.getProductId()) {
					present = true;
					/* Already in the order ! */
					return null;
				}
			}
			if (present == false) {
				ProductList newConsumerProduct = new ProductList(0, new BigDecimal(0));
				Product product = productRepository.findByProductId(consumerProductVO.getProductId());
				if (product != null) {
					if (newConsumerProduct.getNumberOfProducts() + consumerProductVO.getNumberOfProducts() <= product
							.getStockSize()) {
						newConsumerProduct.setNumberOfProducts(consumerProductVO.getNumberOfProducts());
						newConsumerProduct.setTotalPrice(
								product.getPrice().multiply(new BigDecimal(consumerProductVO.getNumberOfProducts())));
						newConsumerProduct.setOrder(currentOrder);
						newConsumerProduct.setProduct(product);
						newConsumerProduct = productListRepository.saveAndFlush(newConsumerProduct);
						orderProductList.add(newConsumerProduct);
						currentOrder.setProductList(orderProductList);
						// productListRepository.saveAndFlush(newConsumerProduct);
						orderRepository.saveAndFlush(currentOrder);
						return new OrderDTO(new OrderVO(currentOrder));
					}
				}
			}
		}
		return null;
	}

	public OrderDTO deleteProductFromOrder(Integer productId) {
		Order currentOrder = getCurrentOrder();
		if (currentOrder != null) {
			List<ProductList> orderProductList = currentOrder.getProductList();
			for (ProductList productList : orderProductList) {
				if (productList.getProduct().getProductId() == productId) {
					orderProductList.remove(productList);
					currentOrder.setProductList(orderProductList);
					productListRepository.delete(productId);
					orderRepository.save(currentOrder);
					return new OrderDTO(new OrderVO(currentOrder));
				}
			}
		}
		return null;
	}

	@Synchronized
	public BuyProductsDTO buyProductsInOrder(BuyProductsDTO buyProductsDTO) {
		Order currentOrder = getCurrentOrder();
		if (currentOrder != null) {
			List<ProductList> orderProductList = currentOrder.getProductList();
			BigDecimal totalCost = new BigDecimal(0);
			if (orderProductList.size() > 0) {
				for (ProductList productList : orderProductList) {
					if (productList.getNumberOfProducts() <= productList.getProduct().getStockSize()) {
						totalCost = totalCost.add(productList.getTotalPrice());
						productList.getProduct().setStockSize(
								productList.getProduct().getStockSize() - productList.getNumberOfProducts());
					} else {
						// Can't buy product's, due to lack of them
						return null;
					}
				}
				buyProductsDTO.getViewObject().setTotalCost(totalCost);
				currentOrder.setProductList(orderProductList);
				currentOrder.setOrderStatus(OrderStatusType.PAID);
				currentOrder.setShipmentType(ShipmentType.valueOf(buyProductsDTO.getViewObject().getShipmentType()));
				currentOrder.setCreationTime(LocalDateTime.now());
				orderRepository.save(currentOrder);
				return buyProductsDTO;
			}
			return null;
		}
		return null;
	}

	public void getClientProducts(Integer pageReq, Model model) {
		int usersNumber = (int) productRepository.count();
		int maxPagesNumber = (int) (Math.ceil(1.0 * usersNumber / MAX_ROWS_PER_PAGE));
		int pageNumber = 1;
		if (maxPagesNumber == 0)
			maxPagesNumber = 1;
		if (pageReq != null)
			pageNumber = pageReq < 1 ? 1 : pageReq > maxPagesNumber ? maxPagesNumber : pageReq;
		Page<Product> productList = (Page<Product>) productRepository
				.findAll(new PageRequest(pageNumber - 1, MAX_ROWS_PER_PAGE, Sort.Direction.ASC, "name"));
		if (productList.getNumberOfElements() == 0)
			model.addAttribute("isEmpty", true);
		else
			model.addAttribute("isEmpty", false);
		Order currentOrder = getCurrentOrder();
		Customer customer = getCurrentCustomer();
		model.addAttribute("order", currentOrder);
		model.addAttribute("customer", customer);
		model.addAttribute("productList", productList);
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("maxPagesNumber", maxPagesNumber);
		log.info("PtS: Get product List.");
		databaseLogService.info("PtS: Get product List.");
	}

	public Order getCurrentOrder() {
		Customer customer = getCurrentCustomer();
		if (customer != null) {
			ArrayList<Order> orderList = (ArrayList<Order>) orderRepository
					.findByOrderStatusAndCustomer(OrderStatusType.NEW, customer);
			Order order = orderList.size() > 0 ? orderList.get(0) : null;
			if (order == null) {
				order = new Order(customer, OrderStatusType.NEW);
				order = orderRepository.save(order);
			}
			return order;
		} else
			return null;
	}

	public ArrayList<Order> getArchivedOrders() {
		Customer customer = getCurrentCustomer();
		if (customer != null)
			return (ArrayList<Order>) orderRepository.findByOrderStatusAndCustomer(OrderStatusType.PAID, customer);
		return new ArrayList<Order>();
	}

	public Order getArchivedOrderById(Integer archivedOrderId) {
		Customer customer = getCurrentCustomer();
		if (customer != null)
			return orderRepository.findByOrderIdAndOrderStatusAndCustomer(archivedOrderId, OrderStatusType.PAID,
					customer);
		return null;
	}

	private Customer getCurrentCustomer() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		if (StringUtils.isNoneBlank(username)) {
			User user = userRepository.findByUsername(username);
			if (user != null) {
				return customerRepository.findByUser(user);
			}
		}
		return null;
	}

	public void increaseProductAmmount(Integer productId) {
		Order currentOrder = getCurrentOrder();
		if (currentOrder != null) {
			List<ProductList> orderProductList = currentOrder.getProductList();
			for (ProductList productList : orderProductList) {
				if (productList.getProduct().getProductId() == productId) {
					Product product = productList.getProduct();
					if (productList.getNumberOfProducts() >= 1) {
						productList.setNumberOfProducts(productList.getNumberOfProducts() + 1);
						productList.setTotalPrice(productList.getTotalPrice().add(product.getPrice()));
					}
					currentOrder.setProductList(orderProductList);
					orderRepository.save(currentOrder);
				}
			}
		}
	}

	public void decreaseProductAmmount(Integer productId) {
		Order currentOrder = getCurrentOrder();
		if (currentOrder != null) {
			List<ProductList> orderProductList = currentOrder.getProductList();
			for (ProductList productList : orderProductList) {
				if (productList.getProduct().getProductId() == productId) {
					Product product = productList.getProduct();
					if (productList.getNumberOfProducts() >= 1) {
						productList.setNumberOfProducts(productList.getNumberOfProducts() - 1);
						productList.setTotalPrice(productList.getTotalPrice().subtract(product.getPrice()));
					}
					currentOrder.setProductList(orderProductList);
					orderRepository.save(currentOrder);
				}
			}
		}
	}

}
