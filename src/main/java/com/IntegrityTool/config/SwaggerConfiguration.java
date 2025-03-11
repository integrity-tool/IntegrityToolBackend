package com.IntegrityTool.config;

import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

public class SwaggerConfiguration {
     @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(
            new Info()
            .title("Integrity Tool API")
            .version("1.0.0")
            .description("Integrity Tool API")
            .contact(new Contact().name("Krishna").email("krishnasolanki120@gmail.com").url("https://github.com/krish"))
            .license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0.html")))
            .externalDocs(new ExternalDocumentation()
            .description("Find out more about Swagger")
            .url("https://swagger.io/docs/"));
    }
}
