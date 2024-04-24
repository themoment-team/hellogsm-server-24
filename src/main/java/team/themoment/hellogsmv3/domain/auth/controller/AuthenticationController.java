package team.themoment.hellogsmv3.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.themoment.hellogsmv3.domain.auth.dto.BasicAuthenticationDto;
import team.themoment.hellogsmv3.domain.auth.service.AuthenticationService;

@RestController
@RequestMapping("/authentication/v3")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @GetMapping("/authentication/{authenticationId}")
    public ResponseEntity<BasicAuthenticationDto> getAuthenticationInfo(
            @PathVariable Long authenticationId
    ) {
        BasicAuthenticationDto authenticationDto = authenticationService.queryAuthenticationInfo(authenticationId);
        return ResponseEntity.ok(authenticationDto);
    }
}

