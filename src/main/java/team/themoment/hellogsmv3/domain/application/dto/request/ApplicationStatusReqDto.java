package team.themoment.hellogsmv3.domain.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.EvaluationStatus;
import team.themoment.hellogsmv3.domain.application.type.Major;
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
        BigDecimal interviewScore,
        Major finalMajor
) {
}
