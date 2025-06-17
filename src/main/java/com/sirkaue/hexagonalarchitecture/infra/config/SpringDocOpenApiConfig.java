package com.sirkaue.hexagonalarchitecture.infra.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Hexagonal Architecture (Ports & Adapters) - User Service")
                        .description("API para gerenciamento de usuários com arquitetura hexagonal")
                        .version("v1")
                        .contact(new Contact()
                                .name("Kauê Lima")
                                .email("kauekdslopes@gmail.com")));
    }
}
