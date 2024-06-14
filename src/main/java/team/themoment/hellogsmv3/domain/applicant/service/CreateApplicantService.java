package team.themoment.hellogsmv3.domain.applicant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsmv3.domain.applicant.dto.request.ApplicantReqDto;
import team.themoment.hellogsmv3.domain.applicant.entity.Applicant;
import team.themoment.hellogsmv3.domain.applicant.entity.AuthenticationCode;
import team.themoment.hellogsmv3.domain.applicant.repo.ApplicantRepository;
import team.themoment.hellogsmv3.domain.auth.entity.Authentication;
import team.themoment.hellogsmv3.domain.auth.repo.AuthenticationRepository;
import team.themoment.hellogsmv3.domain.auth.type.Role;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateApplicantService {

    private final ApplicantRepository applicantRepository;
    private final AuthenticationRepository authenticationRepository;
    private final CommonCodeService commonCodeService;

    public Role execute(ApplicantReqDto reqDto, Long authenticationId) {

        Authentication authentication = authenticationRepository.findById(authenticationId)
                .orElseThrow(() -> new ExpectedException("존재하지 않는 Authentication 입니다", HttpStatus.BAD_REQUEST));

        if (applicantRepository.existsByAuthenticationId(authenticationId))
            throw new ExpectedException("이미 존재하는 Applicant 입니다", HttpStatus.BAD_REQUEST);

        AuthenticationCode code = commonCodeService.validateAndGetRecentCode(authenticationId, reqDto.code(), reqDto.phoneNumber());

        Authentication roleUpdatedAuthentication = authenticationRepository.save(
                new Authentication(
                        authentication.getId(),
                        authentication.getProviderName(),
                        authentication.getProviderId(),
                        Role.APPLICANT
                ));

        Applicant newApplicant = new Applicant(
                null,
                reqDto.name(),
                reqDto.phoneNumber(),
                reqDto.birth(),
                reqDto.gender(),
                authenticationId
        );
        applicantRepository.save(newApplicant);

        commonCodeService.deleteCode(x);

        return roleUpdatedAuthentication.getRole();
    };
}
