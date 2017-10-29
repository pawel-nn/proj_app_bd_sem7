package app.model.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import app.model.Product;

@Transactional
@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {

	public Product findByProductId(Integer productId);

}
