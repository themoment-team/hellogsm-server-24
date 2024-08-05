package team.themoment.hellogsmv3.domain.application.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGraduateMiddleSchoolGrade is a Querydsl query type for GraduateMiddleSchoolGrade
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGraduateMiddleSchoolGrade extends EntityPathBase<GraduateMiddleSchoolGrade> {

    private static final long serialVersionUID = -590669236L;

    public static final QGraduateMiddleSchoolGrade graduateMiddleSchoolGrade = new QGraduateMiddleSchoolGrade("graduateMiddleSchoolGrade");

    public final team.themoment.hellogsmv3.domain.application.entity.abs.QAbstractMiddleSchoolGrade _super = new team.themoment.hellogsmv3.domain.application.entity.abs.QAbstractMiddleSchoolGrade(this);

    public final NumberPath<java.math.BigDecimal> attendanceScore = createNumber("attendanceScore", java.math.BigDecimal.class);

    //inherited
    public final ComparablePath<java.util.UUID> id = _super.id;

    //inherited
    public final NumberPath<java.math.BigDecimal> percentileRank = _super.percentileRank;

    //inherited
    public final NumberPath<java.math.BigDecimal> totalScore = _super.totalScore;

    //inherited
    public final StringPath transcript = _super.transcript;

    public final NumberPath<java.math.BigDecimal> volunteerScore = createNumber("volunteerScore", java.math.BigDecimal.class);

    public QGraduateMiddleSchoolGrade(String variable) {
        super(GraduateMiddleSchoolGrade.class, forVariable(variable));
    }

    public QGraduateMiddleSchoolGrade(Path<GraduateMiddleSchoolGrade> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGraduateMiddleSchoolGrade(PathMetadata metadata) {
        super(GraduateMiddleSchoolGrade.class, metadata);
    }

}

