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
import team.themoment.hellogsmv3.domain.member.entity.type.Role;
import team.themoment.hellogsmv3.global.security.auth.AuthEnvironment;
import team.themoment.hellogsmv3.global.security.handler.CustomAccessDeniedHandler;
import team.themoment.hellogsmv3.global.security.handler.CustomAuthenticationEntryPoint;
import team.themoment.hellogsmv3.global.security.handler.CustomUrlAuthenticationSuccessHandler;

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
                // authentication
                .requestMatchers(HttpMethod.GET, "/authentication/v3/authentication/me").hasAnyAuthority(
                        Role.UNAUTHENTICATED.name(),
                        Role.APPLICANT.name(),
                        Role.ADMIN.name(),
                        Role.ROOT.name()
                )
                .requestMatchers("/authentication/v3/authentication/*").hasAnyAuthority(
                        Role.ADMIN.name()
                )
                // applicant
                .requestMatchers(HttpMethod.POST, "/applicant/v3/applicant/me").hasAnyAuthority(
                        Role.UNAUTHENTICATED.name(),
                        Role.APPLICANT.name(),
                        Role.ADMIN.name(),
                        Role.ROOT.name()
                )
                .requestMatchers(HttpMethod.PUT, "/applicant/v3/applicant/me").hasAnyAuthority(
                        Role.APPLICANT.name(),
                        Role.ADMIN.name(),
                        Role.ROOT.name()
                )
                .requestMatchers(HttpMethod.GET, "/applicant/v3/applicant/me").hasAnyAuthority(
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
                .requestMatchers(HttpMethod.GET, "/applicant/v3/applicant/{authenticationId}").hasAnyAuthority(
                        Role.ADMIN.name()
                )
                .requestMatchers(HttpMethod.POST, "/applicant/v3/applicant/me/send-code-test").hasAnyAuthority(
                        Role.ROOT.name()
                )
                // application
                .requestMatchers("/application/v3/application/me").hasAnyAuthority(
                        Role.APPLICANT.name()
                )
                .requestMatchers(HttpMethod.GET, "/application/v3/application/search").hasAnyAuthority(
                        Role.UNAUTHENTICATED.name(),
                        Role.APPLICANT.name(),
                        Role.ADMIN.name()
                )
                .requestMatchers(HttpMethod.GET, "/application/v3/application/all").hasAnyAuthority(
                        Role.ADMIN.name()
                )
                .requestMatchers(HttpMethod.PUT, "/application/v1/final-submit").hasAnyAuthority(
                        Role.APPLICANT.name()
                )
                .requestMatchers(HttpMethod.GET, "application/v3/admission-tickets").hasAnyAuthority(
                        Role.ADMIN.name()
                )
                .requestMatchers(HttpMethod.DELETE, "application/v3/application").hasAnyAuthority(
                        Role.APPLICANT.name()
                )
                .requestMatchers(HttpMethod.POST, "application/v3/image").hasAnyAuthority(
                        Role.APPLICANT.name(),
                        Role.ADMIN.name(),
                        Role.ROOT.name()
                )
                .requestMatchers(HttpMethod.GET, "/application/v3/application/{applicantId}").hasAnyAuthority(
                        Role.ADMIN.name()
                )
                .requestMatchers(HttpMethod.PUT, "/application/v3/application/{applicantId}").hasAnyAuthority(
                        Role.ADMIN.name(),
                        Role.ROOT.name()
                )
                .requestMatchers(HttpMethod.PUT, "application/v3/status/{applicantId}").hasAnyAuthority(
                        Role.ADMIN.name()
                )

                // member
                .requestMatchers(HttpMethod.GET, "/member/v3/member/me").hasAnyAuthority(
                        Role.UNAUTHENTICATED.name(),
                        Role.APPLICANT.name(),
                        Role.ADMIN.name(),
                        Role.ROOT.name()
                )
                .requestMatchers(HttpMethod.GET, "/member/v3/member/{memberId}").hasAnyAuthority(
                        Role.ADMIN.name()
                )
                .requestMatchers(HttpMethod.POST,
                        "/member/v3/member/me/send-code",
                        "/member/v3/member/me/auth-code"
                ).hasAnyAuthority(
                        Role.UNAUTHENTICATED.name(),
                        Role.APPLICANT.name(),
                        Role.ADMIN.name(),
                        Role.ROOT.name()
                )
                .requestMatchers(HttpMethod.POST, "/member/v3/member/me/send-code-test").hasAnyAuthority(
                        Role.ROOT.name()
                )
                .requestMatchers(HttpMethod.POST, "/member/v3/member/me").hasAnyAuthority(
                        Role.UNAUTHENTICATED.name(),
                        Role.APPLICANT.name(),
                        Role.ADMIN.name(),
                        Role.ROOT.name()
                )
                .requestMatchers(HttpMethod.GET, "/member/v3/auth-info/me").hasAnyAuthority(
                        Role.UNAUTHENTICATED.name(),
                        Role.APPLICANT.name(),
                        Role.ADMIN.name(),
                        Role.ROOT.name()
                )
                .requestMatchers(HttpMethod.GET, "/member/v3/auth-info/{memberId}").hasAnyAuthority(
                        Role.ADMIN.name()
                )

                // oneseo
                .requestMatchers("/oneseo/v3/oneseo/me").hasAnyAuthority(
                        Role.APPLICANT.name(),
                        Role.ROOT.name()
                )
                .requestMatchers("/oneseo/v3/oneseo/{memberId}").hasAnyAuthority(
                        Role.ADMIN.name()
                )
                .requestMatchers(HttpMethod.PATCH, "/oneseo/v3/arrived-status/{memberId}").hasAnyAuthority(
                        Role.ADMIN.name()
                )
                .requestMatchers(HttpMethod.PATCH, "/oneseo/v3/aptitude-score/{memberId}").hasAnyAuthority(
                        Role.ADMIN.name()
                )
                .requestMatchers(HttpMethod.POST, "/oneseo/v3/image").hasAnyAuthority(
                        Role.APPLICANT.name(),
                        Role.ADMIN.name(),
                        Role.ROOT.name()
                )
                .requestMatchers(HttpMethod.DELETE, "/oneseo/v3/oneseo/me").hasAnyAuthority(
                        Role.APPLICANT.name(),
                        Role.ROOT.name()
                )
                .requestMatchers(HttpMethod.GET, "/oneseo/v3/oneseo/search").hasAnyAuthority(
                        Role.ADMIN.name(),
                        Role.ROOT.name()
                )
                .requestMatchers(HttpMethod.PUT, "/oneseo/v3/final-submit").hasAnyAuthority(
                        Role.APPLICANT.name()
                )

                // mock score calculate
                .requestMatchers(HttpMethod.POST, "/oneseo/v3/calculate-mock-score").permitAll()
                .anyRequest().permitAll()
        );
    }
}
