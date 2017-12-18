package app.model.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.model.Customer;
import app.model.User;

@Transactional
@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

	public Customer findByUser(User user);
}
