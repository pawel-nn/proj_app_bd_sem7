package app.operations;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import app.dataTransportObject.ProductCategoryDTO;
import app.model.Producer;
import app.model.ProductCategory;
import app.model.repository.DictionaryRepository;
import app.model.repository.ProductCategoryRepository;
import app.validation.ProductCategoryValidation;

@Service
public class ProductCategoryService {

    private static int MAX_ROWS_PER_PAGE = 20;	
	
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
	
    @Autowired
    private ProductCategoryValidation productCategoryValidation;
    
    @Autowired
    private DictionaryService dictionaryRepository;
    
    public ArrayList<ProductCategory> findAllProductCategory() {
    	return (ArrayList<ProductCategory>) productCategoryRepository.findAll();
    }
    
	public void getProductCategoriesByPagination(Integer pageReq, Model model) {
    	int usersNumber = (int) productCategoryRepository.count();
    	int maxPagesNumber = (int) (Math.ceil(1.0*usersNumber/MAX_ROWS_PER_PAGE));
    	int pageNumber = 1;
    	if( maxPagesNumber == 0 )
    		maxPagesNumber = 1;
    	if(pageReq != null)
    		pageNumber = pageReq;
    	Page<ProductCategory> productCategoryList = (Page<ProductCategory>) productCategoryRepository.findAll(new PageRequest(pageNumber - 1, MAX_ROWS_PER_PAGE, Sort.Direction.DESC, "productCategoryId"));
    	if(productCategoryList.getNumberOfElements() == 0) 
    		model.addAttribute("isEmpty", true); 
    	else 
    		model.addAttribute("isEmpty", false);
    	model.addAttribute("productCategoryList", productCategoryList);
    	model.addAttribute("pageNumber",pageNumber);
    	model.addAttribute("maxPagesNumber",maxPagesNumber); 
	}

	public ProductCategoryDTO saveProductCategory(ProductCategoryDTO productCategoryDTO) {
		productCategoryDTO = productCategoryValidation.validateNewProduct(productCategoryDTO);
		if(productCategoryDTO.isValid()) {
			try {
				ProductCategory productCategory = new ProductCategory(productCategoryDTO.getViewObject().getProductCategoryName());
				productCategory = productCategoryRepository.save(productCategory);
				dictionaryRepository.saveDictionaryKeyword(productCategoryDTO.getViewObject().getProductCategoryName(),productCategory.getProductCategoryId(),DictionaryCategoryType.PRODUCT_CATEGORY.getName());
			return productCategoryDTO;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}

	public boolean deleteProductCategory(Integer productCategoryId) {
		try {
			ProductCategory productCategory = productCategoryRepository.findByProductCategoryId(productCategoryId);
			productCategoryRepository.delete(productCategory);
			return true;
		} catch ( Exception e ) {
			return false;
		}		
	}

}
