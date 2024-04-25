package team.themoment.hellogsmv3.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsmv3.domain.auth.dto.BasicAuthenticationDto;
import team.themoment.hellogsmv3.domain.auth.entity.Authentication;
import team.themoment.hellogsmv3.domain.auth.repo.AuthenticationRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

@Service
@RequiredArgsConstructor
public class BringAuthenticationByIdQuery {

    private final AuthenticationRepository authenticationRepository;

    @Transactional(readOnly = true)
    public BasicAuthenticationDto execute(Long authenticationId) {
        Authentication authentication = authenticationRepository.findById(authenticationId)
                .orElseThrow(() -> new ExpectedException("authentication을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        return BasicAuthenticationDto
                .builder()
                .id(authenticationId)
                .provider(authentication.getProviderName())
                .providerId(authentication.getProviderId())
                .role(authentication.getRole())
                .build();
    }
}
