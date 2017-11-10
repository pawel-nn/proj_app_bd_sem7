package app.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.model.LogMessage;
import app.model.LogMessageType;
import app.model.repository.LogMessageRepository;
import app.model.repository.LogMessageTypeRepository;

@Service
public class DatabaseLogService {
	
	@Autowired
	private LogMessageTypeRepository logMessageTypeRepository;
	
	@Autowired
	private LogMessageRepository logMessageRepository;
	
	public void logToDatabase(String msg, Integer msgType) {
		try {
		LogMessageType lmt = null;
		switch (msgType) {
            case 0:  
            	lmt = logMessageTypeRepository.findByMessageType("INFO");
            	if(lmt == null)
            		lmt = logMessageTypeRepository.save(new LogMessageType("INFO"));
                break;
            case 1:  
            	lmt = logMessageTypeRepository.findByMessageType("ERROR");
            	if(lmt == null)
            		lmt = logMessageTypeRepository.save(new LogMessageType("ERROR"));     
            	break;
            case 2:  
            	lmt = logMessageTypeRepository.findByMessageType("WARN");
            	if(lmt == null)
            		lmt = logMessageTypeRepository.save(new LogMessageType("WARN"));     
            	break;
            case 3:  
            	lmt = logMessageTypeRepository.findByMessageType("DEBUG");
            	if(lmt == null)
            		lmt = logMessageTypeRepository.save(new LogMessageType("DEBUG"));     
            	break;
            default: break;
		}
            logMessageRepository.save(new LogMessage(msg,lmt));
		} catch(Exception e) {}
	}
	
	public void info(String msg) {
		logToDatabase(msg,0);
	}
	
	public void error(String msg) {
		logToDatabase(msg,1);
	}
	
	public void warn(String msg) {
		logToDatabase(msg,2);
	}
	
	public void debug(String msg) {
		logToDatabase(msg,3);
	}
	
}
