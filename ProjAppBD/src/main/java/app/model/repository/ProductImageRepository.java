package app.model.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.model.ProductImage;

@Transactional
@Repository
public interface ProductImageRepository extends CrudRepository<ProductImage, Integer> {
	
	

}
