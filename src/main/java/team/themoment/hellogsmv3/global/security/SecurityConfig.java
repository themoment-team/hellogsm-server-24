package team.themoment.hellogsmv3.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private static final String oauth2LoginEndpointBaseUri = "/auth/v1/oauth2/authorization";
    private static final String oauth2LoginProcessingUri = "/auth/v1/oauth2/code/*";


    private void authorizeHttpRequests(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(httpRequests -> httpRequests
                .requestMatchers(HttpMethod.OPTIONS, "/**/*").permitAll()
                .requestMatchers("/csrf").permitAll()
                .requestMatchers("/auth/v1/**").permitAll()
                .anyRequest().permitAll()
        );
    }
}
