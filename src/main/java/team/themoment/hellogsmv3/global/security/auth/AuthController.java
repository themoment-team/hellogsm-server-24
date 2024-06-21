package team.themoment.hellogsmv3.global.security.auth;

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
import team.themoment.hellogsmv3.global.common.response.CommonApiMessageResponse;

@RestController
@RequestMapping("/auth/v3")
@RequiredArgsConstructor
public class AuthController {

    @GetMapping("/logout")
    public CommonApiMessageResponse logout(HttpServletRequest req, HttpServletResponse res){
        return logoutProccess(req, res, SecurityContextHolder.getContext().getAuthentication()) ?
                CommonApiMessageResponse.success("로그아웃 되었습니다.") :
                CommonApiMessageResponse.error("인증 정보가 올바르지 않습니다.", HttpStatus.UNAUTHORIZED);
    }

    private static boolean logoutProccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) {
        if (auth instanceof OAuth2AuthenticationToken) {
            new SecurityContextLogoutHandler().logout(req, res, SecurityContextHolder.getContext().getAuthentication());
            return true;
        } else {
            return false;
        }
    }

}
