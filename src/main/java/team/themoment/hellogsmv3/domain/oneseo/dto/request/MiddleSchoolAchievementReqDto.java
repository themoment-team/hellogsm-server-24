package team.themoment.hellogsmv3.domain.oneseo.dto.request;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record MiddleSchoolAchievementReqDto(

        List<BigDecimal> achievement1_2,
        List<BigDecimal> achievement2_1,
        List<BigDecimal> achievement2_2,
        List<BigDecimal> achievement3_1,
        List<BigDecimal> achievement3_2,
        List<String> generalSubjects,
        List<String> newSubjects,
        List<BigDecimal> artsPhysicalAchievement,
        List<String> artsPhysicalSubjects,
        List<BigDecimal> absentDays,
        List<BigDecimal> attendanceDays,
        List<BigDecimal> volunteerTime,
        String liberalSystem,
        String freeSemester,
        BigDecimal gedTotalScore,
        BigDecimal gedMaxScore
) {
}
