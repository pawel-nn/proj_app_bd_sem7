package app.operationsTest;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import app.dataTransportObject.ProductDTO;
import app.model.Producer;
import app.model.Product;
import app.model.ProductCategory;
import app.model.ProductImage;
import app.model.repository.ProducerRepository;
import app.model.repository.ProductCategoryRepository;
import app.model.repository.ProductImageRepository;
import app.model.repository.ProductRepository;
import app.operations.DatabaseLogService;
import app.operations.ProductService;
import app.viewObject.ProductVO;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

	@MockBean
	private ProductCategoryRepository productCategoryRepository;	
	
	@MockBean
	private ProductRepository productRepository;

	@MockBean
	private ProducerRepository producerRepository;

	@MockBean
	private ProductImageRepository productImageRepository;
    
	@MockBean
	private DatabaseLogService databaseLogService;
	
    @Before
    public void beforeTest() {
    	ProductCategory pc = new ProductCategory("Category");
    	Producer p = new Producer("Producer");
    	ProductImage pi = new ProductImage("no_photo");
    	
        Mockito
    		.when(productImageRepository.findByProductImageName("file.png"))
    		.thenReturn(null);
        
        Mockito
			.when(productCategoryRepository.findByProductCategoryId(1))
			.thenReturn(pc);
        
        Mockito
        	.when(producerRepository.findByProducerId(1))
        	.thenReturn(p);
        
        Mockito
        	.when(productRepository.save(new Product(pi, pc, p, "Product name", new BigDecimal(100), 25, "012345678910",null)))
        	.thenReturn(new Product(pi, pc, p, "Product name", new BigDecimal(100), 25, "012345678910", 1));
    }
    
    @Test
    public void saveProductTest() {
		ProductVO productVO = new ProductVO(1, 1, "Product name", "100", 25, "012345678910");
		ProductDTO productDTO = new ProductDTO(productVO);
		MultipartFile productPhoto = new MockMultipartFile("file.png", "file.png", "image/png", new byte[0]);
		productDTO = productService.saveProduct(productPhoto, productDTO);
		Assert.assertTrue(productDTO.isValid());
     }

}
