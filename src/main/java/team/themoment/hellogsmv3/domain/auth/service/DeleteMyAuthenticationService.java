package team.themoment.hellogsmv3.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsmv3.domain.applicant.service.CascadeDeleteApplicantService;
import team.themoment.hellogsmv3.domain.application.service.CascadeDeleteApplicationService;
import team.themoment.hellogsmv3.domain.auth.entity.Authentication;
import team.themoment.hellogsmv3.domain.auth.repo.AuthenticationRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

@Service
@RequiredArgsConstructor
public class DeleteMyAuthenticationService {

    private final AuthenticationRepository authenticationRepository;
    private final CascadeDeleteApplicationService cascadeDeleteApplicationService;
    private final CascadeDeleteApplicantService cascadeDeleteApplicantService;

    @Transactional
    public void execute(Long authenticationId) {
        final Authentication authentication = authenticationRepository.findById(authenticationId)
                .orElseThrow(() -> new ExpectedException(String.format("ID(%s)에 해당하는 회원을 찾을 수 없습니다.", authenticationId), HttpStatus.NOT_FOUND));

        authenticationRepository.delete(authentication);
        cascadeDeleteApplicationService.execute(authenticationId);
        cascadeDeleteApplicantService.execute(authenticationId);
    }
}
