package team.themoment.hellogsmv3.domain.applicant.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import team.themoment.hellogsmv3.domain.applicant.type.Gender;
import team.themoment.hellogsmv3.domain.auth.type.Role;

import java.time.LocalDate;

public record CreateApplicantResDto(

        Long id,
        String name,
        String phoneNumber,
        @JsonFormat(pattern="yyyy-MM-dd")
        LocalDate birth,
        Gender gender,
        Role userRole,
        Long userId
) {
}
