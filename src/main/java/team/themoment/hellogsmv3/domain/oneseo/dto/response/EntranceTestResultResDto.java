package team.themoment.hellogsmv3.domain.oneseo.dto.response;

import team.themoment.hellogsmv3.domain.oneseo.entity.type.Major;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo;

import java.math.BigDecimal;

public record EntranceTestResultResDto(

        BigDecimal firstTestResultScore,
        YesNo firstTestPassYn,
        BigDecimal secondTestResultScore,
        BigDecimal interviewScore,
        YesNo secondTestPassYn,
        Major decidedMajor
) {
}
