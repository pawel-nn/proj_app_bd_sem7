package app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


@Configuration
public class MvcConfig {
	
	@Bean(name = "dataSource")
	@Primary
	public DriverManagerDataSource dataSource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/proj_app_bd?useSSL=false");
		driverManagerDataSource.setUsername("user_db");
		driverManagerDataSource.setPassword("user_db");
		return driverManagerDataSource;
	}
	
}
