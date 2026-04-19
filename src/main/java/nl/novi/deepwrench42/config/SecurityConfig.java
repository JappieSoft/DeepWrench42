package nl.novi.deepwrench42.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.jwt.JwtAudienceValidator;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Configuration
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuer;
    @Value("${spring.security.oauth2.resourceserver.jwt.audiences}")
    private String audience;
    @Value("${client-id}")
    private String clientId;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .httpBasic(hp -> hp.disable())
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {
                })
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                                .decoder(jwtDecoder())
                        ))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/{id}").hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/inspection",
                                "/inspection/find-inspection-date-before",
                                "/inspection/find-inspection-date-after",
                                "/inspection/find-next-overdue",
                                "/tool-log", "/tool-log/{id}",
                                "/user/{id}").hasAnyAuthority("ADMIN", "LEAD")
                        .requestMatchers(HttpMethod.GET, "/aircraft", "/aircraft/{id}",
                                "/aircraft-type", "/aircraft-type/{id}",
                                "/engine-type", "/engine-type/{id}",
                                "/inspection/{id}",
                                "/storage-location", "/storage-location/{id}",
                                "/tool", "/tool/**",
                                "/tool-kit", "/tool-kit/**",
                                "/user").authenticated()
                        .requestMatchers(HttpMethod.POST, "/aircraft",
                                "/aircraft-type",
                                "/engine-type",
                                "/inspection", "/inspection/performed",
                                "/storage-location",
                                "/tool", "/tool/**",
                                "/tool-kit", "/tool-kit/**").hasAnyAuthority("ADMIN", "LEAD")
                        .requestMatchers(HttpMethod.POST, "/user").hasAuthority("USER")
                        .requestMatchers(HttpMethod.POST, "/equipment/checkout", "/equipment/checkin").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/user/{id}").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/user").hasAuthority("USER")
                        .requestMatchers(HttpMethod.PUT, "/aircraft/{id}",
                                "/aircraft-type/{id}",
                                "/engine-type/{id}",
                                "/inspection/{id}",
                                "/storage-location/{id}",
                                "/tool/{id}",
                                "/tool-kit/{id}").hasAnyAuthority("ADMIN", "LEAD")
                        .requestMatchers(HttpMethod.DELETE, "/aircraft/{id}",
                                "/aircraft-type/{id}",
                                "/engine-type/{id}",
                                "/storage-location/{id}",
                                "/inspection/{id}",
                                "/tool/{id}",
                                "/tool-kit/{id}",
                                "/user/{id}").hasAuthority("ADMIN")
                        .anyRequest().denyAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }


    public JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = JwtDecoders.fromOidcIssuerLocation(issuer);

        OAuth2TokenValidator<Jwt> audienceValidator = new JwtAudienceValidator(audience);
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);
        jwtDecoder.setJwtValidator(withAudience);
        return jwtDecoder;
    }

    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new Converter<>() {
            @Override
            public Collection<GrantedAuthority> convert(Jwt source) {
                Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
                for (String authority : getAuthorities(source)) {
                    grantedAuthorities.add(new SimpleGrantedAuthority(authority));
                }
                return grantedAuthorities;
            }

            private List<String> getAuthorities(Jwt jwt) {
                Map<String, Object> resourceAcces = jwt.getClaim("resource_access");
                if (resourceAcces != null) {
                    if (resourceAcces.get(clientId) instanceof Map) {
                        Map<String, Object> client = (Map<String, Object>) resourceAcces.get(clientId);
                        if (client != null && client.containsKey("roles")) {
                            return (List<String>) client.get("roles");
                        }
                    } else {
                        Map<String, Object> realmAcces = jwt.getClaim("realm_access");
                        if (realmAcces != null && realmAcces.containsKey("roles")) {
                            return (List<String>) realmAcces.get("roles");
                        }
                        return new ArrayList<>();
                    }
                }
                return new ArrayList<>();
            }
        });
        return jwtAuthenticationConverter;
    }
}
