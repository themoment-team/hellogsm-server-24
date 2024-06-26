package team.themoment.hellogsmv3.domain.application.type;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.EvaluationStatus;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening;

import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvaluationResult {

    @Enumerated(EnumType.STRING)
    private EvaluationStatus evaluationStatus;

    @Enumerated(EnumType.STRING)
    private Screening preScreeningEvaluation;

    @Enumerated(EnumType.STRING)
    private Screening postScreeningEvaluation;

    private BigDecimal score;

    public Boolean isPass() {
        return Objects.equals(evaluationStatus, EvaluationStatus.PASS);
    }
}
