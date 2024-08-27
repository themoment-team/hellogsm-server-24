package team.themoment.hellogsmv3.domain.oneseo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOneseo is a Querydsl query type for Oneseo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOneseo extends EntityPathBase<Oneseo> {

    private static final long serialVersionUID = 985068353L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOneseo oneseo = new QOneseo("oneseo");

    public final EnumPath<team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening> appliedScreening = createEnum("appliedScreening", team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening.class);

    public final EnumPath<team.themoment.hellogsmv3.domain.oneseo.entity.type.Major> decidedMajor = createEnum("decidedMajor", team.themoment.hellogsmv3.domain.oneseo.entity.type.Major.class);

    public final team.themoment.hellogsmv3.domain.oneseo.entity.type.QDesiredMajors desiredMajors;

    public final EnumPath<team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo> entranceIntentionYn = createEnum("entranceIntentionYn", team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo.class);

    public final QEntranceTestResult entranceTestResult;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final team.themoment.hellogsmv3.domain.member.entity.QMember member;

    public final QOneseoPrivacyDetail oneseoPrivacyDetail;

    public final StringPath oneseoSubmitCode = createString("oneseoSubmitCode");

    public final EnumPath<team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo> realOneseoArrivedYn = createEnum("realOneseoArrivedYn", team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo.class);

    public final EnumPath<team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening> wantedScreening = createEnum("wantedScreening", team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening.class);

    public QOneseo(String variable) {
        this(Oneseo.class, forVariable(variable), INITS);
    }

    public QOneseo(Path<? extends Oneseo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOneseo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOneseo(PathMetadata metadata, PathInits inits) {
        this(Oneseo.class, metadata, inits);
    }

    public QOneseo(Class<? extends Oneseo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.desiredMajors = inits.isInitialized("desiredMajors") ? new team.themoment.hellogsmv3.domain.oneseo.entity.type.QDesiredMajors(forProperty("desiredMajors")) : null;
        this.entranceTestResult = inits.isInitialized("entranceTestResult") ? new QEntranceTestResult(forProperty("entranceTestResult"), inits.get("entranceTestResult")) : null;
        this.member = inits.isInitialized("member") ? new team.themoment.hellogsmv3.domain.member.entity.QMember(forProperty("member")) : null;
        this.oneseoPrivacyDetail = inits.isInitialized("oneseoPrivacyDetail") ? new QOneseoPrivacyDetail(forProperty("oneseoPrivacyDetail"), inits.get("oneseoPrivacyDetail")) : null;
    }

}

