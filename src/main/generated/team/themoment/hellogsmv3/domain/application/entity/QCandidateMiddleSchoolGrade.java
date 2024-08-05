package team.themoment.hellogsmv3.domain.application.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCandidateMiddleSchoolGrade is a Querydsl query type for CandidateMiddleSchoolGrade
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCandidateMiddleSchoolGrade extends EntityPathBase<CandidateMiddleSchoolGrade> {

    private static final long serialVersionUID = 730729826L;

    public static final QCandidateMiddleSchoolGrade candidateMiddleSchoolGrade = new QCandidateMiddleSchoolGrade("candidateMiddleSchoolGrade");

    public final team.themoment.hellogsmv3.domain.application.entity.abs.QAbstractMiddleSchoolGrade _super = new team.themoment.hellogsmv3.domain.application.entity.abs.QAbstractMiddleSchoolGrade(this);

    public final NumberPath<java.math.BigDecimal> artisticScore = createNumber("artisticScore", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> attendanceScore = createNumber("attendanceScore", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> curricularSubtotalScore = createNumber("curricularSubtotalScore", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> extraCurricularSubtotalScore = createNumber("extraCurricularSubtotalScore", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> grade1Semester1Score = createNumber("grade1Semester1Score", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> grade1Semester2Score = createNumber("grade1Semester2Score", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> grade2Semester1Score = createNumber("grade2Semester1Score", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> grade2Semester2Score = createNumber("grade2Semester2Score", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> grade3Semester1Score = createNumber("grade3Semester1Score", java.math.BigDecimal.class);

    //inherited
    public final ComparablePath<java.util.UUID> id = _super.id;

    //inherited
    public final NumberPath<java.math.BigDecimal> percentileRank = _super.percentileRank;

    //inherited
    public final NumberPath<java.math.BigDecimal> totalScore = _super.totalScore;

    //inherited
    public final StringPath transcript = _super.transcript;

    public final NumberPath<java.math.BigDecimal> volunteerScore = createNumber("volunteerScore", java.math.BigDecimal.class);

    public QCandidateMiddleSchoolGrade(String variable) {
        super(CandidateMiddleSchoolGrade.class, forVariable(variable));
    }

    public QCandidateMiddleSchoolGrade(Path<CandidateMiddleSchoolGrade> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCandidateMiddleSchoolGrade(PathMetadata metadata) {
        super(CandidateMiddleSchoolGrade.class, metadata);
    }

}

