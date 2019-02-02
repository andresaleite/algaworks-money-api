package com.estanciaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.estanciaapi.estanciaapi.config.property.EstanciaApiProperty;


@SpringBootApplication
@EnableConfigurationProperties(EstanciaApiProperty.class)
public class EstanciaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstanciaApiApplication.class, args);
	}
	
	 @Bean
	    public WebMvcConfigurer corsConfigurer() {
	        return new WebMvcConfigurerAdapter() {
	            @Override
	            public void addCorsMappings(CorsRegistry registry) {
	                registry.addMapping("/greeting-javaconfig").allowedOrigins("https://desa-estancia-ui.herokuapp.com");
	               // registry.addMapping("/greeting-javaconfig").allowedOrigins("http://localhost:4200");
	            }
	        };
	    }
}
