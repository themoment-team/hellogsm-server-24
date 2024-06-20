package team.themoment.hellogsmv3.domain.application.entity.param;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.NonNull;
import team.themoment.hellogsmv3.domain.application.type.DesiredMajors;
import team.themoment.hellogsmv3.domain.application.type.EvaluationResult;
import team.themoment.hellogsmv3.domain.application.type.Major;

import java.math.BigDecimal;

@Builder
public record AbstractApplicationStatusParameter(
        @NonNull Boolean finalSubmitted,
        @NonNull Boolean printsArrived,
        @Nullable EvaluationResult subjectEvaluationResult,
        @Nullable EvaluationResult competencyEvaluationResult,
        @Nullable Long registrationNumber,
        @Nullable BigDecimal competencyExamScore,
        @Nullable BigDecimal interviewScore,
        @NonNull DesiredMajors desiredMajors,
        @Nullable Major finalMajor) {

    public static AbstractApplicationStatusParameter init(DesiredMajors desiredMajors) {
        return AbstractApplicationStatusParameter.builder()
                .finalSubmitted(false)
                .printsArrived(false)
                .subjectEvaluationResult(null)
                .competencyEvaluationResult(null)
                .registrationNumber(null)
                .competencyExamScore(null)
                .interviewScore(null)
                .desiredMajors(desiredMajors)
                .finalMajor(null)
                .build();
    }
}
