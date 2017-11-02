package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.dataTransportObject.ProductDTO;
import app.model.Product;
import app.operations.UserService;
import app.operations.owner.ProductService;
import app.viewObject.ProductVO;


@Controller
public class OwnerController {

	@Autowired
	private UserService userService;

	@Autowired ProductService productService;
	
    @GetMapping("/owner/productList")
    public String productList(@RequestParam(value="page", required=false) Integer page, Model model) {
    	productService.getProductsByPagination(page, model);
    	return "product_list";
    }
	
	@GetMapping("/owner/productList/addProduct")
	public String addNewProductGET(ProductVO productVO, Model m) {
		return "product_add";
	}

	@PostMapping("/owner/productList/addProduct")
	public String addNreProductPOST(ProductVO productVO, Model m, RedirectAttributes redirectAttributes) {
		ProductDTO productDTO = new ProductDTO(productVO);
		productDTO = productService.savePart(productDTO);
		if(productDTO == null) {
			m.addAttribute("msg", "Błąd, nie można utworzyć produktu!");
			return "product_add";	
		}
		redirectAttributes.addAttribute("productId", productDTO.getViewObject().getProductId());
		return "redirect:/owner/productList/product";
	}

	@GetMapping("/owner/productList/product")
	public String productGET(@RequestParam(value="productId", required=false) Integer productId, Model m) {
		if(!productService.existsProduct(productId))
			return "bad_request";
		Product product = productService.getProductById(productId);
		m.addAttribute("product", product);
		return "product";
	}
	
    @GetMapping("/owner/accountsControl")
    public String accountsControl(@RequestParam(value="page", required=false) Integer page, Model model) {
    	userService.getUsersByPagination(page, model);
    	return "accounts_control";
    }
    
    @GetMapping("/owner/accountsControl/blockUser")
    public String blockUser(@RequestParam(value="page", required=true) Integer page,
    						@RequestParam(value="username", required=true) String username) {
    	userService.blockUser(username);
    	return "redirect:/owner/accountsControl?page=" + page;
	}
    
    @GetMapping("/owner/accountsControl/unlockUser")
    public String unlockUser(@RequestParam(value="page", required=true) Integer page,
    						 @RequestParam(value="username", required=true) String username) {
    	userService.unlockUser(username);
    	return "redirect:/owner/accountsControl?page=" + page;
	}
	

}
