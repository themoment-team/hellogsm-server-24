package team.themoment.hellogsmv3.domain.applicant.service;

import team.themoment.hellogsmv3.domain.applicant.dto.request.ApplicantReqDto;
import team.themoment.hellogsmv3.domain.applicant.dto.response.CreateApplicantResDto;

public interface CreateApplicantService {

    CreateApplicantResDto execute(ApplicantReqDto reqDto);
}
