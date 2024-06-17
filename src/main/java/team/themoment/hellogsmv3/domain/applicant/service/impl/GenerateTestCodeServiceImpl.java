package team.themoment.hellogsmv3.domain.applicant.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.applicant.dto.request.GenerateCodeReqDto;
import team.themoment.hellogsmv3.domain.applicant.entity.AuthenticationCode;
import team.themoment.hellogsmv3.domain.applicant.repo.CodeRepository;
import team.themoment.hellogsmv3.domain.applicant.service.GenerateCodeService;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GenerateTestCodeServiceImpl extends GenerateCodeService {

    private final CodeRepository codeRepository;
    private final static Random RANDOM = new Random();

    @Override
    public String execute(Long authenticationId, GenerateCodeReqDto reqDto) {
        final String code = generateUniqueCode(RANDOM, codeRepository);
        codeRepository.save(new AuthenticationCode(code, authenticationId, false, reqDto.phoneNumber(), LocalDateTime.now()));
        return code;
    }
}
