package app.model.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import app.model.User;

@Transactional
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

	  public User findByUsername(String username);
	  
	  public User findByUserRole(String userRole);
	  
	  public boolean exists(Integer userId);
	  
	  public Iterable<User> findAll(Sort sort);

	  public Page<User> findAll(Pageable pageable);

	  public long count();
	
}
