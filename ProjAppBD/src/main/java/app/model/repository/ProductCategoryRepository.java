package app.model.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import app.model.ProductCategory;

@Transactional
@Repository
public interface ProductCategoryRepository extends PagingAndSortingRepository<ProductCategory, Integer> {

	public Page<ProductCategory> findAll(Pageable pageable);

	ProductCategory findByProductCategoryId(Integer productCategoryId);

	public ProductCategory findByProductCategoryName(String string);
	
}
