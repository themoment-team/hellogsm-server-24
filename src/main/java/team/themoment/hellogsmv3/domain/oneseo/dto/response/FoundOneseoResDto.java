package team.themoment.hellogsmv3.domain.oneseo.dto.response;

import lombok.Builder;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo;

@Builder
public record FoundOneseoResDto(

        Long oneseoId,
        String submitCode,
        Screening wantedScreening,
        DesiredMajorsResDto desiredMajors,
        OneseoPrivacyDetailResDto privacyDetail,
        MiddleSchoolAchievementResDto middleSchoolAchievement
) {
}
