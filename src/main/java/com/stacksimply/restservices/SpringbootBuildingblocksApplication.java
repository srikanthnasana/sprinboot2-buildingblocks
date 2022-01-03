package com.stacksimply.restservices;

import java.util.Locale;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
//test
@SpringBootApplication
@EnableJpaRepositories("com.stacksimply.restservices.repositories")
@EntityScan("com.stacksimply.restservices.entities")
@ComponentScan(basePackages= {"com.stacksimply.restservices.mappers","com.stacksimply.restservices"})
@EnableWebSecurity(debug = true)//Don't enable in production
public class SpringbootBuildingblocksApplication {

	@Bean
	public LocaleResolver localeResolver() {
		AcceptHeaderLocaleResolver localeResolver= new AcceptHeaderLocaleResolver();
		localeResolver.setDefaultLocale(Locale.US);
		return localeResolver;
	}
	
	@Bean
	public ResourceBundleMessageSource resMessageSource() {
		ResourceBundleMessageSource messageSource=new ResourceBundleMessageSource();
		messageSource.setBasename("message");
		return messageSource;
	}
	@Bean
	public MessageSource messageSource() {
	    ReloadableResourceBundleMessageSource messageSource
	      = new ReloadableResourceBundleMessageSource();
	    
	    messageSource.setBasename("classpath:messages");
	    messageSource.setDefaultEncoding("UTF-8");
	    return messageSource;
	}
	
	@Bean
	public LocalValidatorFactoryBean getValidator() {
	    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
	    bean.setValidationMessageSource(messageSource());
	    return bean;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootBuildingblocksApplication.class, args);
	}

}
