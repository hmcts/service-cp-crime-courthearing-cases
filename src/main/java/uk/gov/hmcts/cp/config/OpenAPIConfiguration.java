package uk.gov.hmcts.cp.config;

import io.swagger.v3.oas.models.OpenAPI;
import uk.gov.hmcts.cp.config.OpenAPIConfigurationLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class OpenAPIConfiguration {

    private OpenAPIConfigurationLoader openAPIConfigLoader = new OpenAPIConfigurationLoader();

    @Bean
    public OpenAPI openAPI() {
        return openAPIConfigLoader.openAPI();
    }
}
