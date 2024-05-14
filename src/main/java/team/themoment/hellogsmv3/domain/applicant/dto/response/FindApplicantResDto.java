package team.themoment.hellogsmv3.domain.applicant.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import team.themoment.hellogsmv3.domain.applicant.type.Gender;

import java.time.LocalDate;

public record FindApplicantResDto(

        Long id,
        String name,
        String phoneNumber,
        @JsonFormat(pattern="yyyy-MM-dd")
        LocalDate birth,
        Gender gender,
        Long authenticationId
) {
}
