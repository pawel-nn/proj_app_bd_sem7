package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.dataTransportObject.ProductCategoryDTO;
import app.dataTransportObject.ProductDTO;
import app.model.Product;
import app.operations.ProductCategoryService;
import app.operations.ProductService;
import app.viewObject.ProductCategoryVO;
import app.viewObject.ProductVO;

@Controller
public class ProductCategoryController {

	@Autowired ProductCategoryService productCategoryService;
	
    @GetMapping("/owner/productCategoryList")
    public String productCategoryList(@RequestParam(value="page", required=false) Integer page, Model model) {
    	productCategoryService.getProductCategoriesByPagination(page, model);
    	return "product_category_list";
    }

	@PostMapping("/owner/productCategoryList/addProductCategory")
	public String addNewProductCategoryPOST(ProductCategoryVO productCategoryVO, Model m) {
		ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO(productCategoryVO);
		productCategoryDTO = productCategoryService.saveProductCategory(productCategoryDTO);
		if(productCategoryDTO == null) {
			m.addAttribute("msg", "Nie można utworzyć nowej kategorii.");
			return "product_category_list";	
		}
		m.addAttribute("msg", "Dodano nową kategorię.");
		return "product_category_list";
	}
	
    @GetMapping("/owner/productCategoryList/deleteProductCategory")
    public String deleteProductCategoryGET(@RequestParam(value="page", required=true) Integer page,
    							   @RequestParam(value="productCategoryId", required=true) Integer productCategoryId) {
    	productCategoryService.deleteProductCategory(productCategoryId);
    	return "redirect:/owner/productList?page=" + page;
	}
	
}
