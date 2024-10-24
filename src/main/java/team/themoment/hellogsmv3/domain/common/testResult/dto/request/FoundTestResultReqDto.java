package team.themoment.hellogsmv3.domain.common.testResult.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record FoundTestResultReqDto(
        @Pattern(regexp = "^0(?:\\d|\\d{2})(?:\\d{3}|\\d{4})\\d{4}$")
        @NotNull String phoneNumber,
        @NotNull String code,
        @NotNull String submitCode
) {
}
