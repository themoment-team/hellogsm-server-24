package team.themoment.hellogsmv3.domain.application.entity;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import team.themoment.hellogsmv3.domain.application.entity.abs.AbstractMiddleSchoolGrade;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class GraduateMiddleSchoolGrade extends AbstractMiddleSchoolGrade {

    private BigDecimal attendanceScore;

    private BigDecimal volunteerScore;

    private GraduateMiddleSchoolGrade(
            @NonNull UUID id,
            @NonNull BigDecimal percentileRank,
            @NonNull BigDecimal attendanceScore,
            @NonNull BigDecimal volunteerScore
    ) {
        super(id, percentileRank);
        this.attendanceScore = attendanceScore;
        this.volunteerScore = volunteerScore;
    }
}
