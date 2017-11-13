package app.operationsTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import app.dataTransportObject.ProductCategoryDTO;
import app.model.ProductCategory;
import app.model.repository.DictionaryRepository;
import app.model.repository.ProductCategoryRepository;
import app.operations.DatabaseLogService;
import app.operations.ProductCategoryService;
import app.viewObject.ProductCategoryVO;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceTest {

	@Autowired
	ProductCategoryService productCategoryService;
	
	@MockBean
	private ProductCategoryRepository productCategoryRepository;	

	@MockBean
    private DictionaryRepository dictionaryRepository;
	
	@MockBean
	private DatabaseLogService databaseLogService;
	
	@Before
	public void beforeTest() {
        Mockito
    		.when(productCategoryRepository.save(new ProductCategory("Category")))
    		.thenReturn(new ProductCategory(1,"Category"));
	}

	@Test
	public void saveProductCategoryTest() {
		ProductCategoryVO productCategoryVO = new ProductCategoryVO("Category");
		ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO(productCategoryVO);
		productCategoryDTO = productCategoryService.saveProductCategory(productCategoryDTO);
		Assert.assertFalse(productCategoryDTO.getViewObject().isInvalidOverall());
	}

}
