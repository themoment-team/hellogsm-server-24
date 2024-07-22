package team.themoment.hellogsmv3.domain.oneseo.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record MockScoreResDto(

		@JsonInclude(JsonInclude.Include.NON_NULL)
		BigDecimal generalSubjectsScore,
		@JsonInclude(JsonInclude.Include.NON_NULL)
		BigDecimal artsPhysicalSubjectsScore,
		BigDecimal totalSubjectsScore,
		BigDecimal attendanceScore,
		BigDecimal volunteerScore,
		BigDecimal totalNonSubjectsScore,
		BigDecimal totalScore
) {
}
