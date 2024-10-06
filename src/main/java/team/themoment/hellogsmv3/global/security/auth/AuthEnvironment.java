package team.themoment.hellogsmv3.global.security.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "auth.redirect-url")
public record AuthEnvironment(
        String dryRun,
        String hgStudent,
        String hgAdmin,
        List<String> allowedOrigins,
        String loginEndPointBaseUri,
        String loginProcessingUri
) {
}
