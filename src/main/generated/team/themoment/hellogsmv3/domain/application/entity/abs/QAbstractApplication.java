package team.themoment.hellogsmv3.domain.application.entity.abs;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAbstractApplication is a Querydsl query type for AbstractApplication
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAbstractApplication extends EntityPathBase<AbstractApplication> {

    private static final long serialVersionUID = 1490118395L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAbstractApplication abstractApplication = new QAbstractApplication("abstractApplication");

    public final team.themoment.hellogsmv3.domain.applicant.entity.QApplicant applicant;

    public final team.themoment.hellogsmv3.domain.application.type.QEvaluationResult competencyEvaluationResult;

    public final team.themoment.hellogsmv3.domain.oneseo.entity.type.QDesiredMajors desiredMajors;

    public final EnumPath<team.themoment.hellogsmv3.domain.oneseo.entity.type.Major> finalMajor = createEnum("finalMajor", team.themoment.hellogsmv3.domain.oneseo.entity.type.Major.class);

    public final BooleanPath finalSubmitted = createBoolean("finalSubmitted");

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final NumberPath<java.math.BigDecimal> interviewScore = createNumber("interviewScore", java.math.BigDecimal.class);

    public final QAbstractMiddleSchoolGrade middleSchoolGrade;

    public final QAbstractPersonalInformation personalInformation;

    public final BooleanPath printsArrived = createBoolean("printsArrived");

    public final NumberPath<Long> registrationNumber = createNumber("registrationNumber", Long.class);

    public final team.themoment.hellogsmv3.domain.application.type.QEvaluationResult subjectEvaluationResult;

    public QAbstractApplication(String variable) {
        this(AbstractApplication.class, forVariable(variable), INITS);
    }

    public QAbstractApplication(Path<? extends AbstractApplication> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAbstractApplication(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAbstractApplication(PathMetadata metadata, PathInits inits) {
        this(AbstractApplication.class, metadata, inits);
    }

    public QAbstractApplication(Class<? extends AbstractApplication> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.applicant = inits.isInitialized("applicant") ? new team.themoment.hellogsmv3.domain.applicant.entity.QApplicant(forProperty("applicant")) : null;
        this.competencyEvaluationResult = inits.isInitialized("competencyEvaluationResult") ? new team.themoment.hellogsmv3.domain.application.type.QEvaluationResult(forProperty("competencyEvaluationResult")) : null;
        this.desiredMajors = inits.isInitialized("desiredMajors") ? new team.themoment.hellogsmv3.domain.oneseo.entity.type.QDesiredMajors(forProperty("desiredMajors")) : null;
        this.middleSchoolGrade = inits.isInitialized("middleSchoolGrade") ? new QAbstractMiddleSchoolGrade(forProperty("middleSchoolGrade")) : null;
        this.personalInformation = inits.isInitialized("personalInformation") ? new QAbstractPersonalInformation(forProperty("personalInformation")) : null;
        this.subjectEvaluationResult = inits.isInitialized("subjectEvaluationResult") ? new team.themoment.hellogsmv3.domain.application.type.QEvaluationResult(forProperty("subjectEvaluationResult")) : null;
    }

}

