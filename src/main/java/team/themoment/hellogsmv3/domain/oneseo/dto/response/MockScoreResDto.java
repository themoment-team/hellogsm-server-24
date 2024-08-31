package team.themoment.hellogsmv3.domain.oneseo.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record MockScoreResDto(

		BigDecimal totalSubjectsScore,
		BigDecimal attendanceScore,
		BigDecimal volunteerScore,
		BigDecimal totalScore
) {
}
