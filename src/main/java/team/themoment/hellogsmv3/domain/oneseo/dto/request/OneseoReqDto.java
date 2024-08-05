package team.themoment.hellogsmv3.domain.oneseo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import team.themoment.hellogsmv3.domain.oneseo.annotation.ValidDesiredMajors;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.GraduationType;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.Major;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening;

@ValidDesiredMajors
public record OneseoReqDto(
        @Schema(description = "보호자 이름", defaultValue = "김보호")
        @NotBlank
        String guardianName,

        @NotBlank
        @Pattern(regexp = "^0(?:\\d|\\d{2})(?:\\d{3}|\\d{4})\\d{4}$", message = "유요한 전화번호가 아닙니다.")
        String guardianPhoneNumber,

        @NotBlank
        String relationshipWithGuardian,

        @NotBlank
        @Pattern(regexp = "^https:\\/\\/[^\\s/$.?#].[^\\s]*$", message = "유효한 이미지 URL이 아닙니다.")
        String profileImg,

        @NotBlank
        String address,

        @NotBlank
        String detailAddress,

        @NotNull
        GraduationType graduationType,

        @NotBlank
        String schoolTeacherName,

        @Pattern(regexp = "^0(?:\\d|\\d{2})(?:\\d{3}|\\d{4})\\d{4}$", message = "유요한 전화번호가 아닙니다.")
        String schoolTeacherPhoneNumber,

        @NotNull
        Major firstDesiredMajor,

        @NotNull
        Major secondDesiredMajor,

        @NotNull
        Major thirdDesiredMajor,

        MiddleSchoolAchievementReqDto middleSchoolAchievement,

        String schoolName,

        String schoolAddress,

        @NotNull
        Screening screening
) {
}
