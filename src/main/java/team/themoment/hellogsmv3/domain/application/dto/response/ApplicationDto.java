package team.themoment.hellogsmv3.domain.application.dto.response;

import lombok.Builder;
import team.themoment.hellogsmv3.domain.application.type.EvaluationStatus;
import team.themoment.hellogsmv3.domain.application.type.GraduationStatus;
import team.themoment.hellogsmv3.domain.application.type.Screening;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record ApplicationDto(
        Long applicantId,
        String applicantName,
        GraduationStatus graduation,
        String applicantPhoneNumber,
        String guardianPhoneNumber,
        String teacherName,
        String teacherPhoneNumber,
        Boolean isFinalSubmitted,
        Boolean isPrintsArrived,
        EvaluationStatus firstEvaluation,
        EvaluationStatus secondEvaluation,
        Screening screeningFirstEvaluationAt,
        Screening screeningSecondEvaluationAt,
        Long registrationNumber,
        BigDecimal secondScore,
        BigDecimal interviewScore
) {
}
