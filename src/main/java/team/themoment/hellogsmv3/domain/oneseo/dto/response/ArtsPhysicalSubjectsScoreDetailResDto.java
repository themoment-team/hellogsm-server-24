package team.themoment.hellogsmv3.domain.oneseo.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ArtsPhysicalSubjectsScoreDetailResDto(
        BigDecimal score2_1,
        BigDecimal score2_2,
        BigDecimal score3_1
) {
}
