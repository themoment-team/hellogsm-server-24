package team.themoment.hellogsmv3.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsmv3.domain.applicant.entity.Applicant;
import team.themoment.hellogsmv3.domain.applicant.service.ApplicantService;
import team.themoment.hellogsmv3.domain.application.entity.abs.AbstractApplication;
import team.themoment.hellogsmv3.domain.application.repo.ApplicationRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

@Service
@RequiredArgsConstructor
public class UpdateApplicationFinalSubmissionService {

    private final ApplicantService applicantService;
    private final ApplicationRepository applicationRepository;

    @Transactional
    public void execute(Long authenticationId) {

        Applicant applicant = applicantService.findOrThrowByAuthId(authenticationId);

        AbstractApplication application = applicationRepository.findByApplicant(applicant)
                .orElseThrow(() -> new ExpectedException("원서를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        if (application.getFinalSubmitted())
            throw new ExpectedException("이미 최종제출 되었습니다.", HttpStatus.BAD_REQUEST);

        application.updateFinalSubmission();
        applicationRepository.save(application);
    }

}
