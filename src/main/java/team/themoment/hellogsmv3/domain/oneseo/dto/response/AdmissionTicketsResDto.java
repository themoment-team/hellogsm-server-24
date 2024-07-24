package team.themoment.hellogsmv3.domain.oneseo.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening;

import java.time.LocalDate;

public record AdmissionTicketsResDto(
        String memberName,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate memberBirth,
        String profileImg,
        String schoolName,
        Screening appliedScreening,
        Long oneseoSubmitCode
) {
}
