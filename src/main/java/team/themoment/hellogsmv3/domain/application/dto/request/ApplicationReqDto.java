package team.themoment.hellogsmv3.domain.application.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import team.themoment.hellogsmv3.domain.application.annotation.ValidDesiredMajors;
import team.themoment.hellogsmv3.domain.application.type.GraduationStatus;
import team.themoment.hellogsmv3.domain.application.type.Major;

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
        @Enumerated(EnumType.STRING)
        GraduationStatus graduation,

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
        @Enumerated(EnumType.STRING)
        Major firstDesiredMajor,

        @NotNull
        @Enumerated(EnumType.STRING)
        Major secondDesiredMajor,

        @NotNull
        @Enumerated(EnumType.STRING)
        Major thirdDesiredMajor,

        @NotBlank
        String middleSchoolGrade,

        String schoolName,

        String schoolLocation,

        @Pattern(regexp = "^(GENERAL|SOCIAL|SPECIAL_VETERANS|SPECIAL_ADMISSION)$")
        @NotBlank
        String screening
) {}
