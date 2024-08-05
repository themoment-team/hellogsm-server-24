package team.themoment.hellogsmv3.domain.application.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGedApplication is a Querydsl query type for GedApplication
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGedApplication extends EntityPathBase<GedApplication> {

    private static final long serialVersionUID = 1834758497L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGedApplication gedApplication = new QGedApplication("gedApplication");

    public final team.themoment.hellogsmv3.domain.application.entity.abs.QAbstractApplication _super;

    // inherited
    public final team.themoment.hellogsmv3.domain.applicant.entity.QApplicant applicant;

    // inherited
    public final team.themoment.hellogsmv3.domain.application.type.QEvaluationResult competencyEvaluationResult;

    // inherited
    public final team.themoment.hellogsmv3.domain.oneseo.entity.type.QDesiredMajors desiredMajors;

    //inherited
    public final EnumPath<team.themoment.hellogsmv3.domain.oneseo.entity.type.Major> finalMajor;

    //inherited
    public final BooleanPath finalSubmitted;

    //inherited
    public final ComparablePath<java.util.UUID> id;

    //inherited
    public final NumberPath<java.math.BigDecimal> interviewScore;

    // inherited
    public final team.themoment.hellogsmv3.domain.application.entity.abs.QAbstractMiddleSchoolGrade middleSchoolGrade;

    // inherited
    public final team.themoment.hellogsmv3.domain.application.entity.abs.QAbstractPersonalInformation personalInformation;

    //inherited
    public final BooleanPath printsArrived;

    //inherited
    public final NumberPath<Long> registrationNumber;

    // inherited
    public final team.themoment.hellogsmv3.domain.application.type.QEvaluationResult subjectEvaluationResult;

    public QGedApplication(String variable) {
        this(GedApplication.class, forVariable(variable), INITS);
    }

    public QGedApplication(Path<GedApplication> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGedApplication(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGedApplication(PathMetadata metadata, PathInits inits) {
        this(GedApplication.class, metadata, inits);
    }

    public QGedApplication(Class<? extends GedApplication> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new team.themoment.hellogsmv3.domain.application.entity.abs.QAbstractApplication(type, metadata, inits);
        this.applicant = _super.applicant;
        this.competencyEvaluationResult = _super.competencyEvaluationResult;
        this.desiredMajors = _super.desiredMajors;
        this.finalMajor = _super.finalMajor;
        this.finalSubmitted = _super.finalSubmitted;
        this.id = _super.id;
        this.interviewScore = _super.interviewScore;
        this.middleSchoolGrade = _super.middleSchoolGrade;
        this.personalInformation = _super.personalInformation;
        this.printsArrived = _super.printsArrived;
        this.registrationNumber = _super.registrationNumber;
        this.subjectEvaluationResult = _super.subjectEvaluationResult;
    }

}

