package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customerApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Oracle Stored Procedure POC API")
                        .description("REST endpoints for invoking Oracle stored procedures and returning customer summaries.")
                        .version("1.0.0")
                        .contact(new Contact().name("Engineering Team"))
                        .license(new License().name("Internal Use")));
    }
}
