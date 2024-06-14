package team.themoment.hellogsmv3.domain.applicant.service;

import team.themoment.hellogsmv3.domain.applicant.dto.request.GenerateCodeReqDto;
import team.themoment.hellogsmv3.domain.applicant.entity.AuthenticationCode;
import team.themoment.hellogsmv3.domain.applicant.repo.CodeRepository;

import java.time.LocalDateTime;
import java.util.Random;

public abstract class GenerateCodeService {
    protected final static int DIGIT_NUMBER = 6;
    protected final static int LIMIT_COUNT_CODE_REQUEST = 5;
    protected final static int MAX = (int) Math.pow(10, DIGIT_NUMBER) - 1;

    protected abstract String execute(Long userId, GenerateCodeReqDto reqDto);

    protected AuthenticationCode createAuthenticationCode(
            AuthenticationCode authCode,
            Long authenticationId,
            String code,
            String phoneNumber,
            boolean isTest) {

        return authCode == null ?
                new AuthenticationCode(authenticationId, code, phoneNumber, LocalDateTime.now()) :
                authCode.updatedCode(code, LocalDateTime.now(), isTest);
    }

    protected String generateUniqueCode(Random RANDOM, CodeRepository codeRepository) {
        String code;
        do {
            code = getRandomCode(RANDOM);
        } while (isDuplicate(code, codeRepository));
        return code;
    }

    protected Boolean isDuplicate(String code, CodeRepository codeRepository) {
        return codeRepository.findByCode(code).isPresent();
    }

    protected String getRandomCode(Random RANDOM) {
        return String.format("%0" + DIGIT_NUMBER + "d", RANDOM.nextInt(0, MAX + 1));
    }
}
