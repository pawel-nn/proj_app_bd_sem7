package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
@Configuration
public class SpringBootMainApp {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpringBootMainApp.class, args);
	}
	
}