package team.themoment.hellogsmv3.domain.applicant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.applicant.dto.response.FoundApplicantResDto;
import team.themoment.hellogsmv3.domain.applicant.entity.Applicant;

@Service
@RequiredArgsConstructor
public class QueryApplicantByIdService {

    private final ApplicantService applicantService;

    public FoundApplicantResDto execute(Long authenticationId) {

        Applicant applicant = applicantService.findOrThrowByAuthId(authenticationId);

        return new FoundApplicantResDto(
                applicant.getId(),
                applicant.getName(),
                applicant.getPhoneNumber(),
                applicant.getBirth(),
                applicant.getSex(),
                authenticationId
        );
    }
}
