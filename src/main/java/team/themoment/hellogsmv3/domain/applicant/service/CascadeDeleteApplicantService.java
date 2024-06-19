package team.themoment.hellogsmv3.domain.applicant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsmv3.domain.applicant.entity.Applicant;
import team.themoment.hellogsmv3.domain.applicant.repo.ApplicantRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CascadeDeleteApplicantService {

    private final ApplicantRepository applicantRepository;

    @Transactional
    public Optional<Applicant> execute(Long authenticationId) {
        final Optional<Applicant> optionalApplicant = applicantRepository.findByAuthenticationId(authenticationId);

        if (optionalApplicant.isPresent()) {
            applicantRepository.delete(optionalApplicant.get());
        } else {
            log.warn("존재하지 않는 applicant 삭제 요청 발생 - authenticationId: {}", authenticationId);
        }

        return optionalApplicant;
    }
}
