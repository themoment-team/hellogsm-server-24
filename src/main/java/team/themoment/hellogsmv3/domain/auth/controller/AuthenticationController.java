package team.themoment.hellogsmv3.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.themoment.hellogsmv3.domain.auth.dto.BasicAuthenticationDto;
import team.themoment.hellogsmv3.domain.auth.service.DeleteMyAuthenticationService;
import team.themoment.hellogsmv3.domain.auth.service.QueryAuthenticationById;
import team.themoment.hellogsmv3.global.security.auth.AuthenticatedUserManager;

import java.util.Map;

@RestController
@RequestMapping("/authentication/v3")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticatedUserManager authenticatedUserManager;
    private final QueryAuthenticationById queryAuthenticationById;
    private final DeleteMyAuthenticationService deleteMyAuthenticationService;

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

    @DeleteMapping("/authentication/me")
    public ResponseEntity<Map<String, String>> deleteMyAuthentication() {
        Long authenticationId = authenticatedUserManager.getId();
        deleteMyAuthenticationService.execute(authenticationId);
        return ResponseEntity.ok().body(Map.of("message", "삭제되었습니다."));
    }
}
