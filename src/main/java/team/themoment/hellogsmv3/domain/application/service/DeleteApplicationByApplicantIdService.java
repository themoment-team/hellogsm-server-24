package team.themoment.hellogsmv3.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsmv3.domain.applicant.entity.Applicant;
import team.themoment.hellogsmv3.domain.applicant.repo.ApplicantRepository;
import team.themoment.hellogsmv3.domain.application.entity.abs.AbstractApplication;
import team.themoment.hellogsmv3.domain.application.repo.ApplicationRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

@Service
@RequiredArgsConstructor
public class DeleteApplicationByApplicantIdService {

    private final ApplicantRepository applicantRepository;
    private final ApplicationRepository applicationRepository;

    @Transactional
    public void execute(Long authenticationId) {
        Applicant applicant = applicantRepository.findByAuthenticationId(authenticationId)
                .orElseThrow(() -> new ExpectedException("applicant를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        AbstractApplication application = applicationRepository.findAbstractApplicationByApplicant(applicant)
                .orElseThrow(() -> new ExpectedException("application을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        if (application.getFinalSubmitted()) {
            throw new ExpectedException("최종제출을 완료한 원서입니다.", HttpStatus.BAD_REQUEST);
        }

        applicationRepository.delete(application);
    }
}
