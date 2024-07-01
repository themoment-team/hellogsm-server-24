package team.themoment.hellogsmv3.domain.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import team.themoment.hellogsmv3.domain.application.annotation.ValidDesiredMajors;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.GraduationType;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.Major;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening;

@ValidDesiredMajors
public record ApplicationReqDto(
        @NotBlank
        String guardianName,

        @NotBlank
        String applicantImageUri,

        @NotBlank
        String address,

        @NotBlank
        String detailAddress,

        @NotNull
        GraduationType graduation,

        @Pattern(regexp = "^0(?:\\d|\\d{2})(?:\\d{3}|\\d{4})\\d{4}$")
        String telephone,

        @NotBlank
        String relationWithApplicant,

        @NotBlank
        @Pattern(regexp = "^0(?:\\d|\\d{2})(?:\\d{3}|\\d{4})\\d{4}$")
        String guardianPhoneNumber,

        @Pattern(regexp = "^(?!\\s*$).+")
        String teacherName,

        @Pattern(regexp = "^0(?:\\d|\\d{2})(?:\\d{3}|\\d{4})\\d{4}$")
        String teacherPhoneNumber,

        @NotNull
        Major firstDesiredMajor,

        @NotNull
        Major secondDesiredMajor,

        @NotNull
        Major thirdDesiredMajor,

        @NotBlank
        String middleSchoolGrade,

        String schoolName,

        String schoolLocation,

        @NotNull
        Screening screening
) {}
