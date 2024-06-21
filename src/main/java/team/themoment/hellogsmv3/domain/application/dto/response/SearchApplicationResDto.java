package team.themoment.hellogsmv3.domain.application.dto.response;

import lombok.Builder;
import team.themoment.hellogsmv3.domain.application.type.EvaluationStatus;
import team.themoment.hellogsmv3.domain.application.type.GraduationStatus;
import team.themoment.hellogsmv3.domain.application.type.Screening;

import java.math.BigDecimal;
import java.util.UUID;

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
