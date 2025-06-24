package com.rsbank.loan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Loan microservice REST API Documentation",
				description = "RSBank Loan microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Sarathi Goswami",
						email = "sarathi@gmail.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description =  "RSBank Loan microservice REST API Documentation",
				url = "https://www.eazybytes.com/swagger-ui.html"
		)
)
public class LoanApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanApplication.class, args);
	}

}
