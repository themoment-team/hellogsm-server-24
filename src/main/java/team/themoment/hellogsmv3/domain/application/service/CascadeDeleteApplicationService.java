package team.themoment.hellogsmv3.domain.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.applicant.entity.Applicant;
import team.themoment.hellogsmv3.domain.applicant.event.DeleteApplicantEvent;
import team.themoment.hellogsmv3.domain.application.entity.abs.AbstractApplication;
import team.themoment.hellogsmv3.domain.application.repo.ApplicationRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CascadeDeleteApplicationService {

    private final ApplicationRepository applicationRepository;

    public void execute(DeleteApplicantEvent deleteApplicantEvent) {
        Applicant applicant = deleteApplicantEvent.applicant();

        Optional<AbstractApplication> optionalAbstractApplication = applicationRepository.findAbstractApplicationByApplicant(applicant);

        if (optionalAbstractApplication.isPresent()) {
            applicationRepository.delete(optionalAbstractApplication.get());
        } else {
            log.warn("존재하지 않는 application 삭제 요청 발생 - applicationId: {}", applicant.getId());
        }

    }
}
