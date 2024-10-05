package team.themoment.hellogsmv3.domain.oneseo.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.math.BigDecimal;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

@Builder
@JsonInclude(NON_NULL)
public record ArtsPhysicalSubjectsScoreDetailResDto(
        BigDecimal score1_2,
        BigDecimal score2_1,
        BigDecimal score2_2,
        BigDecimal score3_1
) {
}
