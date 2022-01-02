package com.stacksimply.restservices.securityconfig;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
@Configuration
public class ProjectSecurity extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		
		/*Default configuration which will secure all the requests
		 * http.authorizeRequests((requests) -> requests.anyRequest().authenticated());
		 * http.formLogin(); http.httpBasic();
		 */
		
		/**
		 * Custom configuration as for our requirement
		 */
		
		  http. authorizeRequests() .antMatchers("/users/**").authenticated()
		  .antMatchers("/userspec/**").authenticated()
		  .antMatchers("/helloworld-bean").permitAll() .and() .formLogin().and()
		  .httpBasic();
		 
		
		/**
		 * Configuration to deny all the requests
		 * deny all request authenticated and unauthenticated request
		 */
		/*
		 * http.authorizeRequests((requests) -> requests.anyRequest().denyAll());
		 * http.formLogin(); http.httpBasic();
		 */
		 
		 /**
			 * Configuration to deny all the requests
			 * deny all request authenticated and unauthenticated request
			 */
			/*
			 * http.authorizeRequests((requests) -> requests.anyRequest().permitAll());
			 * http.formLogin(); http.httpBasic();
			 */
		  
		  
		
	}
	
	/*
	 * protected void configure(AuthenticationManagerBuilder auth) throws Exception
	 * { auth.inMemoryAuthentication() .withUser("admin") .password("12345")
	 * .authorities("admin") .and() .withUser("user") .password("12345")
	 * .authorities("read").and()
	 * .passwordEncoder(NoOpPasswordEncoder.getInstance()); }
	 */
	/**
	 * Custom UserDetails Implementations'                                                                                                          
	 */
	/*
	 * protected void configure(AuthenticationManagerBuilder auth) throws Exception
	 * { InMemoryUserDetailsManager userDtlsService=new
	 * InMemoryUserDetailsManager(); UserDetails
	 * user=User.withUsername("admin").password("12345").authorities("admin").build(
	 * ); UserDetails
	 * user1=User.withUsername("user").password("12345").authorities("read").build()
	 * ; userDtlsService.createUser(user); userDtlsService.createUser(user1);
	 * auth.userDetailsService(userDtlsService); }
	 */
	
	/*
	 * @Bean public UserDetailsManager userDetailsManager(DataSource dataSource) {
	 * return new JdbcUserDetailsManager(dataSource); }
	 */
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}
