package com.desafio.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação Spring Boot.
 * Configura e inicia a aplicação com todas as configurações necessárias.
 */
@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Desafio Final - API REST",
                version = "1.0.0",
                description = "API REST para gerenciamento de clientes - Desafio Final do Bootcamp de Arquiteto de Software",
                contact = @Contact(
                        name = "Equipe de Desenvolvimento",
                        email = "dev@desafio.com"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                )
        )
)
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}

