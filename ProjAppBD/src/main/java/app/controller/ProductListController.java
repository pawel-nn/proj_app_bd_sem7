package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
		return "product_list";
	}
	//process html form
	
	//

}
