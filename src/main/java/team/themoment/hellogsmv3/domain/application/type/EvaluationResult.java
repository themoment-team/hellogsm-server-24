package team.themoment.hellogsmv3.domain.application.type;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor
public class EvaluationResult {

    @Enumerated(EnumType.STRING)
    private EvaluationStatus evaluationStatus;

    private Screening preScreeningEvaluation;

    private Screening postScreeningEvaluation;

    public Boolean isPass() {
        return Objects.equals(evaluationStatus, EvaluationStatus.PASS);
    }
}
