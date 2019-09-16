package com.smithdrug.sgs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableEurekaClient
@EnableZuulProxy
@SpringBootApplication
@EnableSwagger2
public class SmithgatewayApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(SmithgatewayApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SmithgatewayApplication.class);
	}
	
	//Whether swagger is turned on or not, the formal environment usually needs to be turned off. It can be set according to the multi-environment configuration of spring boot.
	@Value(value = "${swagger.enabled}")
	Boolean swaggerEnabled;

	@Autowired
	RouteLocator routeLocator;
	
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
				// Whether to open
				.enable(swaggerEnabled).select()
				// Scanning Path Pack
				.apis(RequestHandlerSelectors.basePackage("*"))
				// Specify a path to process PathSelectors.any() to represent all paths
				.paths(PathSelectors.any()).build().pathMapping("/");
	}

	//Setting up api information
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Routing Gateway(Zuul): utilize swagger2 polymerization API File")
				.description("oKong | Staggering ape")
				// Author information
				.contact(new Contact("oKong", "https://blog.lqdev.cn/", "alexjeyasingh@gmail.com"))
				.version("1.0.0")
				.termsOfServiceUrl("https://github.com/neethualex/")
				.build();
	}

	@Bean
	UiConfiguration uiConfig() {
		return new UiConfiguration("validatorUrl", "list", "alpha", "schema",
				UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS, false, true, 60000L);
	}
}
