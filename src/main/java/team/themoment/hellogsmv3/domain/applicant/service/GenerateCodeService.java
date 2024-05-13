package team.themoment.hellogsmv3.domain.applicant.service;

import team.themoment.hellogsmv3.domain.applicant.dto.request.GenerateCodeReqDto;
import team.themoment.hellogsmv3.domain.applicant.repo.CodeRepository;

import java.util.Random;

public abstract class GenerateCodeService {
    public final static int DIGIT_NUMBER = 6;
    public final static int LIMIT_COUNT_CODE_REQUEST = 5;
    public final static int MAX = (int) Math.pow(10, DIGIT_NUMBER) - 1;

    public abstract String execute(Long userId, GenerateCodeReqDto reqDto);

    protected String generateUniqueCode(Random RANDOM, CodeRepository codeRepository) {
        String code;
        do {
            code = getRandomCode(RANDOM);
        } while (isDuplicate(code, codeRepository));
        return code;
    }

    protected Boolean isDuplicate(String code, CodeRepository codeRepository) {
        return codeRepository.findById(code).isPresent();
    }

    protected static String getRandomCode(Random RANDOM) {
        return String.format("%0" + DIGIT_NUMBER + "d", RANDOM.nextInt(0, MAX + 1));
    }
}
