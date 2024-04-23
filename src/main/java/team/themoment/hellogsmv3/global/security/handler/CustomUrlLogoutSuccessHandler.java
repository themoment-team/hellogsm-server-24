package team.themoment.hellogsmv3.global.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;
import team.themoment.hellogsmv3.domain.auth.type.Role;

import java.io.IOException;

@RequiredArgsConstructor
public class CustomUrlLogoutSuccessHandler implements LogoutSuccessHandler {

    private final String redirectBaseUri;
    private final String redirectAdminUri;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String redirectUrl = buildRedirectUrl(isAdmin(authentication));
        response.sendRedirect(redirectUrl);
    }

    protected final boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> Role.ADMIN.name().equals(authority.getAuthority()));
    }

    protected final String buildRedirectUrl(boolean isAdmin) {
        String targetUrl = isAdmin ? redirectAdminUri : redirectBaseUri;

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("logout", "success")
                .build()
                .toUriString();
    }
}
