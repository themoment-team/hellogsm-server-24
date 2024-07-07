package team.themoment.hellogsmv3.domain.oneseo.dto.response;

import lombok.Builder;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.Major;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo;

import java.math.BigDecimal;

@Builder
public record EntranceTestResultResDto(

        BigDecimal documentEvaluationScore,
        YesNo firstTestPassYn,
        BigDecimal aptitudeEvaluationScore,
        BigDecimal interviewScore,
        YesNo secondTestPassYn,
        Major decidedMajor
) {
}
