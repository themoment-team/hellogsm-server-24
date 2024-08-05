package team.themoment.hellogsmv3.domain.application.type;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEvaluationResult is a Querydsl query type for EvaluationResult
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QEvaluationResult extends BeanPath<EvaluationResult> {

    private static final long serialVersionUID = -1223364487L;

    public static final QEvaluationResult evaluationResult = new QEvaluationResult("evaluationResult");

    public final EnumPath<EvaluationStatus> evaluationStatus = createEnum("evaluationStatus", EvaluationStatus.class);

    public final EnumPath<team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening> postScreeningEvaluation = createEnum("postScreeningEvaluation", team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening.class);

    public final EnumPath<team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening> preScreeningEvaluation = createEnum("preScreeningEvaluation", team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening.class);

    public final NumberPath<java.math.BigDecimal> score = createNumber("score", java.math.BigDecimal.class);

    public QEvaluationResult(String variable) {
        super(EvaluationResult.class, forVariable(variable));
    }

    public QEvaluationResult(Path<? extends EvaluationResult> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEvaluationResult(PathMetadata metadata) {
        super(EvaluationResult.class, metadata);
    }

}

