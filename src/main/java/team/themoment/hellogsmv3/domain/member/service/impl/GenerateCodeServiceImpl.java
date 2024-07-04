package team.themoment.hellogsmv3.domain.member.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.member.dto.request.GenerateCodeReqDto;
import team.themoment.hellogsmv3.domain.member.entity.AuthenticationCode;
import team.themoment.hellogsmv3.domain.member.repo.CodeRepository;
import team.themoment.hellogsmv3.domain.member.service.GenerateCodeService;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class GenerateCodeServiceImpl extends GenerateCodeService {

    private final CodeRepository codeRepository;
    private static final Random RANDOM = new Random();

    @Override
    public String execute(Long memberId, GenerateCodeReqDto reqDto) {

        AuthenticationCode authenticationCode = codeRepository.findByMemberId(memberId)
                .orElse(null);

        if (isLimitedRequest(authenticationCode))
            throw new ExpectedException(String.format(
                    "너무 많은 요청이 발생했습니다. 잠시 후 다시 시도해주세요. 특정 시간 내 제한 횟수인 %d회를 초과하였습니다.",
                    LIMIT_COUNT_CODE_REQUEST), HttpStatus.BAD_REQUEST);

        final String code = generateUniqueCode(RANDOM, codeRepository);

        codeRepository.save(createAuthenticationCode(
                authenticationCode,
                memberId,
                code,
                reqDto.phoneNumber(),
                false));

        // TODO 문자 발송 로직

        return code;
    }

    private boolean isLimitedRequest(AuthenticationCode authenticationCode) {
        return authenticationCode != null && authenticationCode.getCount() >= LIMIT_COUNT_CODE_REQUEST;
    }
}
