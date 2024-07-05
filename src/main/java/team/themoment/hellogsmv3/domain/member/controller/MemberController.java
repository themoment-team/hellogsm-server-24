package team.themoment.hellogsmv3.domain.member.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.themoment.hellogsmv3.domain.member.dto.CreateMemberReqDto;
import team.themoment.hellogsmv3.domain.member.dto.request.ModifyMemberReqDto;
import team.themoment.hellogsmv3.domain.member.entity.type.Role;
import team.themoment.hellogsmv3.domain.member.service.CreateMemberService;
import team.themoment.hellogsmv3.domain.member.dto.request.AuthenticateCodeReqDto;
import team.themoment.hellogsmv3.domain.member.dto.request.GenerateCodeReqDto;
import team.themoment.hellogsmv3.domain.member.dto.response.FoundMemberAuthInfoResDto;
import team.themoment.hellogsmv3.domain.member.dto.response.FoundMemberResDto;
import team.themoment.hellogsmv3.domain.member.service.AuthenticateCodeService;
import team.themoment.hellogsmv3.domain.member.service.ModifyMemberService;
import team.themoment.hellogsmv3.domain.member.service.QueryMemberAuthInfoByIdService;
import team.themoment.hellogsmv3.domain.member.service.QueryMemberByIdService;
import team.themoment.hellogsmv3.domain.member.service.impl.GenerateCodeServiceImpl;
import team.themoment.hellogsmv3.domain.member.service.impl.GenerateTestCodeServiceImpl;
import team.themoment.hellogsmv3.global.common.handler.annotation.AuthRequest;
import team.themoment.hellogsmv3.global.common.response.CommonApiResponse;
import team.themoment.hellogsmv3.global.security.auth.AuthenticatedUserManager;

@RestController
@RequestMapping("/member/v3")
@RequiredArgsConstructor
public class MemberController {

    private final AuthenticatedUserManager manager;
    private final GenerateCodeServiceImpl generateCodeService;
    private final GenerateTestCodeServiceImpl generateTestCodeService;
    private final AuthenticateCodeService authenticateCodeService;
    private final QueryMemberByIdService queryMemberByIdService;
    private final CreateMemberService createMemberService;
    private final ModifyMemberService modifyMemberService;
    private final QueryMemberAuthInfoByIdService queryMemberAuthInfoByIdService;

    @PostMapping("/member/me/send-code")
    public CommonApiResponse sendCode(
        @AuthRequest Long memberId, 
        @RequestBody GenerateCodeReqDto reqDto
    ) {
        generateCodeService.execute(memberId, reqDto);
        return CommonApiResponse.success("전송되었습니다.");
    }

    @PostMapping("/member/me/send-code-test")
    public CommonApiResponse sendCodeTest(@AuthRequest Long memberId, @RequestBody GenerateCodeReqDto reqDto) {
        String code = generateTestCodeService.execute(memberId, reqDto);
        return CommonApiResponse.success("전송되었습니다. : " + code);
    }

    @PostMapping("/member/me/auth-code")
    public CommonApiResponse authCode(
            @AuthRequest Long memberId,
            @RequestBody @Valid AuthenticateCodeReqDto reqDto
    ) {
        authenticateCodeService.execute(memberId, reqDto);
        return CommonApiResponse.success("인증되었습니다.");
    }

    @GetMapping("/member/me")
    public FoundMemberResDto find(
            @AuthRequest Long memberId
    ) {
        return queryMemberByIdService.execute(memberId);
    }

    @GetMapping("/member/{memberId}")
    public FoundMemberResDto findByMemberId(
            @PathVariable Long memberId
    ) {
        return queryMemberByIdService.execute(memberId);
    }

    @PostMapping("/member/me")
    public CommonApiResponse create(
            HttpServletRequest httpServletRequest,
            @RequestBody @Valid CreateMemberReqDto reqDto,
            @AuthRequest Long memberId
    ) {
        Role role = createMemberService.execute(reqDto, memberId);
        manager.setRole(httpServletRequest, role);
        return CommonApiResponse.created("본인인증이 완료되었습니다.");
    }

    @GetMapping("/auth-info/me")
    public FoundMemberAuthInfoResDto findAuthInfo(
            @AuthRequest Long memberId
    ) {
        return queryMemberAuthInfoByIdService.execute(memberId);
    }

    @GetMapping("/auth-info/{memberId}")
    public FoundMemberAuthInfoResDto findAuthInfoByMemberId(
            @PathVariable Long memberId
    ) {
        return queryMemberAuthInfoByIdService.execute(memberId);

    }

    @PutMapping("/member/me")
    public CommonApiResponse modify(
            @RequestBody @Valid ModifyMemberReqDto reqDto,
            @AuthRequest Long memberId
    ) {
        modifyMemberService.execute(reqDto, memberId);
        return CommonApiResponse.success("수정되었습니다.");
    }
}
