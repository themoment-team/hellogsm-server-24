package team.themoment.hellogsmv3.domain.application.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import team.themoment.hellogsmv3.domain.application.type.EvaluationStatus;
import team.themoment.hellogsmv3.domain.application.type.Screening;

import java.math.BigDecimal;

public record ApplicationStatusReqDto(
        @NotNull
        Boolean isFinalSubmitted,
        @NotNull
        Boolean isPrintsArrived,
        @NotBlank
        EvaluationStatus firstEvaluation,
        @NotBlank
        EvaluationStatus secondEvaluation,
        Screening screeningFirstEvaluationAt,
        Screening screeningSecondEvaluationAt,
        Long registrationNumber,
        BigDecimal secondScore,
        @Pattern(regexp = "^(IOT|SW|AI)$")
        String finalMajor
) {
}
