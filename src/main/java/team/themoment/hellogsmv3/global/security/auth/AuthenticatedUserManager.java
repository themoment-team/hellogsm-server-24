package team.themoment.hellogsmv3.global.security.auth;

import jakarta.servlet.http.HttpServletRequest;
import team.themoment.hellogsmv3.domain.member.entity.type.Role;

public interface AuthenticatedUserManager {
    Long getId();
    Role setRole(HttpServletRequest req, Role role);
}
