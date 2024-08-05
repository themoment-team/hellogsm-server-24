package team.themoment.hellogsmv3.domain.application.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGedMiddleSchoolGrade is a Querydsl query type for GedMiddleSchoolGrade
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGedMiddleSchoolGrade extends EntityPathBase<GedMiddleSchoolGrade> {

    private static final long serialVersionUID = 1195837247L;

    public static final QGedMiddleSchoolGrade gedMiddleSchoolGrade = new QGedMiddleSchoolGrade("gedMiddleSchoolGrade");

    public final team.themoment.hellogsmv3.domain.application.entity.abs.QAbstractMiddleSchoolGrade _super = new team.themoment.hellogsmv3.domain.application.entity.abs.QAbstractMiddleSchoolGrade(this);

    public final NumberPath<java.math.BigDecimal> gedMaxScore = createNumber("gedMaxScore", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> gedTotalScore = createNumber("gedTotalScore", java.math.BigDecimal.class);

    //inherited
    public final ComparablePath<java.util.UUID> id = _super.id;

    //inherited
    public final NumberPath<java.math.BigDecimal> percentileRank = _super.percentileRank;

    //inherited
    public final NumberPath<java.math.BigDecimal> totalScore = _super.totalScore;

    //inherited
    public final StringPath transcript = _super.transcript;

    public QGedMiddleSchoolGrade(String variable) {
        super(GedMiddleSchoolGrade.class, forVariable(variable));
    }

    public QGedMiddleSchoolGrade(Path<GedMiddleSchoolGrade> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGedMiddleSchoolGrade(PathMetadata metadata) {
        super(GedMiddleSchoolGrade.class, metadata);
    }

}

