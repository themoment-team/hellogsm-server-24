package team.themoment.hellogsmv3.domain.applicant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsmv3.domain.applicant.dto.request.AuthenticateCodeReqDto;
import team.themoment.hellogsmv3.domain.member.entity.AuthenticationCode;
import team.themoment.hellogsmv3.domain.member.repo.CodeRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticateCodeService {

    private final CodeRepository codeRepository;

    public void execute(Long userId, AuthenticateCodeReqDto reqDto) {

        AuthenticationCode code = codeRepository.findByAuthenticationId(userId)
                .orElseThrow(() -> new ExpectedException("사용자의 code가 존재하지 않습니다. 사용자의 ID : " + userId, HttpStatus.BAD_REQUEST));

        if (!code.getCode().equals(reqDto.code()))
            throw new ExpectedException("유효하지 않은 code 입니다. 이전 혹은 잘못된 code입니다.", HttpStatus.BAD_REQUEST);

        code.authenticatedAuthenticationCode();

        codeRepository.save(code);
    }
}
