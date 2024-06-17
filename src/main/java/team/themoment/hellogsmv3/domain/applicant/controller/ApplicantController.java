package team.themoment.hellogsmv3.domain.applicant.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.themoment.hellogsmv3.domain.applicant.dto.request.ApplicantReqDto;
import team.themoment.hellogsmv3.domain.applicant.dto.response.FoundApplicantResDto;
import team.themoment.hellogsmv3.domain.applicant.service.CreateApplicantService;
import team.themoment.hellogsmv3.domain.applicant.service.QueryApplicantByIdService;
import team.themoment.hellogsmv3.domain.applicant.dto.request.AuthenticateCodeReqDto;
import team.themoment.hellogsmv3.domain.applicant.dto.request.GenerateCodeReqDto;
import team.themoment.hellogsmv3.domain.applicant.service.AuthenticateCodeService;
import team.themoment.hellogsmv3.domain.applicant.service.ModifyApplicantService;
import team.themoment.hellogsmv3.domain.applicant.service.impl.GenerateCodeServiceImpl;
import team.themoment.hellogsmv3.domain.applicant.service.impl.GenerateTestCodeServiceImpl;
import team.themoment.hellogsmv3.domain.auth.type.Role;
import team.themoment.hellogsmv3.global.common.response.CommonApiResponse;
import team.themoment.hellogsmv3.global.security.auth.AuthenticatedUserManager;

@RestController
@RequestMapping("/applicant/v3")
@RequiredArgsConstructor
public class ApplicantController {

    private final AuthenticatedUserManager manager;
    private final CreateApplicantService createApplicantService;
    private final ModifyApplicantService modifyApplicantService;
    private final QueryApplicantByIdService queryApplicantByIdService;
    private final AuthenticateCodeService authenticateCodeService;
    private final GenerateTestCodeServiceImpl generateTestCodeService;
    private final GenerateCodeServiceImpl generateCodeService;

    @PostMapping("/applicant/me/send-code")
    public ResponseEntity<CommonApiResponse> sendCode(
            @RequestBody @Valid GenerateCodeReqDto reqDto
    ) {
        generateCodeService.execute(manager.getId(), reqDto);
        return ResponseEntity.status(HttpStatus.OK).body(CommonApiResponse.success("전송되었습니다."));
    }

    @PostMapping("/applicant/me/send-code-test")
    public ResponseEntity<CommonApiResponse> sendCodeTest(
            @RequestBody @Valid GenerateCodeReqDto reqDto
    ) {
        String code = generateTestCodeService.execute(manager.getId(), reqDto);
        return ResponseEntity.status(HttpStatus.OK).body(CommonApiResponse.success("전송되었습니다. : " + code));
    }

    @PostMapping("/applicant/me/auth-code")
    public ResponseEntity<CommonApiResponse> authCode(
            @RequestBody @Valid AuthenticateCodeReqDto reqDto
    ) {
        authenticateCodeService.execute(manager.getId(), reqDto);
        return ResponseEntity.status(HttpStatus.OK).body(CommonApiResponse.success("인증되었습니다."));
    }

    @PostMapping("/applicant/me")
    public ResponseEntity<CommonApiResponse> create(
            HttpServletRequest httpServletRequest,
            @RequestBody @Valid ApplicantReqDto reqDto
    ) {
        Role role = createApplicantService.execute(reqDto, manager.getId());
        manager.setRole(httpServletRequest, role);
        return ResponseEntity.status(HttpStatus.CREATED).body(CommonApiResponse.success("본인인증이 완료되었습니다"));
    }

    @PutMapping("/applicant/me")
    public ResponseEntity<CommonApiResponse> modify(
            @RequestBody @Valid ApplicantReqDto reqDto
    ) {
        modifyApplicantService.execute(reqDto, manager.getId());
        return ResponseEntity.status(HttpStatus.OK).body(CommonApiResponse.success("수정되었습니다"));
    }

    @GetMapping("/applicant/me")
    public ResponseEntity<FoundApplicantResDto> find() {
        FoundApplicantResDto foundApplicantResDto = queryApplicantByIdService.execute(manager.getId());
        return ResponseEntity.status(HttpStatus.OK).body(foundApplicantResDto);
    }

    @GetMapping("/applicant/{authenticationId}")
    public ResponseEntity<FoundApplicantResDto> findByUserId(
            @PathVariable Long authenticationId
    ) {
        FoundApplicantResDto foundApplicantResDto = queryApplicantByIdService.execute(authenticationId);
        return ResponseEntity.status(HttpStatus.OK).body(foundApplicantResDto);
    }
}
