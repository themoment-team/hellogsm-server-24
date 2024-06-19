package team.themoment.hellogsmv3.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.themoment.hellogsmv3.domain.auth.dto.BasicAuthenticationDto;
import team.themoment.hellogsmv3.domain.auth.service.DeleteMyAuthenticationService;
import team.themoment.hellogsmv3.domain.auth.service.QueryAuthenticationById;
import team.themoment.hellogsmv3.global.common.response.CommonApiMessageResponse;
import team.themoment.hellogsmv3.global.security.auth.AuthenticatedUserManager;

import java.util.Map;

@RestController
@RequestMapping("/authentication/v3")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticatedUserManager manager;
    private final QueryAuthenticationById queryAuthenticationById;
    private final DeleteMyAuthenticationService deleteMyAuthenticationService;

    @GetMapping("/authentication/{authenticationId}")
    public BasicAuthenticationDto getAuthenticationInfo(
            @PathVariable Long authenticationId
    ) {
        return queryAuthenticationById.execute(authenticationId);
    }

    @GetMapping("/authentication/me")
    public BasicAuthenticationDto getMyAuthenticationInfo() {
        return queryAuthenticationById.execute(manager.getId());
    }

    @DeleteMapping("/authentication/me")
    public CommonApiMessageResponse deleteMyAuthentication() {
        Long authenticationId = manager.getId();
        deleteMyAuthenticationService.execute(authenticationId);
        return CommonApiMessageResponse.success("삭제되었습니다.");
    }
}
