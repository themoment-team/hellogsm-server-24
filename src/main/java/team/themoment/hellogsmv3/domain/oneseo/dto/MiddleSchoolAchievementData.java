package team.themoment.hellogsmv3.domain.oneseo.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record MiddleSchoolAchievementData(

        List<BigDecimal> achievement1_2,
        List<BigDecimal> achievement2_1,
        List<BigDecimal> achievement2_2,
        List<BigDecimal> achievement3_1,
        List<BigDecimal> achievement3_2,
        List<BigDecimal> artsPhysicalAchievement,
        List<BigDecimal> absentDays,
        List<BigDecimal> attendanceDays,
        List<BigDecimal> volunteerTime,
        String liberalSystem,
        String freeSemester,
        BigDecimal gedTotalScore,
        BigDecimal gedMaxScore
) {
}
