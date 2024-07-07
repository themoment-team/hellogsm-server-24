package team.themoment.hellogsmv3.domain.oneseo.dto.response;

import java.math.BigDecimal;

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
        Integer attendanceDay,
        BigDecimal attendanceScore,
        BigDecimal volunteerScore
) {
}
