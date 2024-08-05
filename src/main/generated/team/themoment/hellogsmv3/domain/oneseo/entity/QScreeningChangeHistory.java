package team.themoment.hellogsmv3.domain.oneseo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QScreeningChangeHistory is a Querydsl query type for ScreeningChangeHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QScreeningChangeHistory extends EntityPathBase<ScreeningChangeHistory> {

    private static final long serialVersionUID = -1204865672L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QScreeningChangeHistory screeningChangeHistory = new QScreeningChangeHistory("screeningChangeHistory");

    public final EnumPath<team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening> afterScreening = createEnum("afterScreening", team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening.class);

    public final EnumPath<team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening> beforeScreening = createEnum("beforeScreening", team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening.class);

    public final DateTimePath<java.time.LocalDateTime> createdTime = createDateTime("createdTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QOneseo oneseo;

    public QScreeningChangeHistory(String variable) {
        this(ScreeningChangeHistory.class, forVariable(variable), INITS);
    }

    public QScreeningChangeHistory(Path<? extends ScreeningChangeHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QScreeningChangeHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QScreeningChangeHistory(PathMetadata metadata, PathInits inits) {
        this(ScreeningChangeHistory.class, metadata, inits);
    }

    public QScreeningChangeHistory(Class<? extends ScreeningChangeHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.oneseo = inits.isInitialized("oneseo") ? new QOneseo(forProperty("oneseo"), inits.get("oneseo")) : null;
    }

}

