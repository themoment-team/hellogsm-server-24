package team.themoment.hellogsmv3.domain.oneseo.dto.response;

import team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo;

public record FoundOneseoResDto(

        Long oneseoId,
        Long submitCode,
        Screening wantedScreening,
        DesiredMajorsResDto desiredMajors,
        YesNo finalSubmittedYn,
        YesNo realOneseoArrivedYn,
        OneseoPrivacyDetailResDto privacyDetail,
        MiddleSchoolAchievementResDto middleSchoolAchievement,
        EntranceTestResultResDto entranceTestResult
) {
}
