package app.configuration;

import java.lang.invoke.MethodHandles;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import app.model.User;
import app.model.repository.UserRepository;
import app.operations.DatabaseLogService;

@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent>{
	
	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	@Autowired
	private DatabaseLogService databaseLogService;
	
	@Autowired
	private UserRepository userRepository;
	
    @Autowired
    private HttpServletRequest request;

	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		String username = event.getAuthentication().getName();
		User user = userRepository.findByUsername(username);
		user.setFailedLogins(0);
		//WebAuthenticationDetails auth = (WebAuthenticationDetails) event.getAuthentication().getDetails();
		String ip = getClientIP();
		log.error("Success login from: {}.", ip);
		databaseLogService.error("Success login from: " + ip);
		userRepository.save(user);
	}
	
    private String getClientIP() {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null){
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}