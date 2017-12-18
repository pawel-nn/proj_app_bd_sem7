package app.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.model.ProductList;

@Transactional
@Repository
public interface ProductListRepository extends JpaRepository<ProductList, Integer> {
    
}

