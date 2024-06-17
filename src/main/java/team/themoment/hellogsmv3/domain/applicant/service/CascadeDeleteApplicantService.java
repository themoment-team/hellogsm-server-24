package team.themoment.hellogsmv3.domain.applicant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsmv3.domain.applicant.entity.Applicant;
import team.themoment.hellogsmv3.domain.applicant.event.DeleteApplicantEvent;
import team.themoment.hellogsmv3.domain.applicant.repo.ApplicantRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CascadeDeleteApplicantService {

    private final ApplicantRepository applicantRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void execute(Long authenticationId) {
        final Optional<Applicant> optionalApplicant = applicantRepository.findByAuthenticationId(authenticationId);

        if (optionalApplicant.isPresent()) {
            applicantRepository.delete(optionalApplicant.get());
            applicationEventPublisher.publishEvent(new DeleteApplicantEvent(optionalApplicant.get()));
        } else {
            log.warn("존재하지 않는 applicant 삭제 요청 발생 - authenticationId: {}", authenticationId);
        }
    }
}
