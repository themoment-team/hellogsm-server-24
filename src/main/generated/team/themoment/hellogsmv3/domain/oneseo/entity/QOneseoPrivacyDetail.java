package team.themoment.hellogsmv3.domain.oneseo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOneseoPrivacyDetail is a Querydsl query type for OneseoPrivacyDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOneseoPrivacyDetail extends EntityPathBase<OneseoPrivacyDetail> {

    private static final long serialVersionUID = -1290489800L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOneseoPrivacyDetail oneseoPrivacyDetail = new QOneseoPrivacyDetail("oneseoPrivacyDetail");

    public final StringPath address = createString("address");

    public final StringPath detailAddress = createString("detailAddress");

    public final EnumPath<team.themoment.hellogsmv3.domain.oneseo.entity.type.GraduationType> graduationType = createEnum("graduationType", team.themoment.hellogsmv3.domain.oneseo.entity.type.GraduationType.class);

    public final StringPath guardianName = createString("guardianName");

    public final StringPath guardianPhoneNumber = createString("guardianPhoneNumber");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QOneseo oneseo;

    public final StringPath profileImg = createString("profileImg");

    public final StringPath relationshipWithGuardian = createString("relationshipWithGuardian");

    public final StringPath schoolAddress = createString("schoolAddress");

    public final StringPath schoolName = createString("schoolName");

    public final StringPath schoolTeacherName = createString("schoolTeacherName");

    public final StringPath schoolTeacherPhoneNumber = createString("schoolTeacherPhoneNumber");

    public QOneseoPrivacyDetail(String variable) {
        this(OneseoPrivacyDetail.class, forVariable(variable), INITS);
    }

    public QOneseoPrivacyDetail(Path<? extends OneseoPrivacyDetail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOneseoPrivacyDetail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOneseoPrivacyDetail(PathMetadata metadata, PathInits inits) {
        this(OneseoPrivacyDetail.class, metadata, inits);
    }

    public QOneseoPrivacyDetail(Class<? extends OneseoPrivacyDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.oneseo = inits.isInitialized("oneseo") ? new QOneseo(forProperty("oneseo"), inits.get("oneseo")) : null;
    }

}

