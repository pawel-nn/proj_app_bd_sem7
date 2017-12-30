package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@Configuration
@EnableAsync
public class SpringBootMainApp {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpringBootMainApp.class, args);
	}
	
}