package app.configuration;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Scope("singleton")
@Service
public class LoginCacheService {

	private static HashMap<String, LocalDateTime> attemptsCache;

	
	public LoginCacheService() {
		attemptsCache = new HashMap<>();
	}

	public void block(String key) {
		if(attemptsCache.containsKey(key) && attemptsCache.get(key).plusSeconds(30).isBefore(LocalDateTime.now()))
			attemptsCache.put(key, LocalDateTime.now());
		else if(!attemptsCache.containsKey(key))
			attemptsCache.put(key, LocalDateTime.now());
	}

	public boolean isBlocked(String key) {
		try {
			return attemptsCache.get(key).plusSeconds(30).isAfter(LocalDateTime.now());
		} catch (Exception e) {
			return false;
		}
	}
}