package team.themoment.hellogsmv3.domain.oneseo.dto.request;

import java.math.BigDecimal;
import java.util.List;

public record MiddleSchoolAchievementReqDto(

        List<Integer> achievement1_1,
        List<Integer> achievement1_2,
        List<Integer> achievement2_1,
        List<Integer> achievement2_2,
        List<Integer> achievement3_1,
        List<String> generalSubjects,
        List<String> newSubjects,
        List<Integer> artsPhysicalAchievement,
        List<String> artsPhysicalSubjects,
        List<Integer> absentDays,
        List<Integer> attendanceDays,
        List<Integer> volunteerTime,
        String liberalSystem,
        String freeSemester,
        BigDecimal gedTotalScore,
        BigDecimal gedMaxScore
) {
}
