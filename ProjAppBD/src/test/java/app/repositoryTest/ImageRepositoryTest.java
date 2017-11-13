package app.repositoryTest;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import app.model.ProductImage;
import app.model.repository.ProductImageRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
public class ImageRepositoryTest {

    @Autowired
    private ProductImageRepository productImageRepository;
    
    @Before
    public void beforeTest() {
    	
    }
    
    @Test
    public void saveProductImageTest() {
    	MultipartFile productPhoto = new MockMultipartFile("new_file.png", "new_file.png", "image/png", new byte[8]);
        ProductImage productImage = new ProductImage(productPhoto.getOriginalFilename());
        productImage = productImageRepository.save(productImage);
    	Assert.assertEquals("new_file.png", productImageRepository.findByProductImageName(productImage.getProductImageName()).getProductImageName());
    	Assert.assertEquals(productImage.getProductImageId(), productImageRepository.findByProductImageName(productImage.getProductImageName()).getProductImageId());
    }

}
