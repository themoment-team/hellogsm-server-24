package team.themoment.hellogsmv3.domain.applicant.dto.request;

import jakarta.validation.constraints.NotNull;

public record AuthenticateCodeReqDto(
        @NotNull String code
) {
}
