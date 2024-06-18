package team.themoment.hellogsmv3.domain.application.entity;

import jakarta.persistence.Entity;
import lombok.*;
import team.themoment.hellogsmv3.domain.application.entity.abs.AbstractMiddleSchoolGrade;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class GraduateMiddleSchoolGrade extends AbstractMiddleSchoolGrade {

    private BigDecimal attendanceScore;

    private BigDecimal volunteerScore;

    @Builder
    private GraduateMiddleSchoolGrade(
            UUID id,
            @NonNull BigDecimal percentileRank,
            @NonNull BigDecimal totalScore,
            @NonNull String transcript,
            @NonNull BigDecimal attendanceScore,
            @NonNull BigDecimal volunteerScore
    ) {
        super(id, percentileRank, totalScore, transcript);
        this.attendanceScore = attendanceScore;
        this.volunteerScore = volunteerScore;
    }
}
