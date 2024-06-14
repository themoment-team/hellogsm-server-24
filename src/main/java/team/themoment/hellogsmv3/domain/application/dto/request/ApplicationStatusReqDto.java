package team.themoment.hellogsmv3.domain.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record ApplicationStatusReqDto(
        @NotNull
        Boolean isFinalSubmitted,
        @NotNull
        Boolean isPrintsArrived,
        @Pattern(regexp = "^(NOT_YET|PASS|FALL)$")
        @NotBlank
        String firstEvaluation,
        @Pattern(regexp = "^(NOT_YET|PASS|FALL)$")
        @NotBlank
        String secondEvaluation,
        @Pattern(regexp = "^(GENERAL|SOCIAL|SPECIAL_VETERANS|SPECIAL_ADMISSION)$")
        String screeningFirstEvaluationAt,
        @Pattern(regexp = "^(GENERAL|SOCIAL|SPECIAL_VETERANS|SPECIAL_ADMISSION)$")
        String screeningSecondEvaluationAt,
        Long registrationNumber,
        BigDecimal secondScore,
        @Pattern(regexp = "^(IOT|SW|AI)$")
        String finalMajor
) {
}
