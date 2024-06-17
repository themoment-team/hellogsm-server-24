package team.themoment.hellogsmv3.domain.applicant.event;

import team.themoment.hellogsmv3.domain.applicant.entity.Applicant;

public record DeleteApplicantEvent(
        Applicant applicant
) {
}
