package team.themoment.hellogsmv3.domain.oneseo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWantedScreeningChangeHistory is a Querydsl query type for WantedScreeningChangeHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWantedScreeningChangeHistory extends EntityPathBase<WantedScreeningChangeHistory> {

    private static final long serialVersionUID = -963145849L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWantedScreeningChangeHistory wantedScreeningChangeHistory = new QWantedScreeningChangeHistory("wantedScreeningChangeHistory");

    public final EnumPath<team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening> afterScreening = createEnum("afterScreening", team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening.class);

    public final EnumPath<team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening> beforeScreening = createEnum("beforeScreening", team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening.class);

    public final DateTimePath<java.time.LocalDateTime> createdTime = createDateTime("createdTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QOneseo oneseo;

    public QWantedScreeningChangeHistory(String variable) {
        this(WantedScreeningChangeHistory.class, forVariable(variable), INITS);
    }

    public QWantedScreeningChangeHistory(Path<? extends WantedScreeningChangeHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWantedScreeningChangeHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWantedScreeningChangeHistory(PathMetadata metadata, PathInits inits) {
        this(WantedScreeningChangeHistory.class, metadata, inits);
    }

    public QWantedScreeningChangeHistory(Class<? extends WantedScreeningChangeHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.oneseo = inits.isInitialized("oneseo") ? new QOneseo(forProperty("oneseo"), inits.get("oneseo")) : null;
    }

}

