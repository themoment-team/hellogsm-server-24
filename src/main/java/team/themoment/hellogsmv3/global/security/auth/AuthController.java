package team.themoment.hellogsmv3.global.security.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;
import team.themoment.hellogsmv3.domain.auth.type.Role;

import java.io.IOException;


@Controller
@RequiredArgsConstructor
@RequestMapping("/auth/v3")
public class AuthController {

    private final AuthEnvironment authEnvironment;

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof OAuth2AuthenticationToken) {
            new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        }
        String redirectUrl = buildRedirectUrl(isAdmin(auth));
        response.sendRedirect(redirectUrl);

    }

    protected final boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> Role.ADMIN.name().equals(authority.getAuthority()));
    }

    protected final String buildRedirectUrl(boolean isAdmin) {
        final String defaultTargetUrl = authEnvironment.redirectBaseUri();
        final String adminUrl = authEnvironment.redirectBaseUri();

        String targetUrl = isAdmin ? adminUrl : defaultTargetUrl;

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("logout", "success")
                .build()
                .toUriString();
    }
}
