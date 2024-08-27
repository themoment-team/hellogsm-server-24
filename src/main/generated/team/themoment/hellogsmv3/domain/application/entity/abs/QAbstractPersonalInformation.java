package team.themoment.hellogsmv3.domain.application.entity.abs;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAbstractPersonalInformation is a Querydsl query type for AbstractPersonalInformation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAbstractPersonalInformation extends EntityPathBase<AbstractPersonalInformation> {

    private static final long serialVersionUID = -1174530697L;

    public static final QAbstractPersonalInformation abstractPersonalInformation = new QAbstractPersonalInformation("abstractPersonalInformation");

    public final StringPath address = createString("address");

    public final StringPath applicantImageUri = createString("applicantImageUri");

    public final StringPath detailAddress = createString("detailAddress");

    public final EnumPath<team.themoment.hellogsmv3.domain.oneseo.entity.type.GraduationType> graduation = createEnum("graduation", team.themoment.hellogsmv3.domain.oneseo.entity.type.GraduationType.class);

    public final StringPath guardianName = createString("guardianName");

    public final StringPath guardianPhoneNumber = createString("guardianPhoneNumber");

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final StringPath phoneNumber = createString("phoneNumber");

    public final StringPath relationWithApplicant = createString("relationWithApplicant");

    public QAbstractPersonalInformation(String variable) {
        super(AbstractPersonalInformation.class, forVariable(variable));
    }

    public QAbstractPersonalInformation(Path<? extends AbstractPersonalInformation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAbstractPersonalInformation(PathMetadata metadata) {
        super(AbstractPersonalInformation.class, metadata);
    }

}

