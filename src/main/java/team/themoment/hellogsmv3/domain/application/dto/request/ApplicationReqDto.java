package team.themoment.hellogsmv3.domain.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import team.themoment.hellogsmv3.domain.application.annotation.NotSpace;

public record ApplicationReqDto(
        @NotBlank
        String guardianName,

        @NotBlank
        String applicantImageUri,

        @NotBlank
        String address,

        @NotBlank
        String detailAddress,

        @NotBlank
        @Pattern(regexp = "^(CANDIDATE|GRADUATE|GED)$")
        String graduation,

        @NotSpace
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

        @Pattern(regexp = "^(AI|SW|IOT)$")
        @NotBlank
        String firstDesiredMajor,

        @Pattern(regexp = "^(AI|SW|IOT)$")
        @NotBlank
        String secondDesiredMajor,

        @Pattern(regexp = "^(AI|SW|IOT)$")
        @NotBlank
        String thirdDesiredMajor,

        @NotBlank
        String middleSchoolGrade,

        @NotSpace
        String schoolName,

        @NotSpace
        String schoolLocation,

        @Pattern(regexp = "^(GENERAL|SOCIAL|SPECIAL_VETERANS|SPECIAL_ADMISSION)$")
        @NotBlank
        String screening
) {}
