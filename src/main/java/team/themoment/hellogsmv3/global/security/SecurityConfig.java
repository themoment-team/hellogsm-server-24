package team.themoment.hellogsmv3.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import team.themoment.hellogsmv3.domain.auth.type.Role;
import team.themoment.hellogsmv3.global.security.handler.CustomAccessDeniedHandler;
import team.themoment.hellogsmv3.global.security.handler.CustomAuthenticationEntryPoint;
import team.themoment.hellogsmv3.global.security.handler.CustomUrlAuthenticationSuccessHandler;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private static final String oauth2LoginEndpointBaseUri = "/oauth2/authorization";
    private static final String oauth2LoginProcessingUri = "/login/oauth2/code/*";
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Configuration
    @EnableWebSecurity
    public class ProdSecurityConfig {
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .formLogin(AbstractHttpConfigurer::disable)
                    .httpBasic(AbstractHttpConfigurer::disable)
                    .csrf(AbstractHttpConfigurer::disable)
                    .cors(cors -> cors.configurationSource(corsConfigurationSource()));

            oauth2Login(http);
            exceptionHandling(http);
            authorizeHttpRequests(http);
            return http.build();
        }
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "HEAD", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private void oauth2Login(HttpSecurity http) throws Exception {
        http.oauth2Login(oauth2Login ->
                oauth2Login
                        .authorizationEndpoint(authorizationEndpointConfig ->
                                authorizationEndpointConfig.baseUri(oauth2LoginEndpointBaseUri))
                        .loginProcessingUrl(oauth2LoginProcessingUri)
                        .successHandler(new CustomUrlAuthenticationSuccessHandler("http://localhost:8080", "http://localhost:8080/admin"))
                        .failureHandler(new SimpleUrlAuthenticationFailureHandler("http://localhost:8080/login"))

        );
    }

    private void exceptionHandling(HttpSecurity http) throws Exception {
        http.exceptionHandling(handling -> handling
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint));
    }

    private void authorizeHttpRequests(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(httpRequests -> httpRequests
                .requestMatchers(HttpMethod.OPTIONS, "/**/*").permitAll()
                .requestMatchers("/csrf").permitAll()
                .requestMatchers("/auth/v3/**").permitAll()
                .requestMatchers("/manager").authenticated()
                .requestMatchers("/admin").hasAnyRole(Role.ADMIN.name())
                .anyRequest().permitAll()
        );
    }
}
