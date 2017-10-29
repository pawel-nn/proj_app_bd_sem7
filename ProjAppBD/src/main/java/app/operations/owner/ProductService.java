package app.operations.owner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import app.dataTransportObject.ProductDTO;
import app.model.Product;
import app.model.repository.ProductRepository;
import app.validation.ProductValidation;

@Service
public class ProductService {

    private static int MAX_ROWS_PER_PAGE = 10;	
    
    @Autowired
    private ProductRepository productRepository;
	
    @Autowired
    private ProductValidation productValidation;
    
	public ProductDTO savePart(ProductDTO productDTO) {
		productDTO = productValidation.validateNewProduct(productDTO);
		if(productDTO.isValid()) {
			try {
			Product product = productRepository.save(new Product(
					productDTO.getViewObject().getName(), productDTO.getViewObject().getValidatedPrice(),
					productDTO.getViewObject().getStockSize(), productDTO.getViewObject().getCode()));
			productDTO.getViewObject().setProductId(product.getProductId());
			return productDTO;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}

	public void getProductsByPagination(Integer pageReq, Model model) {
    	int usersNumber = (int) productRepository.count();
    	int maxPagesNumber = (int) (Math.ceil(1.0*usersNumber/MAX_ROWS_PER_PAGE));
    	int pageNumber = 1;
    	if( maxPagesNumber == 0 )
    		maxPagesNumber = 1;
    	if(pageReq != null)
    		pageNumber = pageReq;
    	Page<Product> productList = (Page<Product>) productRepository.findAll(new PageRequest(pageNumber - 1, MAX_ROWS_PER_PAGE, Sort.Direction.ASC, "name"));
    	if(productList.getNumberOfElements() == 0) 
    		model.addAttribute("isEmpty", true); 
    	else 
    		model.addAttribute("isEmpty", false);
    	model.addAttribute("productList", productList);
    	model.addAttribute("pageNumber",pageNumber);
    	model.addAttribute("maxPagesNumber",maxPagesNumber);   
	}

	public boolean existsProduct(Integer productId) {
		if(productRepository.exists(productId))
			return true;
		else 
			return false;
	}

	public Product getProductById(Integer productId) {
		return productRepository.findByProductId(productId);
	}

}
