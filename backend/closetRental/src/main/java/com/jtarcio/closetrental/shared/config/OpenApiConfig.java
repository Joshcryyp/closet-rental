package com.jtarcio.closetrental.shared.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Closet Rental API")
                        .version("1.0.0")
                        .description("API para sistema de aluguel de peças/produtos com gestão de catálogo, clientes, locações, estoque e pagamentos")
                        .contact(new Contact()
                                .name("JTarcio")
                                .email("contato@closetrental.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}
