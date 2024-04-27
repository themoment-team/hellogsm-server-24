package team.themoment.hellogsmv3.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.themoment.hellogsmv3.domain.auth.dto.BasicAuthenticationDto;
import team.themoment.hellogsmv3.domain.auth.service.BringAuthenticationByIdQuery;

@RestController
@RequestMapping("/authentication/v3")
@RequiredArgsConstructor
public class AuthenticationController {

    private final BringAuthenticationByIdQuery bringAuthenticationByIdQuery;

    @GetMapping("/authentication/{authenticationId}")
    public ResponseEntity<BasicAuthenticationDto> getAuthenticationInfo(
            @PathVariable Long authenticationId
    ) {
        BasicAuthenticationDto authenticationDto = bringAuthenticationByIdQuery.execute(authenticationId);
        return ResponseEntity.ok(authenticationDto);
    }
}
