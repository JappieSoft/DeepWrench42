package nl.novi.deepwrench42.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Developer",
                        email = "jasper@vd-heijden.eu"
                ),
                description = "Below is the API documentation for DeepWrench42. Please refer to the application readme for endpoint authorization limitations. Any problems, please contact the developer.",
                title = "DeepWrench Dynamic API Documentation",
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Localhost / Internal Server",
                        url = "http://localhost:8080"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "KeyCloak"
                )
        }
)

@SecurityScheme(
        name = "KeyCloak",
        type = SecuritySchemeType.OAUTH2,
        description = "Keycloak OAuth2 Authorization Code flow",
        flows = @OAuthFlows(
                authorizationCode = @OAuthFlow(
                        authorizationUrl = "http://localhost:9090/realms/DeepWrench42/protocol/openid-connect/auth",
                        tokenUrl = "http://localhost:9090/realms/DeepWrench42/protocol/openid-connect/token"
                )
        )
)

public class SwaggerConfig { }

