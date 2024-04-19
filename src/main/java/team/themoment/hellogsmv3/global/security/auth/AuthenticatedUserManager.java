package team.themoment.hellogsmv3.global.security.auth;

import jakarta.servlet.http.HttpServletRequest;
import team.themoment.hellogsmv3.domain.auth.type.Role;

import java.time.LocalDateTime;

public interface AuthenticatedUserManager {
    Long getId();
    Role getRole();
    LocalDateTime getLastLoginTime();
    Role setRole(HttpServletRequest req, Role role);
}
