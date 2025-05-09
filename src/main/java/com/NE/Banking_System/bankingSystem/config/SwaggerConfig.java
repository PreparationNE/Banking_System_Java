package com.NE.Banking_System.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI bankingSystemOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Banking System API")
                        .description("API documentation for Banking System Application")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Chloe Ishimwe")
                                .email("karlychloee12@gmail.com")));
    }
}