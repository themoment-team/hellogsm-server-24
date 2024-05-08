package team.themoment.hellogsmv3.domain.applicant.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.applicant.dto.request.GenerateCodeReqDto;
import team.themoment.hellogsmv3.domain.applicant.entity.AuthenticationCode;
import team.themoment.hellogsmv3.domain.applicant.repo.CodeRepository;
import team.themoment.hellogsmv3.domain.applicant.service.GenerateTestCodeService;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GenerateTestCodeServiceImpl implements GenerateTestCodeService {

    private final static Random RANDOM = new Random();
    public final static int DIGIT_NUMBER = GenerateCodeServiceImpl.DIGIT_NUMBER;
    public final static int MAX = GenerateCodeServiceImpl.MAX;

    private final CodeRepository codeRepository;
    @Override
    public String execute(Long userId, GenerateCodeReqDto reqDto) {
        final String code = generateUniqueCode();
        codeRepository.save(new AuthenticationCode(code, userId, false, reqDto.phoneNumber(), LocalDateTime.now()));
        return code;
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
