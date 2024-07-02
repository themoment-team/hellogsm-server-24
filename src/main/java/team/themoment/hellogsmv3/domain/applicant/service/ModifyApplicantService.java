package team.themoment.hellogsmv3.domain.applicant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsmv3.domain.applicant.dto.request.ApplicantReqDto;
import team.themoment.hellogsmv3.domain.applicant.entity.Applicant;
import team.themoment.hellogsmv3.domain.applicant.entity.ApplicantAuthenticationCode;
import team.themoment.hellogsmv3.domain.applicant.repo.ApplicantRepository;
import team.themoment.hellogsmv3.domain.auth.repo.AuthenticationRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

@Service
@Transactional
@RequiredArgsConstructor
public class ModifyApplicantService {

    private final ApplicantRepository applicantRepository;
    private final AuthenticationRepository authenticationRepository;
    private final CommonCodeService commonCodeService;
    private final ApplicantService applicantService;

    public void execute(ApplicantReqDto reqDto, Long authenticationId) {
        if(!authenticationRepository.existsById(authenticationId))
            throw new ExpectedException("존재하지 않는 Authentication 입니다", HttpStatus.BAD_REQUEST);

        Applicant savedApplicant = applicantService.findOrThrowByAuthId(authenticationId);

        ApplicantAuthenticationCode code = commonCodeService.validateAndGetRecentCode(authenticationId, reqDto.code(), reqDto.phoneNumber());

        Applicant newApplicant = new Applicant(
                savedApplicant.getId(),
                reqDto.name(),
                reqDto.phoneNumber(),
                reqDto.birth(),
                reqDto.gender(),
                authenticationId
        );
        applicantRepository.save(newApplicant);

        commonCodeService.deleteCode(code);
    }
}
