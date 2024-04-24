package team.themoment.hellogsmv3.global.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;
import team.themoment.hellogsmv3.domain.auth.type.Role;

import java.io.IOException;

@RequiredArgsConstructor
public class CustomUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final String redirectBaseUri;
    private final String redirectAdminUri;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
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
            return redirectAdminUri;
        } else {
            return redirectBaseUri;
        }
    }

    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }

}
