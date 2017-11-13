package app.model.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.model.LogMessage;

@Transactional
@Repository
public interface LogMessageRepository extends CrudRepository<LogMessage, Integer> {

}
