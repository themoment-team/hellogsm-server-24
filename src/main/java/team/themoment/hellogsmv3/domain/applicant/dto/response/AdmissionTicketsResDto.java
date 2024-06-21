package team.themoment.hellogsmv3.domain.applicant.dto.response;

import lombok.Builder;
import team.themoment.hellogsmv3.domain.application.type.Screening;

@Builder
public record AdmissionTicketsResDto(
        String applicantName,
        String applicantBirth,
        String applicantImageUri,
        String schoolName,
        Screening screening,
        Long registrationNumber
) {
}
