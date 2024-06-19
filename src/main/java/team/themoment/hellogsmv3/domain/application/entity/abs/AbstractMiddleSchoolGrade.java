package team.themoment.hellogsmv3.domain.application.entity.abs;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class AbstractMiddleSchoolGrade implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    @NotNull
    @Column(name = "percentile_rank")
    protected BigDecimal percentileRank;

    protected BigDecimal totalScore;

    @Column(columnDefinition = "TEXT")
    protected String transcript;

    public AbstractMiddleSchoolGrade(UUID id, @NonNull BigDecimal percentileRank, @NonNull BigDecimal totalScore, @NonNull String transcript) {
        this.id = id;
        this.percentileRank = percentileRank;
        this.totalScore = totalScore;
        this.transcript = transcript;
    }

    @Override
    public AbstractMiddleSchoolGrade clone() {
        try {
            return (AbstractMiddleSchoolGrade) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
