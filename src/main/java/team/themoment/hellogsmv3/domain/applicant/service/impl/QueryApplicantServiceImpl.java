package team.themoment.hellogsmv3.domain.applicant.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.applicant.dto.response.FindApplicantResDto;
import team.themoment.hellogsmv3.domain.applicant.entity.Applicant;
import team.themoment.hellogsmv3.domain.applicant.repo.ApplicantRepository;
import team.themoment.hellogsmv3.domain.applicant.service.QueryApplicantService;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

@Service
@RequiredArgsConstructor
public class QueryApplicantServiceImpl implements QueryApplicantService {

    private final ApplicantRepository applicantRepository;

    @Override
    public FindApplicantResDto execute(Long authenticationId) {
        Applicant applicant = applicantRepository.findByAuthenticationId(authenticationId)
                .orElseThrow(() -> new ExpectedException("존재하지 않는 Applicant 입니다", HttpStatus.BAD_REQUEST));

        return new FindApplicantResDto(
                applicant.getId(),
                applicant.getName(),
                applicant.getPhoneNumber(),
                applicant.getBirth(),
                applicant.getGender(),
                authenticationId
        );
    }
}
