package team.themoment.hellogsmv3.domain.oneseo.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record MiddleSchoolAchievementResDto(

        TranscriptResDto transcript,
        BigDecimal totalScore,
        BigDecimal percentileRank,
        BigDecimal grade1Semester1Score,
        BigDecimal grade1Semester2Score,
        BigDecimal grade2Semester1Score,
        BigDecimal grade2Semester2Score,
        BigDecimal grade3Semester1Score,
        BigDecimal curricularSubtotalScore,
        BigDecimal extraCurricularSubtotalScore,
        BigDecimal artisticScore,
        BigDecimal attendanceScore,
        BigDecimal volunteerScore
) {
}
