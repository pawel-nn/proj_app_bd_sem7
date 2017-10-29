package app.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            	.antMatchers("/**", "/css/**").permitAll()
                .antMatchers("/owner/**").access("hasRole('ROLE_OWNER')")
                .antMatchers("/client/**").access("hasRole('ROLE_CLIENT')")
                .antMatchers("/home", "/result").access("hasRole('ROLE_ADMIN') OR hasRole('ROLE_EMPLOYEE')")
            	.anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/home")
                .permitAll()
                .and()
            .logout()
                .permitAll()
            .and().exceptionHandling().accessDeniedPage("/403");
    }
    
    @Autowired
    DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
      auth.jdbcAuthentication().dataSource(dataSource)
      	  .usersByUsernameQuery("select username,password, enabled from users where username=?")
      	  .authoritiesByUsernameQuery("select username, user_role from users where username=?");
    }
    
}