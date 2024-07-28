package team.themoment.hellogsmv3.domain.oneseo.dto.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record InterviewScoreReqDto(
        @NotNull
        BigDecimal interviewScore
) {
}
