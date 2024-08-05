package team.themoment.hellogsmv3.domain.application.entity.abs;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAbstractMiddleSchoolGrade is a Querydsl query type for AbstractMiddleSchoolGrade
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAbstractMiddleSchoolGrade extends EntityPathBase<AbstractMiddleSchoolGrade> {

    private static final long serialVersionUID = -2084560551L;

    public static final QAbstractMiddleSchoolGrade abstractMiddleSchoolGrade = new QAbstractMiddleSchoolGrade("abstractMiddleSchoolGrade");

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final NumberPath<java.math.BigDecimal> percentileRank = createNumber("percentileRank", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> totalScore = createNumber("totalScore", java.math.BigDecimal.class);

    public final StringPath transcript = createString("transcript");

    public QAbstractMiddleSchoolGrade(String variable) {
        super(AbstractMiddleSchoolGrade.class, forVariable(variable));
    }

    public QAbstractMiddleSchoolGrade(Path<? extends AbstractMiddleSchoolGrade> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAbstractMiddleSchoolGrade(PathMetadata metadata) {
        super(AbstractMiddleSchoolGrade.class, metadata);
    }

}

