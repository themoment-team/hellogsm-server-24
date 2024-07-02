package team.themoment.hellogsmv3.domain.applicant.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.applicant.dto.request.GenerateCodeReqDto;
import team.themoment.hellogsmv3.domain.applicant.entity.ApplicantAuthenticationCode;
import team.themoment.hellogsmv3.domain.applicant.repo.ApplicantCodeRepository;
import team.themoment.hellogsmv3.domain.applicant.service.GenerateApplicantCodeService;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GenerateApplicantTestCodeServiceImpl extends GenerateApplicantCodeService {

    private final ApplicantCodeRepository codeRepository;
    private static final Random RANDOM = new Random();

    @Override
    public String execute(Long authenticationId, GenerateCodeReqDto reqDto) {
        final String code = generateUniqueCode(RANDOM, codeRepository);

        ApplicantAuthenticationCode authenticationCode = codeRepository.findByAuthenticationId(authenticationId)
                .orElse(null);

        codeRepository.save(createAuthenticationCode(
                authenticationCode,
                authenticationId,
                code,
                reqDto.phoneNumber(),
                true));

        codeRepository.save(new ApplicantAuthenticationCode(authenticationId, code, reqDto.phoneNumber(), LocalDateTime.now()));

        return code;
    }
}
