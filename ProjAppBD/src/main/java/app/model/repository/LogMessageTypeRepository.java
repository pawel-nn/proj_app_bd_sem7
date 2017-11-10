package app.model.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.model.LogMessageType;

@Transactional
@Repository
public interface LogMessageTypeRepository extends CrudRepository<LogMessageType, Integer> {

	  public LogMessageType findByMessageType(String messageType);
	
}
