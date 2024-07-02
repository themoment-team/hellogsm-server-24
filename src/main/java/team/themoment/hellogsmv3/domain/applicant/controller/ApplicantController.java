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
import team.themoment.hellogsmv3.domain.member.entity.type.Role;
import team.themoment.hellogsmv3.global.common.handler.annotation.AuthRequest;
import team.themoment.hellogsmv3.global.common.response.CommonApiResponse;
import team.themoment.hellogsmv3.global.security.auth.AuthenticatedUserManager;

@RestController
@RequestMapping("/applicant/v3")
@RequiredArgsConstructor
public class ApplicantController {

    private final CreateApplicantService createApplicantService;
    private final ModifyApplicantService modifyApplicantService;
    private final QueryApplicantByIdService queryApplicantByIdService;
    private final AuthenticateCodeService authenticateCodeService;
    private final GenerateTestCodeServiceImpl generateTestCodeService;
    private final GenerateCodeServiceImpl generateCodeService;
    private final AuthenticatedUserManager manager;

    @PostMapping("/applicant/me/send-code")
    public CommonApiResponse sendCode(
            @RequestBody @Valid GenerateCodeReqDto reqDto,
            @AuthRequest Long authId
            ) {
        generateCodeService.execute(authId, reqDto);
        return CommonApiResponse.success("전송되었습니다.");
    }

    @PostMapping("/applicant/me/send-code-test")
    public CommonApiResponse sendCodeTest(
            @RequestBody @Valid GenerateCodeReqDto reqDto,
            @AuthRequest Long authId
    ) {
        String code = generateTestCodeService.execute(authId, reqDto);
        return CommonApiResponse.success("전송되었습니다. : " + code);
    }

    @PostMapping("/applicant/me/auth-code")
    public CommonApiResponse authCode(
            @RequestBody @Valid AuthenticateCodeReqDto reqDto,
            @AuthRequest Long authId
    ) {
        authenticateCodeService.execute(authId, reqDto);
        return CommonApiResponse.success("인증되었습니다.");
    }

    @PostMapping("/applicant/me")
    public CommonApiResponse create(
            HttpServletRequest httpServletRequest,
            @RequestBody @Valid ApplicantReqDto reqDto,
            @AuthRequest Long authId
    ) {
        Role role = createApplicantService.execute(reqDto, authId);
        manager.setRole(httpServletRequest, role);
        return CommonApiResponse.created("본인인증이 완료되었습니다.");
    }

    @PutMapping("/applicant/me")
    public CommonApiResponse modify(
            @RequestBody @Valid ApplicantReqDto reqDto,
            @AuthRequest Long authId
    ) {
        modifyApplicantService.execute(reqDto, authId);
        return CommonApiResponse.success("수정되었습니다.");
    }

    @GetMapping("/applicant/me")
    public FoundApplicantResDto find(@AuthRequest Long authId) {
        return queryApplicantByIdService.execute(authId);
    }

    @GetMapping("/applicant/{authenticationId}")
    public FoundApplicantResDto findByUserId(
            @PathVariable Long authenticationId
    ) {
        return queryApplicantByIdService.execute(authenticationId);
    }
}
