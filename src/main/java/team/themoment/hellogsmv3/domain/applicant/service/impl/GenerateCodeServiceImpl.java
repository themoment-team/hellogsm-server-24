package team.themoment.hellogsmv3.domain.applicant.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.applicant.dto.request.GenerateCodeReqDto;
import team.themoment.hellogsmv3.domain.applicant.repo.CodeRepository;
import team.themoment.hellogsmv3.domain.applicant.service.GenerateCodeService;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class GenerateCodeServiceImpl implements GenerateCodeService {

    private final static Random RANDOM = new Random();
    public final static int DIGIT_NUMBER = 6;
    public final static int LIMIT_COUNT_CODE_REQUEST = 5;
    public final static int MAX = (int) Math.pow(10, DIGIT_NUMBER) - 1;

    private final CodeRepository codeRepository;

    @Override
    public String execute(Long userId, GenerateCodeReqDto reqDto) {

        if (isLimitedRequest(userId))
            throw new ExpectedException(String.format(
                    "너무 많은 요청이 발생했습니다. 잠시 후 다시 시도해주세요. 특정 시간 내 제한 횟수인 %d회를 초과하였습니다.",
                    LIMIT_COUNT_CODE_REQUEST), HttpStatus.FORBIDDEN);

        final String code = generateUniqueCode();

        // 문자 발송 로직

        return code;
    }

    private Boolean isLimitedRequest(Long userId) {
        long count = codeRepository.findByAuthenticationId(userId).stream().count();
        return count >= LIMIT_COUNT_CODE_REQUEST;
    }

    private String generateUniqueCode() {
        String code;
        do {
            code = getRandomCode();
        } while (isDuplicate(code));
        return code;
    }

    private Boolean isDuplicate(String code) {
        return codeRepository.findById(code).isPresent();
    }

    public static String getRandomCode() {
        return String.format("%0" + DIGIT_NUMBER + "d", RANDOM.nextInt(0, MAX + 1));
    }
}
