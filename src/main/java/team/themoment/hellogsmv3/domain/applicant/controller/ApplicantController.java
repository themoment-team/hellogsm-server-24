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
import team.themoment.hellogsmv3.domain.applicant.dto.response.CreateApplicantResDto;
import team.themoment.hellogsmv3.domain.applicant.service.CreateApplicantService;
import team.themoment.hellogsmv3.global.security.auth.AuthenticatedUserManager;

import java.util.Map;

@RestController
@RequestMapping("/applicant/v3")
@RequiredArgsConstructor
public class ApplicantController {

    private final AuthenticatedUserManager manager;
    private final CreateApplicantService createApplicantService;

    @PostMapping("/applicant/me")
    public ResponseEntity<Map<String, String>> create(
            HttpServletRequest httpServletRequest,
            @RequestBody @Valid ApplicantReqDto reqDto
    ) {
        CreateApplicantResDto resDto = createApplicantService.execute(reqDto, manager.getId());
        manager.setRole(httpServletRequest, resDto.authenticationRole());
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "본인인증이 완료되었습니다"));
    }
}
