package team.themoment.hellogsmv3.domain.application.dto.response;

import lombok.Builder;
import team.themoment.hellogsmv3.domain.applicant.type.Gender;
import team.themoment.hellogsmv3.domain.application.type.DesiredMajors;
import team.themoment.hellogsmv3.domain.application.type.GraduationStatus;
import team.themoment.hellogsmv3.domain.application.type.Screening;

import java.time.LocalDate;

@Builder
public record GedAdmissionInfoResDto(
        String applicantName,
        Gender applicantGender,
        LocalDate applicantBirth,
        String address,
        String detailAddress,
        GraduationStatus graduation,
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