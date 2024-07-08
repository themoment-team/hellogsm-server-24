package team.themoment.hellogsmv3.domain.oneseo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import team.themoment.hellogsmv3.domain.application.type.EvaluationStatus;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.Major;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo;

import java.math.BigDecimal;

public record OneseoStatusReqDto(
        @NotNull
        YesNo finalSubmittedYn,
        @NotNull
        YesNo realOneseoArrivedYn,
        @NotBlank
        YesNo firstTestPassYn,
        @NotBlank
        YesNo secondTestPassYn,
        Screening appliedScreening,
        Long oneseoSubmitCode,
        BigDecimal secondTestResultScore,
        BigDecimal interviewScore,
        Major decidedMajor
) {
}
