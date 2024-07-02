package team.themoment.hellogsmv3.domain.member.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.member.dto.request.GenerateCodeReqDto;
import team.themoment.hellogsmv3.domain.member.entity.AuthenticationCode;
import team.themoment.hellogsmv3.domain.member.repo.CodeRepository;
import team.themoment.hellogsmv3.domain.member.service.GenerateCodeService;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GenerateTestCodeServiceImpl extends GenerateCodeService {

    private final CodeRepository codeRepository;
    private static final Random RANDOM = new Random();

    @Override
    public String execute(Long authenticationId, GenerateCodeReqDto reqDto) {
        final String code = generateUniqueCode(RANDOM, codeRepository);

        AuthenticationCode authenticationCode = codeRepository.findByAuthenticationId(authenticationId)
                .orElse(null);

        codeRepository.save(createAuthenticationCode(
                authenticationCode,
                authenticationId,
                code,
                reqDto.phoneNumber(),
                true));

        codeRepository.save(new AuthenticationCode(authenticationId, code, reqDto.phoneNumber(), LocalDateTime.now()));

        return code;
    }
}
