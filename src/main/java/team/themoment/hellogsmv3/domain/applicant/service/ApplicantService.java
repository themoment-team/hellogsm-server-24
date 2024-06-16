package team.themoment.hellogsmv3.domain.applicant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.applicant.entity.Applicant;
import team.themoment.hellogsmv3.domain.applicant.repo.ApplicantRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

@Service
@RequiredArgsConstructor
public class ApplicantService {

    private final ApplicantRepository applicantRepository;

    public Applicant findOrThrowByAuthId(Long authenticationId) {
        return applicantRepository.findByAuthenticationId(authenticationId)
                .orElseThrow(() -> new ExpectedException("존재하지 않는 지원자입니다. authentication ID: " + authenticationId, HttpStatus.NOT_FOUND));
    }

    public Applicant findOrThrow(Long applicantId) {
        return applicantRepository.findByAuthenticationId(applicantId)
                .orElseThrow(() -> new ExpectedException("존재하지 않는 지원자입니다. applicant ID: " + applicantId, HttpStatus.NOT_FOUND));
    }

}
