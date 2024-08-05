package team.themoment.hellogsmv3.domain.oneseo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEntranceTestResult is a Querydsl query type for EntranceTestResult
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEntranceTestResult extends EntityPathBase<EntranceTestResult> {

    private static final long serialVersionUID = 358467247L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEntranceTestResult entranceTestResult = new QEntranceTestResult("entranceTestResult");

    public final NumberPath<java.math.BigDecimal> aptitudeEvaluationScore = createNumber("aptitudeEvaluationScore", java.math.BigDecimal.class);

    public final EnumPath<team.themoment.hellogsmv3.domain.oneseo.entity.type.Major> decidedMajor = createEnum("decidedMajor", team.themoment.hellogsmv3.domain.oneseo.entity.type.Major.class);

    public final NumberPath<java.math.BigDecimal> documentEvaluationScore = createNumber("documentEvaluationScore", java.math.BigDecimal.class);

    public final QEntranceTestFactorsDetail entranceTestFactorsDetail;

    public final EnumPath<team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo> firstTestPassYn = createEnum("firstTestPassYn", team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<java.math.BigDecimal> interviewScore = createNumber("interviewScore", java.math.BigDecimal.class);

    public final QOneseo oneseo;

    public final EnumPath<team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo> secondTestPassYn = createEnum("secondTestPassYn", team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo.class);

    public QEntranceTestResult(String variable) {
        this(EntranceTestResult.class, forVariable(variable), INITS);
    }

    public QEntranceTestResult(Path<? extends EntranceTestResult> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEntranceTestResult(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEntranceTestResult(PathMetadata metadata, PathInits inits) {
        this(EntranceTestResult.class, metadata, inits);
    }

    public QEntranceTestResult(Class<? extends EntranceTestResult> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.entranceTestFactorsDetail = inits.isInitialized("entranceTestFactorsDetail") ? new QEntranceTestFactorsDetail(forProperty("entranceTestFactorsDetail")) : null;
        this.oneseo = inits.isInitialized("oneseo") ? new QOneseo(forProperty("oneseo"), inits.get("oneseo")) : null;
    }

}

