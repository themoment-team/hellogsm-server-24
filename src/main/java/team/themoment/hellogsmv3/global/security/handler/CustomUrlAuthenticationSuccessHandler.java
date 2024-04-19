package team.themoment.hellogsmv3.global.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;
import team.themoment.hellogsmv3.domain.auth.type.Role;

import java.io.IOException;

public class CustomUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final String defaultTargetUrl;
    private final String adminUrl;

    public CustomUrlAuthenticationSuccessHandler(String defaultTargetUrl, String adminUrl) {
        this.defaultTargetUrl = defaultTargetUrl;
        this.adminUrl = adminUrl;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        boolean isUnAuthentication = authentication.getAuthorities().stream()
                .anyMatch(authority -> Role.UNAUTHENTICATED.name().equals(authority.getAuthority()));

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> Role.ADMIN.name().equals(authority.getAuthority()));

        String redirectUrlWithParameter = UriComponentsBuilder
                .fromUriString(getTargetUrl(isAdmin))
                .queryParam("verification", !isUnAuthentication)
                .build()
                .toUriString();

        response.sendRedirect(redirectUrlWithParameter);

        clearAuthenticationAttributes(request);
    }

    protected final String getTargetUrl(boolean isAdmin) {
        if (isAdmin) {
            return adminUrl;
        } else {
            return defaultTargetUrl;
        }
    }

    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }

}
