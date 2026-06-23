package com.urbandelivery.catalogo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI catalogoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Catálogo")
                        .description("Microservicio Consumidor para platos y precios finales con HATEOAS")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo UrbanDelivery")
                                .email("contacto@urbandelivery.cl")));
    }
}