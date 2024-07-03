package team.themoment.hellogsmv3.domain.applicant.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import team.themoment.hellogsmv3.domain.member.entity.type.Sex;

import java.time.LocalDate;

public record FoundApplicantResDto(

        Long id,
        String name,
        String phoneNumber,
        @JsonFormat(pattern="yyyy-MM-dd")
        LocalDate birth,
        Sex sex,
        Long authenticationId
) {
}
