package team.themoment.hellogsmv3.domain.application.dto.response;

import lombok.Builder;
import team.themoment.hellogsmv3.domain.member.entity.type.Gender;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.DesiredMajors;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.GraduationType;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening;

import java.time.LocalDate;

@Builder
public record GedAdmissionInfoResDto(
        String applicantName,
        Gender applicantGender,
        LocalDate applicantBirth,
        String address,
        String detailAddress,
        GraduationType graduation,
        String telephone,
        String applicantPhoneNumber,
        String guardianName,
        String relationWithApplicant,
        String guardianPhoneNumber,
        String applicantImageUri,
        DesiredMajors desiredMajor,
        Screening screening
) implements AdmissionInfoResDto {
}
