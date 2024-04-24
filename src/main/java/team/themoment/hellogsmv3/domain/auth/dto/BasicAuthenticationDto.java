package team.themoment.hellogsmv3.domain.auth.dto;

import team.themoment.hellogsmv3.domain.auth.type.Role;

public record BasicAuthenticationDto(
        Long id,
        String provider,
        String providerId,
        Role role
) {

}
