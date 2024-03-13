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
public final class GedMiddleSchoolGrade extends AbstractMiddleSchoolGrade {

    private BigDecimal gedTotalScore;

    private BigDecimal gedMaxScore;

    public GedMiddleSchoolGrade(
            @NonNull UUID id,
            @NonNull BigDecimal percentileRank,
            @NonNull BigDecimal gedTotalScore,
            @NonNull BigDecimal gedMaxScore
    ) {
        super(id, percentileRank);
        this.gedTotalScore = gedTotalScore;
        this.gedMaxScore = gedMaxScore;
    }
}
