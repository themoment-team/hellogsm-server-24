package team.themoment.hellogsmv3.domain.application.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record GedAdmissionGradeResDto(
        BigDecimal totalScore,
        BigDecimal percentileRank,
        BigDecimal gedTotalScore,
        BigDecimal gedMaxScore
) implements AdmissionGradeResDto {
}
