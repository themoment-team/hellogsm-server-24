package team.themoment.hellogsmv3.domain.member.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.member.dto.request.GenerateCodeReqDto;
import team.themoment.hellogsmv3.domain.member.entity.AuthenticationCode;
import team.themoment.hellogsmv3.domain.member.repo.CodeRepository;
import team.themoment.hellogsmv3.domain.member.service.GenerateCodeService;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class GenerateTestCodeServiceImpl extends GenerateCodeService {

    private final CodeRepository codeRepository;
    private static final Random RANDOM = new Random();

    @Override
    public String execute(Long memberId, GenerateCodeReqDto reqDto) {
        final String code = generateUniqueCode(RANDOM, codeRepository);

        AuthenticationCode authenticationCode = codeRepository.findByMemberId(memberId)
                .orElse(null);

        codeRepository.save(createAuthenticationCode(
                authenticationCode,
                memberId,
                code,
                reqDto.phoneNumber(),
                true));

        return code;
    }
}
