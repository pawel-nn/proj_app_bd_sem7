package app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.dataTransportObject.ProductCategoryDTO;
import app.model.ProductCategory;
import app.operations.ProductCategoryService;
import app.viewObject.ProductCategoryVO;

@Controller
public class ProductCategoryController {

	@Autowired ProductCategoryService productCategoryService;
	
    @GetMapping("/owner/productCategoryList")
    public String productCategoryList(@RequestParam(value="page", required=false) Integer page, ProductCategoryVO productCategoryVO, Model model) {
    	productCategoryService.getProductCategoriesByPagination(page, model);
    	return "product_category_list";
    }

	@PostMapping("/owner/productCategoryList/addProductCategory")
	public String addNewProductCategoryPOST(ProductCategoryVO productCategoryVO, Model m) {
		ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO(productCategoryVO);
		productCategoryDTO = productCategoryService.saveProductCategory(productCategoryDTO);
		if(productCategoryDTO == null) {
			m.addAttribute("msg", "Nie można utworzyć nowej kategorii.");
			return "redirect:/owner/productCategoryList";
		}
		m.addAttribute("msg", "Dodano nową kategorię.");
		return "redirect:/owner/productCategoryList";
	}
	
    @GetMapping("/owner/productCategoryList/deleteProductCategory")
    public String deleteProductCategoryGET(@RequestParam(value="page", required=true) Integer page,
    							   @RequestParam(value="oId", required=true) Integer oId) {
    	productCategoryService.deleteProductCategory(oId);
    	return "redirect:/owner/productCategoryList?page=" + page;
	}
    
	@GetMapping("/owner/productCategoryList/updateProductCategory")
	public String addRegisterOwnerGET(@RequestParam(value="oId", required=true) Integer oId, ProductCategoryVO productCategoryVO, Model m) {
		ProductCategory productCategory = productCategoryService.getProductCategoryById(oId);
		productCategoryVO.setUp(productCategory.getProductCategoryName(), productCategory.getProductCategoryId());
		return "product_category_update";
	}

	@PostMapping("/owner/productCategoryList/updateProductCategory")
	public String addRegisterOwnerOST(@RequestParam(value="oId", required=false) Integer oId, ProductCategoryVO productCategoryVO, BindingResult bindingResult, Model m) {
		ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO(productCategoryVO);
		productCategoryDTO = productCategoryService.updateProductCategory(productCategoryDTO);
		if(productCategoryDTO == null) {
			m.addAttribute("result", "Błąd, nie można utworzyć nowej kategorii!");
			return "product_category_update";	
		}		
		m.addAttribute("result", "Utworzono nową kategorię");
		return "product_category_update";	
	}
	
}
