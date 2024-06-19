package team.themoment.hellogsmv3.domain.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.applicant.entity.Applicant;
import team.themoment.hellogsmv3.domain.applicant.service.ApplicantService;
import team.themoment.hellogsmv3.domain.application.entity.abs.AbstractApplication;
import team.themoment.hellogsmv3.domain.application.repo.ApplicationRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CascadeDeleteApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicantService applicantService;

    public void execute(Long authenticationId) {
        Applicant applicant = applicantService.findOrThrowByAuthId(authenticationId);

        final Optional<AbstractApplication> optionalApplication = applicationRepository.findByApplicant(applicant);

        if (optionalApplication.isPresent()) {
            AbstractApplication application = optionalApplication.get();
            if (application.getFinalSubmitted()) {
                log.warn("최종제출 된 application 삭제 요청 발생 - applicantId: {}", applicant.getId());
            } else {
                applicationRepository.deleteById(application.getId());
            }
        } else {
            log.warn("존재하지 않는 application 삭제 요청 발생 - applicantId: {}", applicant.getId());
        }
    }
}
