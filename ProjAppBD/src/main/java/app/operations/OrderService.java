package app.operations;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
	
	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	@Autowired
	private DatabaseLogService databaseLogService;

}
