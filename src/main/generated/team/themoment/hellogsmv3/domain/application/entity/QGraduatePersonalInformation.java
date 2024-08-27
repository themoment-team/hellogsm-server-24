package team.themoment.hellogsmv3.domain.application.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGraduatePersonalInformation is a Querydsl query type for GraduatePersonalInformation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGraduatePersonalInformation extends EntityPathBase<GraduatePersonalInformation> {

    private static final long serialVersionUID = -64053846L;

    public static final QGraduatePersonalInformation graduatePersonalInformation = new QGraduatePersonalInformation("graduatePersonalInformation");

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

    public final StringPath schoolLocation = createString("schoolLocation");

    public final StringPath schoolName = createString("schoolName");

    public final StringPath teacherName = createString("teacherName");

    public final StringPath teacherPhoneNumber = createString("teacherPhoneNumber");

    public QGraduatePersonalInformation(String variable) {
        super(GraduatePersonalInformation.class, forVariable(variable));
    }

    public QGraduatePersonalInformation(Path<GraduatePersonalInformation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGraduatePersonalInformation(PathMetadata metadata) {
        super(GraduatePersonalInformation.class, metadata);
    }

}

