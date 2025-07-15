package EVOLUIR_DESAFIO_RPE.fintech_RPE.config;

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
                        .title("API - Desafio Técnico RPE")
                        .version("1.0")
                        .description("Documentação da API para controle de clientes e faturas.")
                        .contact(new Contact()
                                .name("John Herbert Freire Lourenço")
                                .email("johnherbertfre98@gamail.com")
                                .url("https://github.com/JohnHerbert1"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org"))
                );
    }
}
