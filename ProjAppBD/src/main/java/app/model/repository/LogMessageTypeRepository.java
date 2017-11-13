package app.model.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.model.LogMessageType;

@Transactional
@Repository
public interface LogMessageTypeRepository extends JpaRepository<LogMessageType, Integer> {
	  
	  @Query("select lmt from LogMessageType lmt where lmt.messageType = :messageType")
	  LogMessageType findByMessageType(@Param("messageType") String messageType);
	
}
