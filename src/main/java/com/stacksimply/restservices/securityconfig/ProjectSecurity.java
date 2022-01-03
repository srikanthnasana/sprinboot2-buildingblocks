package com.stacksimply.restservices.securityconfig;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.stacksimply.restservices.filter.AuthoritiesLoggingAtFilter;
import com.stacksimply.restservices.filter.AuthoritiesLogingAfterFilter;
import com.stacksimply.restservices.filter.JWTTokenGeneratorFilter;
import com.stacksimply.restservices.filter.JWTTokenValidatorFilter;
import com.stacksimply.restservices.filter.RequestValidationBeforeFilter;

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
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().//This means Don't create HttpSessions and tokens my self to create tokens like that we are saying to spring security framework
		cors().configurationSource(new CorsConfigurationSource() {

			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				// verify and prevent other browser request and allow only configure requests.
				CorsConfiguration config = new CorsConfiguration();
				config.setAllowedOrigins(Collections.singletonList("http://localhost:8080"));
				config.setAllowedMethods(Collections.singletonList("*"));
				config.setAllowCredentials(true);
				config.setAllowedHeaders(Collections.singletonList("*"));
				config.setExposedHeaders(Arrays.asList("Authorization"));                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
				config.setMaxAge(3600L);
				return config;
			}
		}).and().csrf().disable().
		        //.ignoringAntMatchers("/helloworld-bean").csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and().// adding CSRF token logic-[Disable CSRF if you using JWT tokens]
				addFilterBefore(new RequestValidationBeforeFilter(),   UsernamePasswordAuthenticationFilter.class).//Adding our custom filter[RequestValidationBeforeFilter] to before BasicAuthenticationFilter
				addFilterAfter(new AuthoritiesLogingAfterFilter(), UsernamePasswordAuthenticationFilter.class).
				addFilterAt(new AuthoritiesLoggingAtFilter(), UsernamePasswordAuthenticationFilter.class).
				addFilterBefore(new JWTTokenValidatorFilter(), UsernamePasswordAuthenticationFilter.class).
				addFilterAfter(new JWTTokenGeneratorFilter(), UsernamePasswordAuthenticationFilter.class).
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
		 * FILTERS
		 * We can check the registered filters inside spring security with below configuratins
		 * @EnableWebSecurity(debug=true)-we need to enabled the debugging of the security details
		 * Enable logging of the details by adding the below property in application.properties
		 * logging.level.org.springframework.security.web.FilterChainProxy=DEBUG
		 * Internal Filters of spring security that get executed in the authentication flow
		 * Security filter chain :[
		 * WebSyncManagerIntegrationFilter
		 * SecurityContextPersistenceFilter
		 * HeaderWriterFilter
		 * CrosFilter
		 * CsrFilter
		 * LogoutFilter
		 * BasicAuthenticationFilter
		 * RequestCacheAwareFilter
		 * SecurityContextHolderAwareRequestFilter
		 * AnnoymousAuthenticationFilter
		 * SessionManagementFilter
		 * ExceptionTransalationFilter
		 * FilterSecurityInterceptor
		 * 
		 */
		
		/**
		 * INTERnal FILTERS
		 * GenericFilterBean :Purpose it wrap(access) all the properties 
		 * and configuration details in web.xml and 
		 * servlet context details can be use to implement this filter to custom filters.
		 * 
		 * OncePerRequestFilter : we have a scenario filter can execute once for a request
		 * where you want in custom logic to be executed only once per request we can 
		 * extends oncePerRequestFilter.
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
