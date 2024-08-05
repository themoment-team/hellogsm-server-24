package team.themoment.hellogsmv3.domain.applicant.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QApplicant is a Querydsl query type for Applicant
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QApplicant extends EntityPathBase<Applicant> {

    private static final long serialVersionUID = 1725875065L;

    public static final QApplicant applicant = new QApplicant("applicant");

    public final NumberPath<Long> authenticationId = createNumber("authenticationId", Long.class);

    public final DatePath<java.time.LocalDate> birth = createDate("birth", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final EnumPath<team.themoment.hellogsmv3.domain.member.entity.type.Sex> sex = createEnum("sex", team.themoment.hellogsmv3.domain.member.entity.type.Sex.class);

    public QApplicant(String variable) {
        super(Applicant.class, forVariable(variable));
    }

    public QApplicant(Path<? extends Applicant> path) {
        super(path.getType(), path.getMetadata());
    }

    public QApplicant(PathMetadata metadata) {
        super(Applicant.class, metadata);
    }

}

