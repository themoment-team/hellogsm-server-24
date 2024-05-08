package team.themoment.hellogsmv3.domain.applicant.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsmv3.domain.applicant.dto.request.AuthenticateCodeReqDto;
import team.themoment.hellogsmv3.domain.applicant.entity.AuthenticationCode;
import team.themoment.hellogsmv3.domain.applicant.repo.CodeRepository;
import team.themoment.hellogsmv3.domain.applicant.service.AuthenticateCodeService;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticateCodeServiceImpl implements AuthenticateCodeService {

    private final CodeRepository codeRepository;

    @Override
    public void execute(Long userId, AuthenticateCodeReqDto reqDto) {
        List<AuthenticationCode> codes = codeRepository.findByAuthenticationId(userId);

        AuthenticationCode recentCode = codes.stream()
                .max(Comparator.comparing(AuthenticationCode::getCreatedAt))
                .orElseThrow(() -> new ExpectedException("사용자의 code가 존재하지 않습니다. 사용자의 ID : "+userId, HttpStatus.BAD_REQUEST));

        if (!recentCode.getCode().equals(reqDto.code()))
            throw new ExpectedException("유효하지 않은 code 입니다. 이전 혹은 잘못된 code입니다.", HttpStatus.BAD_REQUEST);

        codeRepository.save(new AuthenticationCode(recentCode.getCode(), recentCode.getAuthenticationId(), true, recentCode.getPhoneNumber(), recentCode.getCreatedAt()));
    }
}
