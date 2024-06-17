package team.themoment.hellogsmv3.domain.auth.event;

import team.themoment.hellogsmv3.domain.auth.entity.Authentication;

public record DeleteAuthenticationEvent (
        Authentication authentication
) {
}
