package com.blog.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	public static final String Authorization_Header="Authorization";
	
	private ApiKey apiKey() {
		return new ApiKey("JWT", Authorization_Header, "header");
	}
	
	private List<SecurityContext> securityContexts(){
		return  Arrays.asList(SecurityContext.builder().securityReferences(sf()).build());
	}
	
	private List<SecurityReference> sf(){
		
		AuthorizationScope scope=new AuthorizationScope("global", "accessEverything");
		return Arrays.asList(new SecurityReference("scope", new AuthorizationScope[] {scope}));
	}
	
	
	@Bean
	public Docket api() {
		
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(getInfo())
				.securityContexts(securityContexts())
				.securitySchemes(Arrays.asList(apiKey()))
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo getInfo() {
		
		return new ApiInfo("Blogging Application : Backend Course", "This project is developed by Amritkp", "1.0", "Terms of service"
				               , new Contact("AMRIT","https://amritkpBlogApp.com", "amritkp12112000@gmail.com") 
				                     , "License of APIs", "API license URl",Collections.EMPTY_LIST);
	}
}
