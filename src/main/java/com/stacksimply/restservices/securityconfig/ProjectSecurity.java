package com.stacksimply.restservices.securityconfig;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
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
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().// This means Don't create
																								// HttpSessions and
																								// tokens my self to
																								// create tokens like
																								// that we are saying to
																								// spring security
																								// framework
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
				// .ignoringAntMatchers("/helloworld-bean").csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and().//
				// adding CSRF token logic-[Disable CSRF if you using JWT tokens]
				addFilterBefore(new RequestValidationBeforeFilter(), UsernamePasswordAuthenticationFilter.class).// Adding
																													// our
																													// custom
																													// filter[RequestValidationBeforeFilter]
																													// to
																													// before
																													// BasicAuthenticationFilter
				addFilterAfter(new AuthoritiesLogingAfterFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterAt(new AuthoritiesLoggingAtFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new JWTTokenValidatorFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class).
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
				.hasRole("ADMIN").antMatchers("/helloworld-bean").authenticated().antMatchers("/mapstruct/**")
				.authenticated().antMatchers("/helloworld").permitAll().and().formLogin().and().httpBasic();

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
		 * FILTERS We can check the registered filters inside spring security with below
		 * configuratins
		 * 
		 * @EnableWebSecurity(debug=true)-we need to enabled the debugging of the
		 *                                   security details Enable logging of the
		 *                                   details by adding the below property in
		 *                                   application.properties
		 *                                   logging.level.org.springframework.security.web.FilterChainProxy=DEBUG
		 *                                   Internal Filters of spring security that
		 *                                   get executed in the authentication flow
		 *                                   Security filter chain :[
		 *                                   WebSyncManagerIntegrationFilter
		 *                                   SecurityContextPersistenceFilter
		 *                                   HeaderWriterFilter CrosFilter CsrFilter
		 *                                   LogoutFilter BasicAuthenticationFilter
		 *                                   RequestCacheAwareFilter
		 *                                   SecurityContextHolderAwareRequestFilter
		 *                                   AnnoymousAuthenticationFilter
		 *                                   SessionManagementFilter
		 *                                   ExceptionTransalationFilter
		 *                                   FilterSecurityInterceptor
		 * 
		 */

		/**
		 * INTERnal FILTERS GenericFilterBean :Purpose it wrap(access) all the
		 * properties and configuration details in web.xml and servlet context details
		 * can be use to implement this filter to custom filters.
		 * 
		 * OncePerRequestFilter : we have a scenario filter can execute once for a
		 * request where you want in custom logic to be executed only once per request
		 * we can extends oncePerRequestFilter.
		 */

		/**
		 * METHOD LEVEL SECURITY To enable method level security to
		 * add @EnableGlobalMethodSecurity on the configure class. Method level
		 * securities provide below approaches 1.Invocation authorization -validate
		 * someone invoke a method or not based on their roles/authorities 2.Filtering
		 * authorization Validates what a method can receive through its parameters and
		 * what the invoker can receive back from the method post business logic
		 * execution.
		 * 
		 * Spring security will use the aspects from the AOP module and have the
		 * interceptors in between method invocation to apply the authorization rules
		 * configured Method level security offers below 3 different styles for
		 * configuring the authorization rules on top of methods
		 * 
		 * 1.The orePostEnabled property enables spring security @PreAuthorize
		 * & @PostAuthorize annotations 2.The securedEnabled property enables @Secured
		 * annotation 3.The jsr250Enabled property enables @RoleAllowed annotation
		 * 
		 * Ex :- @configuration
		 * 
		 * @EnableGlobalMethodSecurity(prePostEnabled=true,securedEnabled=true,jsr250Enabled=true) public
		 *                                                                                         class
		 *                                                                                         MethodSecurityConfig{
		 *                                                                                         }
		 * 
		 * @Secured & @RoleAllowed less secure compare to @PreAuthorize
		 *          and @PostAuthorize
		 * 
		 *          1.Invocation authorization Using Invocation authorization we can
		 *          decide if a user is authorize to invoke a method before the method
		 *          executes(PreAuthorization) or after the method execution is
		 *          completed(PostAuthorization) For filtering the parameters before
		 *          calling the method we can user Prefiltering.
		 * 
		 * @Service Public class
		 *          LoanService{ @PreAuthorize("hasAuthority('admin')") @PreAuthorize("hasRole('admin')") @PreAuthorize("hasAnyRole('admin','user')") @PreAuthorize("#username=authentication.principal.username") @PreAuthorize("hasPermission(returnObject,'admin')")
		 *          public Loan getLoanDetails(string username){ return
		 *          loanRepository.loadByUserName(username); }
		 * 
		 *          For applying postAuthorization rules below is the sample
		 *          configuration
		 * 
		 * @Service Public class
		 *          LoanService{ @PostAuthorize("returnObject.username=authentication.pricipal.username") @PostAuthorize("hasPermission(returnObject,'admin')")
		 *          public loan getLoanDetails(String username){ return
		 *          loanRepository.loadLoanByUserName(username); } } when implementing
		 *          complex authorities logic,we can separate the logic using a separate
		 *          class that implements PermissionEvaluator and overwrite the method
		 *          hasPermission() inside it which has be leveraged inside the
		 *          hasPermission configurations.
		 * 
		 *          FILTERING AUTHORIZATION IN SPRING SECURITY If we have scenario where
		 *          we don't want to control the invocation of the method but we want to
		 *          make sure that the parameters sent and received to/from the method
		 *          need to follow authorization rules,then we can consider filtering.
		 * 
		 *          For filtering the parameters before calling the method we can
		 *          user @PreFilter annotation
		 * @Service public class Loanservice{ @PreFilter("filterObject.username ==
		 *          authentication.pricipal.username") public Loan
		 *          updateLoanDetails(Loan loan){ [business logic] return loan; }
		 *
		 * @PostFilter For filtering the parameters after executing the method we can
		 *             use PostFiltering.
		 * 
		 * @Service public class
		 *          LoanService{ @Postfilter("filterObject.username==authentication.principal.username")
		 *          public Loan getLoanDetails(Loan loan){ [business logic] return loan;
		 *          } We can use the @PostFilter on the Spring Data repository methods
		 *          as well to filter any unwanted data Coming from the data base.
		 * 
		 */

		/**
		 * PROBLEMS WITHOUT OAUTH2 1.The Bank System maintain separate applications for
		 * departments like Loans,Cards and Accounts. 2.The Users has to register and
		 * maintain different credentials/same credentials but will be stored in 3
		 * different DBs. 3.Even the AuthN & AuthZ logic,security standards will be
		 * duplicated in all the 3 Applications
		 * 
		 * OAUTH2 COMPONENT 1.AUTHORIZATION SERVER: The server that authorize the client
		 * to access the user resources in resource server.When authorize server
		 * identifies that a client client is authorized to access the user resources on
		 * behalf of the user,it issues a token.The client application uses token prove
		 * to resource server that it was authorize by authorize server.The resource
		 * server allows the client access the resource it requested if it has a valid
		 * token after validating the same with Auth server.
		 * 
		 * 2.RESOURCE SERVER: where the protected resources owned by user is present
		 * like photos,personal information,transactions etc.
		 * 
		 * 3.USER OR RESOURCE OWNER The person who owns resources exposed by the
		 * resource server.UsUally the user will prove his identity with the help of
		 * username and password.
		 * 
		 * 4.CLIENT The application that want to access the resources owned by the user
		 * on their behalf. The client uses the client id and secret to identify itself.
		 * But these are not same as user credentials.
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
