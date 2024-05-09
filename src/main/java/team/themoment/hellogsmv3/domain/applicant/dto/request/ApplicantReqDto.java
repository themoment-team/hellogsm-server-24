package team.themoment.hellogsmv3.domain.applicant.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import team.themoment.hellogsmv3.domain.applicant.type.Gender;

import java.time.LocalDate;

public record ApplicantReqDto(

        @NotBlank String code,
        @NotBlank String name,
        @Pattern(regexp = "^0(?:\\d|\\d{2})(?:\\d{3}|\\d{4})\\d{4}$")
        @NotBlank String phoneNumber,
        @NotBlank Gender gender,
        @NotBlank LocalDate birth
) {
}
