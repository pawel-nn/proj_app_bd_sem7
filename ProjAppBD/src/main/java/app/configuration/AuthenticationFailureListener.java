package app.configuration;

import java.lang.invoke.MethodHandles;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import app.model.User;
import app.model.repository.UserRepository;
import app.operations.DatabaseLogService;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private final int MAX_ATTEMPTS = 5;
	
	@Autowired
	private DatabaseLogService databaseLogService;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LoginCacheService loginCacheService;
	
    @Autowired
    private HttpServletRequest request;
	
	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
		String username = event.getAuthentication().getName();
		User user = userRepository.findByUsername(username);
		if (user != null) {
			user.setFailedLogins(user.getFailedLogins()+1);
			if(user.getFailedLogins() >= MAX_ATTEMPTS) {
				//WebAuthenticationDetails auth = (WebAuthenticationDetails) event.getAuthentication().getDetails();
				String ip = getClientIP();
				loginCacheService.block(ip);
				log.error("Multiple failed loggins from: {}.", ip);
				databaseLogService.error("Multiple failed loggins from: " + ip);
				
			}
			userRepository.save(user);
		}
	}
	
    private String getClientIP() {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null){
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}
