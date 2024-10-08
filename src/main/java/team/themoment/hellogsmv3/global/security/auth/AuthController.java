package team.themoment.hellogsmv3.global.security.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.themoment.hellogsmv3.global.common.response.CommonApiResponse;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

@Tag(name = "Auth API", description = "인증 관련 API입니다.")
@RestController
@RequestMapping("/auth/v3")
@RequiredArgsConstructor
public class AuthController {

    @Operation(summary = "로그아웃", description = "로그아웃을 진행합니다.")
    @GetMapping("/logout")
    public CommonApiResponse logout(HttpServletRequest req, HttpServletResponse res){
        logoutProcess(req, res, SecurityContextHolder.getContext().getAuthentication());
        return CommonApiResponse.success("로그아웃 되었습니다.");
    }

    private static void logoutProcess(HttpServletRequest req, HttpServletResponse res, Authentication auth) {
        if (auth instanceof OAuth2AuthenticationToken) {
            new SecurityContextLogoutHandler().logout(req, res, SecurityContextHolder.getContext().getAuthentication());
        } else {
            throw new ExpectedException("인증 정보가 올바르지 않습니다.", HttpStatus.UNAUTHORIZED);
        }
    }

}
