package app.operations;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import app.dataTransportObject.ProductCategoryDTO;
import app.model.ProductCategory;
import app.model.repository.ProductCategoryRepository;
import app.validation.ProductCategoryValidation;

@Service
public class ProductCategoryService {

    private static int MAX_ROWS_PER_PAGE = 20;	
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    
	@Autowired
	private DatabaseLogService databaseLogService;
    
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
	
    @Autowired
    private ProductCategoryValidation productCategoryValidation;
    
    @Autowired
    private DictionaryService dictionaryRepository;
    
    public ArrayList<ProductCategory> getAllProductCategory() {
    	return (ArrayList<ProductCategory>) productCategoryRepository.findAll();
    }
    
    public ProductCategory getProductCategoryById(Integer productCategoryId) {
    	return (ProductCategory) productCategoryRepository.findByProductCategoryId(productCategoryId);
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
    	log.info("PCS: Get product category List.");
    	databaseLogService.info("PCS: Get product category List.");
	}

	public ProductCategoryDTO saveProductCategory(ProductCategoryDTO productCategoryDTO) {
		productCategoryDTO = productCategoryValidation.validateNewProduct(productCategoryDTO);
		if(productCategoryDTO.isValid()) {
			try {
				ProductCategory newProductCategory = new ProductCategory(productCategoryDTO.getViewObject().getProductCategoryName());
				newProductCategory = productCategoryRepository.save(newProductCategory);
				dictionaryRepository.saveDictionaryKeyword(productCategoryDTO.getViewObject().getProductCategoryName(),newProductCategory.getProductCategoryId(),DictionaryCategoryType.PRODUCT_CATEGORY.getName());
				log.info("PCS: New product category created: {}.", productCategoryDTO.getViewObject().getProductCategoryName());
				databaseLogService.info("PCS: New product category created: " + productCategoryDTO.getViewObject().getProductCategoryName());
				return productCategoryDTO;
			} catch (Exception e) {
				log.error("PCS: Product category: {} cannot be created.", productCategoryDTO.getViewObject().getProductCategoryName());
				databaseLogService.error("PCS: Product category: " +productCategoryDTO.getViewObject().getProductCategoryName()+ " cannot be created." );
				return null;
			}
		} else {
			return null;
		}
	}
	
	public ProductCategoryDTO updateProductCategory(ProductCategoryDTO productCategoryDTO) {
		productCategoryDTO = productCategoryValidation.validateNewProduct(productCategoryDTO);
		ProductCategory oldProductCategory = productCategoryRepository.findByProductCategoryId(productCategoryDTO.getViewObject().getUId());
		if(productCategoryDTO.isValid() && oldProductCategory != null) {
			try {
				ProductCategory newproductCategory = new ProductCategory(productCategoryDTO.getViewObject().getProductCategoryName(), productCategoryDTO.getViewObject().getUId());
				newproductCategory = productCategoryRepository.save(newproductCategory);
				dictionaryRepository.updateDictionaryKeyword(oldProductCategory.getProductCategoryName(), newproductCategory.getProductCategoryName());
				log.info("PCS: Product category update from {} to {}.", oldProductCategory.getProductCategoryName(), newproductCategory.getProductCategoryName());
				databaseLogService.info("PCS: New product category creaupdatedted: " + productCategoryDTO.getViewObject().getProductCategoryName());
				return productCategoryDTO;
			} catch (Exception e) {
				e.printStackTrace();
				log.error("PCS: Product category: {} cannot be updated to {}.", oldProductCategory.getProductCategoryName(), productCategoryDTO.getViewObject().getProductCategoryName());
				databaseLogService.error("PCS: Product category: "+oldProductCategory.getProductCategoryName()+" cannot be updated to " + productCategoryDTO.getViewObject().getProductCategoryName() );
				return null;
			}
		} else {
			log.error("PCS: Invalid request!");
			databaseLogService.error("PCS: Invalid request!");
			return null;
		}
	}

	public boolean deleteProductCategory(Integer productCategoryId) {
		try {
			ProductCategory productCategory = productCategoryRepository.findByProductCategoryId(productCategoryId);
			dictionaryRepository.deleteDictionaryKeyword(productCategory.getProductCategoryName());
			productCategoryRepository.delete(productCategory);
			log.info("PCS: Product category of id: {} was deleted.", productCategoryId);
			databaseLogService.info("PCS: Product category of id: " +productCategoryId+ "was deleted.");
			return true;
		} catch ( Exception e ) {
			log.error("PCS: Product category of id: {} cannot be deleted.", productCategoryId);
			databaseLogService.info("PCS: Product category of id: " +productCategoryId+ " cannot be deleted.");
			return false;
		}		
	}

}
