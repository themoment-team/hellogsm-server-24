package team.themoment.hellogsmv3.domain.applicant.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.themoment.hellogsmv3.domain.applicant.dto.request.ApplicantReqDto;
import team.themoment.hellogsmv3.domain.applicant.dto.request.AuthenticateCodeReqDto;
import team.themoment.hellogsmv3.domain.applicant.dto.request.GenerateCodeReqDto;
import team.themoment.hellogsmv3.domain.applicant.service.AuthenticateCodeService;
import team.themoment.hellogsmv3.domain.applicant.service.CreateApplicantService;
import team.themoment.hellogsmv3.domain.applicant.service.impl.GenerateCodeServiceImpl;
import team.themoment.hellogsmv3.domain.applicant.service.impl.GenerateTestCodeServiceImpl;
import team.themoment.hellogsmv3.domain.auth.type.Role;
import team.themoment.hellogsmv3.global.security.auth.AuthenticatedUserManager;

import java.util.Map;

@RestController
@RequestMapping("/applicant/v3")
@RequiredArgsConstructor
public class ApplicantController {

    private final AuthenticatedUserManager manager;
    private final CreateApplicantService createApplicantService;
    private final AuthenticateCodeService authenticateCodeService;
    private final GenerateTestCodeServiceImpl generateTestCodeService;
    private final GenerateCodeServiceImpl generateCodeService;

    @PostMapping("/applicant/me/send-code")
    public ResponseEntity<Map<String, String>> sendCode(
            @RequestBody @Valid GenerateCodeReqDto reqDto
    ) {
        generateCodeService.execute(manager.getId(), reqDto);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "전송되었습니다."));
    }

    @PostMapping("/applicant/me/send-code-test")
    public ResponseEntity<Map<String, String>> sendCodeTest(
            @RequestBody @Valid GenerateCodeReqDto reqDto
    ) {
        String code = generateTestCodeService.execute(manager.getId(), reqDto);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "전송되었습니다. : " + code));
    }

    @PostMapping("/applicant/me/auth-code")
    public ResponseEntity<Map<String, String>> authCode(
            @RequestBody @Valid AuthenticateCodeReqDto reqDto
    ) {
        authenticateCodeService.execute(manager.getId(), reqDto);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "인증되었습니다."));
    }

    @PostMapping("/applicant/me")
    public ResponseEntity<Map<String, String>> create(
            HttpServletRequest httpServletRequest,
            @RequestBody @Valid ApplicantReqDto reqDto
    ) {
        Role role = createApplicantService.execute(reqDto, manager.getId());
        manager.setRole(httpServletRequest, role);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "본인인증이 완료되었습니다"));
    }
}
