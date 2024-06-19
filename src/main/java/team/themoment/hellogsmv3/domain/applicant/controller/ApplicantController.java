package team.themoment.hellogsmv3.domain.applicant.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import team.themoment.hellogsmv3.global.common.response.CommonApiMessageResponse;
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
    public CommonApiMessageResponse sendCode(
            @RequestBody @Valid GenerateCodeReqDto reqDto
    ) {
        generateCodeService.execute(manager.getId(), reqDto);
        return CommonApiMessageResponse.success("전송되었습니다.");
    }

    @PostMapping("/applicant/me/send-code-test")
    public CommonApiMessageResponse sendCodeTest(
            @RequestBody @Valid GenerateCodeReqDto reqDto
    ) {
        String code = generateTestCodeService.execute(manager.getId(), reqDto);
        return CommonApiMessageResponse.success("전송되었습니다. : " + code);
    }

    @PostMapping("/applicant/me/auth-code")
    public CommonApiMessageResponse authCode(
            @RequestBody @Valid AuthenticateCodeReqDto reqDto
    ) {
        authenticateCodeService.execute(manager.getId(), reqDto);
        return CommonApiMessageResponse.success("인증되었습니다.");
    }

    @PostMapping("/applicant/me")
    public CommonApiMessageResponse create(
            HttpServletRequest httpServletRequest,
            @RequestBody @Valid ApplicantReqDto reqDto
    ) {
        Role role = createApplicantService.execute(reqDto, manager.getId());
        manager.setRole(httpServletRequest, role);
        return CommonApiMessageResponse.created("본인인증이 완료되었습니다.");
    }

    @PutMapping("/applicant/me")
    public CommonApiMessageResponse modify(
            @RequestBody @Valid ApplicantReqDto reqDto
    ) {
        modifyApplicantService.execute(reqDto, manager.getId());
        return CommonApiMessageResponse.success("수정되었습니다.");
    }

    @GetMapping("/applicant/me")
    public FoundApplicantResDto find() {
        return queryApplicantByIdService.execute(manager.getId());
    }

    @GetMapping("/applicant/{authenticationId}")
    public FoundApplicantResDto findByUserId(
            @PathVariable Long authenticationId
    ) {
        return queryApplicantByIdService.execute(authenticationId);
    }
}
