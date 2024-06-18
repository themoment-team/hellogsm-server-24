package team.themoment.hellogsmv3.domain.application.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record GeneralAdmissionGradeResDto(
        BigDecimal totalScore,
        BigDecimal percentileRank,
        BigDecimal grade1Semester1Score,
        BigDecimal grade1Semester2Score,
        BigDecimal grade2Semester1Score,
        BigDecimal grade2Semester2Score,
        BigDecimal grade3Semester1Score,
        BigDecimal artisticScore,
        BigDecimal curricularSubtotalScore,
        BigDecimal attendanceScore,
        BigDecimal volunteerScore,
        BigDecimal extracurricularSubtotalScore
) implements AdmissionGradeResDto {
}
