package team.themoment.hellogsmv3.domain.applicant.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.applicant.dto.request.GenerateCodeReqDto;
import team.themoment.hellogsmv3.domain.applicant.entity.AuthenticationCode;
import team.themoment.hellogsmv3.domain.applicant.repo.CodeRepository;
import team.themoment.hellogsmv3.domain.applicant.service.GenerateCodeService;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GenerateCodeServiceImpl extends GenerateCodeService {

    private final CodeRepository codeRepository;
    private final static Random RANDOM = new Random();

    @Override
    public String execute(Long userId, GenerateCodeReqDto reqDto) {

        if (isLimitedRequest(userId))
            throw new ExpectedException(String.format(
                    "너무 많은 요청이 발생했습니다. 잠시 후 다시 시도해주세요. 특정 시간 내 제한 횟수인 %d회를 초과하였습니다.",
                    LIMIT_COUNT_CODE_REQUEST), HttpStatus.FORBIDDEN);

        final String code = generateUniqueCode(RANDOM, codeRepository);
        codeRepository.save(new AuthenticationCode(code, userId, false, reqDto.phoneNumber(), LocalDateTime.now()));

        // 문자 발송 로직

        return code;
    }

    private Boolean isLimitedRequest(Long userId) {
        long count = codeRepository.findByAuthenticationId(userId).stream().count();
        return count >= LIMIT_COUNT_CODE_REQUEST;
    }
}
