package team.themoment.hellogsmv3.domain.application.type;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@NoArgsConstructor
public class EvaluationResult {

    private EvaluationStatus evaluationStatus;

    private Screening preScreeningEvaluation;

    private Screening postScreeningEvaluation;

    public Boolean isPass() {
        return Objects.equals(evaluationStatus, EvaluationStatus.PASS);
    }
}
