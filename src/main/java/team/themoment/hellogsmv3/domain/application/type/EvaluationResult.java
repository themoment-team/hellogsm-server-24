package team.themoment.hellogsmv3.domain.application.type;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvaluationResult {

    private EvaluationStatus evaluationStatus;

    private Screening preScreeningEvaluation;

    private Screening postScreeningEvaluation;

    private BigDecimal score;

    public Boolean isPass() {
        return Objects.equals(evaluationStatus, EvaluationStatus.PASS);
    }
}
