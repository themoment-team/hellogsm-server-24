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
public class UpdateFinalSubmissionService {

    private final ApplicantRepository applicantRepository;
    private final ApplicationRepository applicationRepository;

    @Transactional
    public void execute(Long userId) {

        Applicant applicant = applicantRepository.findById(userId)
                .orElseThrow(() -> new ExpectedException("존재하지 않는 유저입니다.", HttpStatus.NOT_FOUND));

        AbstractApplication application = applicationRepository.findByApplicant(applicant)
                .orElseThrow(() -> new ExpectedException("원서를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        application.updateFinalSubmission();
        applicationRepository.save(application);
    }

}
