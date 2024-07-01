package team.themoment.hellogsmv3.domain.application.entity.abs;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import team.themoment.hellogsmv3.domain.application.entity.param.AbstractPersonalInformationParameter;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.GraduationType;

import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class AbstractPersonalInformation implements Cloneable{

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
    @Enumerated(EnumType.STRING)
    protected GraduationType graduation;

    @NotNull
    @Column(name = "phone_number", unique = true)
    protected String phoneNumber;

    @NotNull
    @Column(name = "guardian_name")
    protected String guardianName;

    @NotNull
    @Column(name = "relation_with_applicant")
    protected String relationWithApplicant;

    @Column(name = "guardian_phone_number", nullable = false)
    private String guardianPhoneNumber;

    protected AbstractPersonalInformation(UUID id, @NonNull AbstractPersonalInformationParameter parameter) {
        this.id = id;
        this.applicantImageUri = parameter.getApplicantImageUri();
        this.address = parameter.getAddress();
        this.detailAddress = parameter.getDetailAddress();
        this.graduation = parameter.getGraduation();
        this.phoneNumber = parameter.getPhoneNumber();
        this.guardianName = parameter.getGuardianName();
        this.relationWithApplicant = parameter.getRelationWithApplicant();
        this.guardianPhoneNumber = parameter.getGuardianPhoneNumber();
    }

    @Override
    public AbstractPersonalInformation clone() {
        try {
            return (AbstractPersonalInformation) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("객체를 복사하는중에 문제가 발생하였습니다.", e);
        }
    }
}
