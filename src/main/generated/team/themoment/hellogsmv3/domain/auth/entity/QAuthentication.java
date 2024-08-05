package team.themoment.hellogsmv3.domain.auth.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuthentication is a Querydsl query type for Authentication
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAuthentication extends EntityPathBase<Authentication> {

    private static final long serialVersionUID = 711433139L;

    public static final QAuthentication authentication = new QAuthentication("authentication");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath providerId = createString("providerId");

    public final StringPath providerName = createString("providerName");

    public final EnumPath<team.themoment.hellogsmv3.domain.member.entity.type.Role> role = createEnum("role", team.themoment.hellogsmv3.domain.member.entity.type.Role.class);

    public QAuthentication(String variable) {
        super(Authentication.class, forVariable(variable));
    }

    public QAuthentication(Path<? extends Authentication> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuthentication(PathMetadata metadata) {
        super(Authentication.class, metadata);
    }

}

