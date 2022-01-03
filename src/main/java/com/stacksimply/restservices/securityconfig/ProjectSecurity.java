package com.stacksimply.restservices.securityconfig;

import java.util.Collection;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class ProjectSecurity extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		/*
		 * Default configuration which will secure all the requests
		 * http.authorizeRequests((requests) -> requests.anyRequest().authenticated());
		 * http.formLogin(); http.httpBasic();
		 */

		/**
		 * Custom configuration as for our requirement
		 */
		http.cors().configurationSource(new CorsConfigurationSource() {

			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				// verify and prevent other browser request and allow only configure requests.
				CorsConfiguration config = new CorsConfiguration();
				config.setAllowedOrigins(Collections.singletonList("http://localhost:8080"));
				config.setAllowedMethods(Collections.singletonList("*"));
				config.setAllowCredentials(true);
				config.setAllowedHeaders(Collections.singletonList("*"));
				config.setMaxAge(3600L);
				return config;
			}
		}).and().csrf().ignoringAntMatchers("/helloworld-bean")
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and().// adding csrf token logic
				// .csrf().disable().//if you disable csrf to use this logic
				/**
				 * Granted Authority is nothing but individual privilege.
				 */
				/*
				 * authorizeRequests().antMatchers("/users/**").hasAuthority("WRITE")
				 * .antMatchers("/userspec/**").hasAuthority("DELETE")
				 * .antMatchers("/helloworld").hasAuthority("READ")
				 * .antMatchers("/helloworld-bean").permitAll() .and() .formLogin().and()
				 * .httpBasic();
				 */
				/**
				 * Role based authority is nothing but group of privileges based on role.
				 */
				authorizeRequests().antMatchers("/users/**").hasAnyRole("USER", "ADMIN").antMatchers("/userspec/**")
				.hasRole("ADMIN").antMatchers("/helloworld").authenticated().antMatchers("/helloworld-bean").permitAll()
				.and().formLogin().and().httpBasic();

		/**
		 * MVC MATCHERS MVC Matches spring MVC HandlerMapingIntrospector to match the
		 * path and extract variables MVCMatchers(HttpMethod method,String
		 * ..pattrens)-we can specify both the HTTP method and path pattern to configure
		 * restictions
		 * http.authorizeRequests().mvcMatchers(HttpMethod.POST,"/example").authenticated().mvcMatchers(HttpMethod.GET,"/example").permitAll().
		 * anyRequest().denyAll();
		 * 
		 * MvcMatchers(String..patterns)-we can specify only path pattern to configure
		 * restrictions and all the HTTP methods will be allowed
		 * http.authorizeRequests().mvcMatchers("/profile/edit/**").authenticated().anyRequest().permitAll();
		 * Note :** indicates any number of paths. For example,/X[**]/Z will match both
		 * /x/y/z and /x/y/abc/z Single * indicates single path.For Example /x/[*]/z
		 * will match /x/y/z,/x/abc/z but nt /x/y/abc/z
		 * 
		 * Impotent :Generally mvcMatchers is more secure then an antMatchers. As an
		 * example antMathers("/secured") matches only exact /secured URL
		 * mvcMatchers("/secured") matches /secured as well as
		 * /secured/,/secured.html,/secured.xyz
		 *
		 */

		/**
		 * ANT MATCHERS
		 * http.authorizeRequests().antMatchers(HttpMethod.POST,"/example").authenticated().anyRequest().denyAll();
		 * http.authorizeRequests().antMatchers("/profiles/edit/**").authenticated().anyRequest().permitAll();
		 * http.authorizeRequests().antMatchers(HttpMethod.POST).authenticated().anyRequest().permitAll();
		 */

		/**
		 * REGEX MATCHERS Regexes can be used to represent format of a string ,so they
		 * offer unlimited possibilities for this matter regexMatchers(HttpMethod
		 * method,String regex)-we can specify both http methods and path regex to
		 * configure restrictions
		 * http.authorizeRequests().regexMatchers(HttpMethod.GET,"."/(en|es|zh)").authenticated.anyRequest().denyAll();
		 * 
		 * regexMatchers(String regex)-we can specify only path regex to configure
		 * restictions and all the HTTP methods will be allowed.
		 * http.authorizeRequests().regexMatchers("."/(en|es|zh)").authenticated.anyRequest().denyAll();->Matching
		 * countries are allowed rest of denied.
		 * 
		 * regex matches can be used to restrict time based authentication and country
		 * based eetc.
		 */

		/**
		 * Configuration to deny all the requests deny all request authenticated and
		 * unauthenticated request
		 */
		/*
		 * http.authorizeRequests((requests) -> requests.anyRequest().denyAll());
		 * http.formLogin(); http.httpBasic();
		 */

		/**
		 * Configuration to deny all the requests deny all request authenticated and
		 * unauthenticated request
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
		return new BCryptPasswordEncoder();
	}
}
