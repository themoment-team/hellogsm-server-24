package team.themoment.hellogsmv3.domain.applicant.service;

import team.themoment.hellogsmv3.domain.applicant.dto.response.FindApplicantResDto;

public interface QueryApplicantService {

    FindApplicantResDto execute(Long userId);
}
