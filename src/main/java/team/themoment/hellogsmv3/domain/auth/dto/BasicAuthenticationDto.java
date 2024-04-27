package team.themoment.hellogsmv3.domain.auth.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import team.themoment.hellogsmv3.domain.auth.type.Role;

@Builder
public record BasicAuthenticationDto(
        Long id,
        String provider,
        String providerId,
        @Enumerated(EnumType.STRING)
        Role role
) {

}
