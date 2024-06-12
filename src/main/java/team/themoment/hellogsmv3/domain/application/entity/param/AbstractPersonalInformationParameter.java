package team.themoment.hellogsmv3.domain.application.entity.param;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import team.themoment.hellogsmv3.domain.application.type.GraduationStatus;

@Getter
public class AbstractPersonalInformationParameter {

    private final String applicantImageUri;

    private final String address;

    private final String detailAddress;

    private final GraduationStatus graduation;

    private final String phoneNumber;

    private final String guardianName;

    private final String relationWithApplicant;

    private final String guardianPhoneNumber;

    @Builder
    public AbstractPersonalInformationParameter(
            @NonNull String applicantImageUri,
            @NonNull String address,
            @NonNull String detailAddress,
            @NonNull GraduationStatus graduation,
            @NonNull String phoneNumber,
            @NonNull String guardianName,
            @NonNull String relationWithApplicant,
            @NonNull String guardianPhoneNumber) {
        this.applicantImageUri = applicantImageUri;
        this.address = address;
        this.detailAddress = detailAddress;
        this.graduation = graduation;
        this.phoneNumber = phoneNumber;
        this.guardianName = guardianName;
        this.relationWithApplicant = relationWithApplicant;
        this.guardianPhoneNumber = guardianPhoneNumber;
    }
}
