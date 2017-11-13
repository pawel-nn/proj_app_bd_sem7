package app.controller;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.operations.ProductService;

@Controller
public class ProductListController {
	
	@Autowired ProductService productService;
	// show initial form
	@RequestMapping("/showProductList")
	public String showProductList(@RequestParam(value="page", required=false) Integer page, Model model){
		productService.getProductsByPagination(page, model);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) auth.getAuthorities();
		Iterator<SimpleGrantedAuthority> it = authorities.iterator();
		String authority = null;
		if( it.hasNext() ) {
			SimpleGrantedAuthority sga = (SimpleGrantedAuthority) it.next();
			authority = sga.getAuthority();
		} 
		model.addAttribute("authority", authority);
		
		return "product_list_client";
	}
	//process html form
	
	//

}
