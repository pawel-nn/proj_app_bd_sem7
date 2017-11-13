package app.repositoryTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import app.model.ProductCategory;
import app.model.repository.ProductCategoryRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
public class ProductCategoryRepositoryTest {
	
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    
    @Before
    public void beforeTest() {

    }
    
    @Test
    public void saveProductCategoryTest() {
    	ProductCategory productCategory = new ProductCategory("RTV");
    	productCategory = productCategoryRepository.save(productCategory);
    	Assert.assertEquals("RTV", productCategoryRepository.findByProductCategoryName("RTV").getProductCategoryName());
    }

}
