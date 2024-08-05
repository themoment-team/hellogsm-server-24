package team.themoment.hellogsmv3.domain.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 57296583L;

    public static final QMember member = new QMember("member1");

    public final EnumPath<team.themoment.hellogsmv3.domain.member.entity.type.AuthReferrerType> authReferrerType = createEnum("authReferrerType", team.themoment.hellogsmv3.domain.member.entity.type.AuthReferrerType.class);

    public final DatePath<java.time.LocalDate> birth = createDate("birth", java.time.LocalDate.class);

    public final DateTimePath<java.time.LocalDateTime> createdTime = createDateTime("createdTime", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final EnumPath<team.themoment.hellogsmv3.domain.member.entity.type.Role> role = createEnum("role", team.themoment.hellogsmv3.domain.member.entity.type.Role.class);

    public final EnumPath<team.themoment.hellogsmv3.domain.member.entity.type.Sex> sex = createEnum("sex", team.themoment.hellogsmv3.domain.member.entity.type.Sex.class);

    public final DateTimePath<java.time.LocalDateTime> updatedTime = createDateTime("updatedTime", java.time.LocalDateTime.class);

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

