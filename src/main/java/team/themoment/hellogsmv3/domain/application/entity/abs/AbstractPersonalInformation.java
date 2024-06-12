package team.themoment.hellogsmv3.domain.application.entity.abs;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import team.themoment.hellogsmv3.domain.application.entity.param.AbstractPersonalInformationParameter;
import team.themoment.hellogsmv3.domain.application.type.GraduationStatus;

import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@ToString
public abstract class AbstractPersonalInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    @NotNull
    @Column(name = "applicant_image_uri")
    protected String applicantImageUri;

    @NotNull
    @Column(name = "address")
    protected String address;

    @NotNull
    @Column(name = "detail_address")
    protected String detailAddress;

    @NotNull
    @Column(name = "graduation")
    protected GraduationStatus graduation;

    @NotNull
    @Column(name = "phone_number", unique = true)
    protected String phoneNumber;

    @NotNull
    @Column(name = "guardian_name")
    protected String guardianName;

    @NotNull
    @Column(name = "relation_with_applicant")
    protected String relationWithApplicant;

    protected AbstractPersonalInformation(UUID id, @NonNull AbstractPersonalInformationParameter parameter) {
        this.id = id;
        this.applicantImageUri = parameter.getApplicantImageUri();
        this.address = parameter.getAddress();
        this.detailAddress = parameter.getDetailAddress();
        this.graduation = parameter.getGraduation();
        this.phoneNumber = parameter.getPhoneNumber();
        this.guardianName = parameter.getGuardianName();
        this.relationWithApplicant = parameter.getRelationWithApplicant();
    }
}
