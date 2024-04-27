package team.themoment.hellogsmv3.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.themoment.hellogsmv3.domain.auth.dto.BasicAuthenticationDto;
import team.themoment.hellogsmv3.domain.auth.service.QueryAuthenticationById;
import team.themoment.hellogsmv3.global.security.auth.AuthenticatedUserManager;

@RestController
@RequestMapping("/authentication/v3")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticatedUserManager authenticatedUserManager;
    private final QueryAuthenticationById queryAuthenticationById;

    @GetMapping("/authentication/{authenticationId}")
    public ResponseEntity<BasicAuthenticationDto> getAuthenticationInfo(
            @PathVariable Long authenticationId
    ) {
        BasicAuthenticationDto authenticationDto = queryAuthenticationById.execute(authenticationId);
        return ResponseEntity.ok(authenticationDto);
    }

    @GetMapping("/authentication/me")
    public ResponseEntity<BasicAuthenticationDto> getMyAuthenticationInfo() {
        Long authenticationId = authenticatedUserManager.getId();
        BasicAuthenticationDto authenticationDto = queryAuthenticationById.execute(authenticationId);
        return ResponseEntity.ok(authenticationDto);
    }
}
