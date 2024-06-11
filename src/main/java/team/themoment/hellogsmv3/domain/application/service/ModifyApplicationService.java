package team.themoment.hellogsmv3.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.applicant.entity.Applicant;
import team.themoment.hellogsmv3.domain.applicant.repo.ApplicantRepository;
import team.themoment.hellogsmv3.domain.application.dto.request.ApplicationReqDto;
import team.themoment.hellogsmv3.domain.application.entity.abs.AbstractApplication;
import team.themoment.hellogsmv3.domain.application.repo.ApplicationRepository;
import team.themoment.hellogsmv3.domain.auth.repo.AuthenticationRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

@Service
@RequiredArgsConstructor
public class ModifyApplicationService {

    private final ApplicantRepository applicantRepository;
    private final AuthenticationRepository authenticationRepository;
    private final ApplicationRepository applicationRepository;

    public void execute(ApplicationReqDto dto, Long userId) {

        Applicant currentApplicant = applicantRepository.findById(userId)
                .orElseThrow(() -> new ExpectedException("존재하지 않는 유저입니다.", HttpStatus.NOT_FOUND));

        AbstractApplication application = applicationRepository.findByApplicant(currentApplicant)
                .orElseThrow(() -> new ExpectedException("원서를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        if (application.getFinalSubmitted())
            throw new ExpectedException("최종제출이 완료된 원서는 수정할 수 없습니다.", HttpStatus.BAD_REQUEST);

        if (!authenticationRepository.existsById(currentApplicant.getAuthenticationId()))
            throw new ExpectedException("인증 정보가 존재하지 않습니다.", HttpStatus.NOT_FOUND);

    }

}
