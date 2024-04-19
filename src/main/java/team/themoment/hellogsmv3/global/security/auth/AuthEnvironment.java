package team.themoment.hellogsmv3.global.security.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "auth")
public record AuthEnvironment(
        String redirectBaseUri,
        String redirectAdminUri,
        String redirectLoginFailureUri,
        List<String> allowedOrigins
) {
}
