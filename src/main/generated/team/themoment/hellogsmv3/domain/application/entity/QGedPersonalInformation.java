package team.themoment.hellogsmv3.domain.application.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGedPersonalInformation is a Querydsl query type for GedPersonalInformation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGedPersonalInformation extends EntityPathBase<GedPersonalInformation> {

    private static final long serialVersionUID = -1218242083L;

    public static final QGedPersonalInformation gedPersonalInformation = new QGedPersonalInformation("gedPersonalInformation");

    public final team.themoment.hellogsmv3.domain.application.entity.abs.QAbstractPersonalInformation _super = new team.themoment.hellogsmv3.domain.application.entity.abs.QAbstractPersonalInformation(this);

    //inherited
    public final StringPath address = _super.address;

    //inherited
    public final StringPath applicantImageUri = _super.applicantImageUri;

    //inherited
    public final StringPath detailAddress = _super.detailAddress;

    //inherited
    public final EnumPath<team.themoment.hellogsmv3.domain.oneseo.entity.type.GraduationType> graduation = _super.graduation;

    //inherited
    public final StringPath guardianName = _super.guardianName;

    //inherited
    public final StringPath guardianPhoneNumber = _super.guardianPhoneNumber;

    //inherited
    public final ComparablePath<java.util.UUID> id = _super.id;

    //inherited
    public final StringPath phoneNumber = _super.phoneNumber;

    //inherited
    public final StringPath relationWithApplicant = _super.relationWithApplicant;

    public QGedPersonalInformation(String variable) {
        super(GedPersonalInformation.class, forVariable(variable));
    }

    public QGedPersonalInformation(Path<GedPersonalInformation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGedPersonalInformation(PathMetadata metadata) {
        super(GedPersonalInformation.class, metadata);
    }

}

