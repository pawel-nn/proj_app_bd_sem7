package app.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.dataTransportObject.ConsumerProductDTO;
import app.dataTransportObject.BuyProductsDTO;
import app.model.Order;
import app.model.Product;
import app.operations.OrderService;
import app.operations.ProductService;
import app.operations.ShipmentType;
import app.viewObject.ConsumerProductVO;
import app.viewObject.OrderVO;
import app.viewObject.BuyProductsVO;

@Controller
public class CustomerController {

	 @Autowired OrderService orderService;
	    
	    @Autowired ProductService productService;

	    @GetMapping("/")
	    String index(@RequestParam(value="page", required=false) Integer page, Model model) {
	        orderService.getClientProducts(page, model);
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) auth.getAuthorities();
			Iterator<SimpleGrantedAuthority> it = authorities.iterator();
	        String authority = null;
			if (it.hasNext()) {
				SimpleGrantedAuthority sga = (SimpleGrantedAuthority) it.next();
				authority = sga.getAuthority();
			}
			System.out.println("authority" + authority);
			model.addAttribute("authority", authority);
	        return "customer_product_list";
	    }

	    @GetMapping("/customer/order")
	    String viewOrder(Model model) {
	        Order order = orderService.getCurrentOrder();
	        model.addAttribute("order", new OrderVO(order));
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) auth.getAuthorities();
			Iterator<SimpleGrantedAuthority> it = authorities.iterator();
	        String authority = null;
			if (it.hasNext()) {
				SimpleGrantedAuthority sga = (SimpleGrantedAuthority) it.next();
				authority = sga.getAuthority();
			}
			System.out.println("authority" + authority);
			model.addAttribute("authority", authority);
	        return "customer_view_order";
	    }

	    @GetMapping("/customer/buy")
	    String buyOrderGET(BuyProductsVO buyProductsVO, Model model) {
	        Order order = orderService.getCurrentOrder();
	        model.addAttribute("shipmentTypeValues", ShipmentType.values());
	        model.addAttribute("order", new OrderVO(order));
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) auth.getAuthorities();
			Iterator<SimpleGrantedAuthority> it = authorities.iterator();
	        String authority = null;
			if (it.hasNext()) {
				SimpleGrantedAuthority sga = (SimpleGrantedAuthority) it.next();
				authority = sga.getAuthority();
			}
			System.out.println("authority" + authority);
			model.addAttribute("authority", authority);
	        return "customer_buy_order";
	    }

	    @PostMapping("/customer/buy")
	    String buyOrderPOST(BuyProductsVO buyProductsVO, Model model) {
	        Order order = orderService.getCurrentOrder();
	        buyProductsVO = orderService.buyProductsInOrder(new BuyProductsDTO(buyProductsVO)).getViewObject();
	        model.addAttribute("order", order);
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) auth.getAuthorities();
			Iterator<SimpleGrantedAuthority> it = authorities.iterator();
	        String authority = null;
			if (it.hasNext()) {
				SimpleGrantedAuthority sga = (SimpleGrantedAuthority) it.next();
				authority = sga.getAuthority();
			}
			System.out.println("authority" + authority);
			model.addAttribute("authority", authority);
	        return "customer_buy_order_result";
	    }

	    @GetMapping("/customer/order/deleteProduct")
	    String deleteProductFromOrderPOST(@RequestParam(value="productId", required=true) Integer productId, Model model) {
	        orderService.deleteProductFromOrder(productId);
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) auth.getAuthorities();
			Iterator<SimpleGrantedAuthority> it = authorities.iterator();
	        String authority = null;
			if (it.hasNext()) {
				SimpleGrantedAuthority sga = (SimpleGrantedAuthority) it.next();
				authority = sga.getAuthority();
			}
			System.out.println("authority" + authority);
			model.addAttribute("authority", authority);
	        return "redirect:/customer/order";
	    }

	    @GetMapping("/customer/addProduct")
	    String addProductToOrderGET(@RequestParam(value="productId", required=true) Integer productId, ConsumerProductVO consumerProductVO, Model model) {
	        Product product = productService.getProductById(productId);
	        model.addAttribute("product", product);
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) auth.getAuthorities();
			Iterator<SimpleGrantedAuthority> it = authorities.iterator();
	        String authority = null;
			if (it.hasNext()) {
				SimpleGrantedAuthority sga = (SimpleGrantedAuthority) it.next();
				authority = sga.getAuthority();
			}
			System.out.println("authority" + authority);
			model.addAttribute("authority", authority);
	        return "customer_add_product";    
	    }

	    @PostMapping("/customer/order/addProduct")
	    String addProductToOrderPOST(ConsumerProductVO consumerProductVO, Model model) {
	        orderService.addProductToOrder(new ConsumerProductDTO(consumerProductVO));
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) auth.getAuthorities();
			Iterator<SimpleGrantedAuthority> it = authorities.iterator();
	        String authority = null;
			if (it.hasNext()) {
				SimpleGrantedAuthority sga = (SimpleGrantedAuthority) it.next();
				authority = sga.getAuthority();
			}
			System.out.println("authority" + authority);
			model.addAttribute("authority", authority);
	        return "redirect:/customer/order";
	    }

	    @GetMapping("/customer/order/product/increase")
	    public String increaseProductAmmount(@RequestParam(value="productId", required=true) Integer productId) {
	        orderService.increaseProductAmmount(productId);
	        return "redirect:/customer/order";
	    }
	    
	    @GetMapping("/customer/order/product/decrease")
	    public String decreaseProductAmmount(@RequestParam(value="productId", required=true) Integer productId) {
	        orderService.decreaseProductAmmount(productId);
	        return "redirect:/customer/order";
	    }
	    
	    @GetMapping("/customer/archivedOrders")
	    public String viewArchivedOrderList(Model model) {
	        ArrayList<Order> archivedOrderList = orderService.getArchivedOrders();
	        model.addAttribute("archivedOrderList", archivedOrderList);
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) auth.getAuthorities();
			Iterator<SimpleGrantedAuthority> it = authorities.iterator();
	        String authority = null;
			if (it.hasNext()) {
				SimpleGrantedAuthority sga = (SimpleGrantedAuthority) it.next();
				authority = sga.getAuthority();
			}
			System.out.println("authority" + authority);
			model.addAttribute("authority", authority);
	        return "customer_view_archived_order_list";
	    }
	    
	    @GetMapping("/customer/archivedOrders/order")
	    public String decreaseProductAmmount(@RequestParam(value="archivedOrderId", required=true) Integer archivedOrderId, Model model) {
	        Order order = orderService.getArchivedOrderById(archivedOrderId);
	        model.addAttribute("order", order);
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) auth.getAuthorities();
			Iterator<SimpleGrantedAuthority> it = authorities.iterator();
	        String authority = null;
			if (it.hasNext()) {
				SimpleGrantedAuthority sga = (SimpleGrantedAuthority) it.next();
				authority = sga.getAuthority();
			}
			System.out.println("authority" + authority);
			model.addAttribute("authority", authority);
	        return "customer_view_archived_order";
	    }
	    
	}

