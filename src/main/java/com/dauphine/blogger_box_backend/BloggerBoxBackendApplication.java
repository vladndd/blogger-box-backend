package com.dauphine.blogger_box_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Blogger box backend", description = "Blogger box endpoints and apis", contact = @Contact(name = "Vladislav", email = "vladislav.yaromiy@dauphine.eu"), version = "1.0.0"))
public class BloggerBoxBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BloggerBoxBackendApplication.class, args);
	}

}
