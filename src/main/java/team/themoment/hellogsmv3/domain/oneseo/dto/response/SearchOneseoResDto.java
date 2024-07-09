package team.themoment.hellogsmv3.domain.oneseo.dto.response;

import team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo;

import java.math.BigDecimal;

public record SearchOneseoResDto(

        Long memberId,
        Long submitCode,
        YesNo realOneseoArrivedYn,
        String name,
        Screening screening,
        String schoolName,
        String phoneNumber,
        String guardianPhoneNumber,
        String schoolTeacherPhoneNumber,
        YesNo firstTestPassYn,
        BigDecimal aptitudeEvaluationScore,
        BigDecimal interviewScore,
        YesNo secondTestPassYn
) {
}
