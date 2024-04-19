package team.themoment.hellogsmv3.global.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;
import team.themoment.hellogsmv3.domain.auth.type.Role;

import java.io.IOException;

public class CustomUrlLogoutSuccessHandler implements LogoutSuccessHandler {

    private final String defaultTargetUrl;
    private final String adminUrl;

    public CustomUrlLogoutSuccessHandler(String defaultTargetUrl, String adminUrl) {
        this.defaultTargetUrl = defaultTargetUrl;
        this.adminUrl = adminUrl;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String redirectUrl = buildRedirectUrl(isAdmin(authentication));
        response.sendRedirect(redirectUrl);
    }

    protected final boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> Role.ADMIN.name().equals(authority.getAuthority()));
    }

    protected final String buildRedirectUrl(boolean isAdmin) {
        String targetUrl = isAdmin ? adminUrl : defaultTargetUrl;

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("logout", "success")
                .build()
                .toUriString();
    }
}
