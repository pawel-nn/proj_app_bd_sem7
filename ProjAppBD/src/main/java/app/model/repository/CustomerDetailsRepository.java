package app.model.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.model.CustomerDetails;

@Transactional
@Repository
public interface CustomerDetailsRepository extends CrudRepository<CustomerDetails, Integer> {

}
