package app.model.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import app.model.ProductCategory;

@Transactional
@Repository
public interface ProductCategoryRepository extends PagingAndSortingRepository<ProductCategory, Integer> {

}
