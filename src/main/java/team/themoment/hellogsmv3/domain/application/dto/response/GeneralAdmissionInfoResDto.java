package team.themoment.hellogsmv3.domain.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import team.themoment.hellogsmv3.domain.applicant.type.Gender;
import team.themoment.hellogsmv3.domain.application.type.DesiredMajors;
import team.themoment.hellogsmv3.domain.application.type.GraduationStatus;
import team.themoment.hellogsmv3.domain.application.type.Screening;

import java.time.LocalDate;

@Builder
public record GeneralAdmissionInfoResDto(
        String applicantName,
        Gender applicantGender,
        @JsonFormat(pattern="yyyy-MM-dd")
        LocalDate applicantBirth,
        String address,
        String detailAddress,
        GraduationStatus graduation,
        String telephone,
        String applicantPhoneNumber,
        String guardianName,
        String relationWithApplicant,
        String guardianPhoneNumber,
        String teacherName,
        String teacherPhoneNumber,
        String schoolName,
        String schoolLocation,
        String applicantImageUri,
        DesiredMajors desiredMajor,
        Screening screening
) implements AdmissionInfoResDto {
}
