package team.themoment.hellogsmv3.domain.applicant.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.applicant.dto.request.ApplicantReqDto;
import team.themoment.hellogsmv3.domain.applicant.dto.response.CreateApplicantResDto;
import team.themoment.hellogsmv3.domain.applicant.service.CreateApplicantService;

@Service
@RequiredArgsConstructor
public class CreateApplicantServiceImpl implements CreateApplicantService {

    @Override
    public CreateApplicantResDto execute(ApplicantReqDto reqDto) {

        return null;
    };
}
