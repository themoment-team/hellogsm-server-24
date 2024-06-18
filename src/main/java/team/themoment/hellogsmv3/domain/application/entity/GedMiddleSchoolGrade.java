package team.themoment.hellogsmv3.domain.application.entity;

import jakarta.persistence.Entity;
import lombok.*;
import team.themoment.hellogsmv3.domain.application.entity.abs.AbstractMiddleSchoolGrade;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class GedMiddleSchoolGrade extends AbstractMiddleSchoolGrade {

    private BigDecimal gedTotalScore;

    private BigDecimal gedMaxScore;

    @Builder
    public GedMiddleSchoolGrade(
            UUID id,
            @NonNull BigDecimal percentileRank,
            @NonNull BigDecimal totalScore,
            @NonNull String transcript,
            @NonNull BigDecimal gedTotalScore,
            @NonNull BigDecimal gedMaxScore
    ) {
        super(id, percentileRank, totalScore, transcript);
        this.gedTotalScore = gedTotalScore;
        this.gedMaxScore = gedMaxScore;
    }
}
