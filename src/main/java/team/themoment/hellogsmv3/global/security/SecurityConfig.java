package team.themoment.hellogsmv3.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.UriComponentsBuilder;
import team.themoment.hellogsmv3.domain.auth.type.Role;
import team.themoment.hellogsmv3.global.security.auth.AuthEnvironment;
import team.themoment.hellogsmv3.global.security.data.CookieName;
import team.themoment.hellogsmv3.global.security.handler.CustomAccessDeniedHandler;
import team.themoment.hellogsmv3.global.security.handler.CustomAuthenticationEntryPoint;
import team.themoment.hellogsmv3.global.security.handler.CustomUrlAuthenticationSuccessHandler;
import team.themoment.hellogsmv3.global.security.handler.CustomUrlLogoutSuccessHandler;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthEnvironment authEnv;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Configuration
    @EnableWebSecurity
    public class LocalSecurityConfig {
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

            basicSetting(http);
            cors(http);
            logout(http);
            oauth2Login(http);
            exceptionHandling(http);
            authorizeHttpRequests(http);

            return http.build();
        }
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(authEnv.allowedOrigins());

        configuration.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.DELETE.name(),
                HttpMethod.OPTIONS.name()));

        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private void basicSetting(HttpSecurity http) throws Exception {
        http.formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable);
    }

    private void cors(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));
    }

    private void logout(HttpSecurity http) throws Exception {
        http.logout(logout ->
                logout
                    .logoutUrl("/auth/v3/logout")
                    .deleteCookies(CookieName.JSESSIONID.name(), CookieName.REMEMBER_ME.name())
                    .logoutSuccessHandler(new CustomUrlLogoutSuccessHandler(authEnv.redirectBaseUri(), authEnv.redirectAdminUri())));
    }

    private void oauth2Login(HttpSecurity http) throws Exception {
        http.oauth2Login(oauth2Login ->
                oauth2Login
                        .authorizationEndpoint(authorizationEndpointConfig ->
                                authorizationEndpointConfig.baseUri(authEnv.loginEndPointBaseUri()))
                        .loginProcessingUrl(authEnv.loginProcessingUri())
                        .successHandler(new CustomUrlAuthenticationSuccessHandler(authEnv.redirectBaseUri(), authEnv.redirectAdminUri()))
                        .failureHandler(new SimpleUrlAuthenticationFailureHandler(authEnv.redirectLoginFailureUri()))

        );
    }

    private void exceptionHandling(HttpSecurity http) throws Exception {
        http.exceptionHandling(handling -> handling
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint));
    }

    private void authorizeHttpRequests(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(httpRequests -> httpRequests
                .requestMatchers(HttpMethod.OPTIONS, "/**/*").permitAll() // for CORS
                .requestMatchers("/auth/v3/**").permitAll()
                //auth
                .requestMatchers(HttpMethod.GET, "/authentication/v3/authentication/me").hasAnyAuthority(
                        Role.UNAUTHENTICATED.name(),
                        Role.APPLICANT.name(),
                        Role.ADMIN.name(),
                        Role.ROOT.name()
                )
                .requestMatchers("/authentication/v3/authentication/*").hasAnyAuthority(
                        Role.ADMIN.name()
                )
                .requestMatchers(HttpMethod.POST, "/applicant/v3/applicant/me").hasAnyAuthority(
                        Role.UNAUTHENTICATED.name(),
                        Role.APPLICANT.name(),
                        Role.ADMIN.name(),
                        Role.ROOT.name()
                )
                .requestMatchers(HttpMethod.POST,
                        "/applicant/v3/applicant/me/send-code",
                        "/applicant/v3/applicant/me/auth-code").hasAnyAuthority(
                        Role.UNAUTHENTICATED.name(),
                        Role.APPLICANT.name(),
                        Role.ADMIN.name(),
                        Role.ROOT.name()
                )
                .requestMatchers(HttpMethod.POST, "/applicant/v3/applicant/me/send-code-test").hasAnyAuthority(
                        Role.ROOT.name()
                )
                .anyRequest().permitAll()
        );
    }
}
