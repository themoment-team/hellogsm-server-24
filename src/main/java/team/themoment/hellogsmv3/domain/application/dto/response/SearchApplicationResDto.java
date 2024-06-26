package team.themoment.hellogsmv3.domain.application.dto.response;

import lombok.Builder;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.EvaluationStatus;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.GraduationStatus;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening;

import java.math.BigDecimal;

@Builder
public record SearchApplicationResDto(
        Long applicantId,
        Boolean isFinalSubmitted,
        Boolean isPrintsArrived,
        String applicantName,
        Screening screening,
        String schoolName,
        String applicantPhoneNumber,
        String guardianPhoneNumber,
        String teacherPhoneNumber,
        EvaluationStatus firstEvaluation,
        EvaluationStatus secondEvaluation,
        Long registrationNumber,
        BigDecimal secondScore,
        BigDecimal interviewScore,
        GraduationStatus graduation
) {
}
