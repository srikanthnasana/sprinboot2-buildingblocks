package com.stacksimply.restservices.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {
    @Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(getApiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.stacksimply.restservices"))
				.paths(PathSelectors.ant("/users/**"))
				.build();
	}
    
    private ApiInfo getApiInfo() {
    	return new ApiInfoBuilder()
    			.title("StackSimply User Management Service")
    			.description("This page lists all API's of User Management")
    			.version("2.0")
    			.contact(new Contact("Srikanth Nasana", "https://www.stacksimplify.com","srikanth.nasana@gmail.com"))
    			.license("License 2.0")
    			.licenseUrl("https://www.stacksimplify.com/license.html")
    			.build();
    			                   
    }
	
	//swagger Metadata: http://localhost:8080/v2/api-docs
	//Swagger UI URL: http://localhost:8080/swagger-ui.html
    //Swagger Core annotations URL : https://github.com/swagger-api/swagger-core/wiki/Annotations
    //API Core annotations - @Api->Controller Level ,@ApiOperation->Method level,@ApiParam->Parameter level
    //If verify in readable format in SWAGGER online editor https://editor.swagger.io/
    //check Complaints swagger API after build 
    //step1:http://localhost:8080/v2/api-docs->copy JSON from this path
    //Step2: Open  https://editor.swagger.io/ and Paste JSON to to swagger editor and check any bugs and fix.
}
