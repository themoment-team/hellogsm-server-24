package team.themoment.hellogsmv3.domain.application.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCandidatePersonalInformation is a Querydsl query type for CandidatePersonalInformation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCandidatePersonalInformation extends EntityPathBase<CandidatePersonalInformation> {

    private static final long serialVersionUID = -1509874880L;

    public static final QCandidatePersonalInformation candidatePersonalInformation = new QCandidatePersonalInformation("candidatePersonalInformation");

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

    public QCandidatePersonalInformation(String variable) {
        super(CandidatePersonalInformation.class, forVariable(variable));
    }

    public QCandidatePersonalInformation(Path<CandidatePersonalInformation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCandidatePersonalInformation(PathMetadata metadata) {
        super(CandidatePersonalInformation.class, metadata);
    }

}

