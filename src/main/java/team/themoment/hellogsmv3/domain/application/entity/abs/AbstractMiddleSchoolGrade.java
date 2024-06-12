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
@ToString
public abstract class AbstractMiddleSchoolGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    @NotNull
    @Column(name = "percentile_rank")
    protected BigDecimal percentileRank;

    public AbstractMiddleSchoolGrade(UUID id, @NonNull BigDecimal percentileRank) {
        this.id = id;
        this.percentileRank = percentileRank;
    }
}
