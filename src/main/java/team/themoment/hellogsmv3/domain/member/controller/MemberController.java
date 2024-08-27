package team.themoment.hellogsmv3.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.themoment.hellogsmv3.domain.member.dto.request.CreateMemberReqDto;
import team.themoment.hellogsmv3.domain.member.entity.type.Role;
import team.themoment.hellogsmv3.domain.member.service.CreateMemberService;
import team.themoment.hellogsmv3.domain.member.dto.request.AuthenticateCodeReqDto;
import team.themoment.hellogsmv3.domain.member.dto.request.GenerateCodeReqDto;
import team.themoment.hellogsmv3.domain.member.dto.response.FoundMemberAuthInfoResDto;
import team.themoment.hellogsmv3.domain.member.dto.response.FoundMemberResDto;
import team.themoment.hellogsmv3.domain.member.service.AuthenticateCodeService;
import team.themoment.hellogsmv3.domain.member.service.QueryMemberAuthInfoByIdService;
import team.themoment.hellogsmv3.domain.member.service.QueryMemberByIdService;
import team.themoment.hellogsmv3.domain.member.service.impl.GenerateCodeServiceImpl;
import team.themoment.hellogsmv3.domain.member.service.impl.GenerateTestCodeServiceImpl;
import team.themoment.hellogsmv3.global.common.handler.annotation.AuthRequest;
import team.themoment.hellogsmv3.global.common.response.CommonApiResponse;
import team.themoment.hellogsmv3.global.security.auth.AuthenticatedUserManager;

@Tag(name = "Member API", description = "맴버 관련 API입니다.")
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
    private final QueryMemberAuthInfoByIdService queryMemberAuthInfoByIdService;

    @Operation(summary = "인증코드 전송", description = "전화번호를 요청받아 인증코드를 전송합니다.")
    @PostMapping("/member/me/send-code")
    public CommonApiResponse sendCode(
            @AuthRequest Long memberId,
            @RequestBody @Valid GenerateCodeReqDto reqDto
    ) {
        generateCodeService.execute(memberId, reqDto);
        return CommonApiResponse.success("전송되었습니다.");
    }

    @Operation(summary = "인증코드 테스트 전송", description = "전화번호를 요청받아 인증코드를 반환합니다. 횟수가 제한이 없습니다.")
    @PostMapping("/member/me/send-code-test")
    public CommonApiResponse sendCodeTest(
            @AuthRequest Long memberId,
            @RequestBody @Valid GenerateCodeReqDto reqDto
    ) {
        String code = generateTestCodeService.execute(memberId, reqDto);
        return CommonApiResponse.success("전송되었습니다. : " + code);
    }

    @Operation(summary = "인증코드 인증", description = "인증코드를 요청받아 인증을 진행합니다.")
    @PostMapping("/member/me/auth-code")
    public CommonApiResponse authCode(
            @AuthRequest Long memberId,
            @RequestBody @Valid AuthenticateCodeReqDto reqDto
    ) {
        authenticateCodeService.execute(memberId, reqDto);
        return CommonApiResponse.success("인증되었습니다.");
    }

    @Operation(summary = "내 맴버 조회", description = "내 맴버 정보를 조회합니다.")
    @GetMapping("/member/me")
    public FoundMemberResDto find(
            @AuthRequest Long memberId
    ) {
        return queryMemberByIdService.execute(memberId);
    }

    @Operation(summary = "맴버 조회", description = "맴버 id로 맴버 정보를 조회합니다.")
    @GetMapping("/member/{memberId}")
    public FoundMemberResDto findByMemberId(
            @PathVariable Long memberId
    ) {
        return queryMemberByIdService.execute(memberId);
    }

    @Operation(summary = "내 맴버 등록", description = "인증코드와 맴버 정보를 요청받아 맴버를 등록합니다.")
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

    @Operation(summary = "내 인증 정보 조회", description = "내 인증 정보를 조회합니다.")
    @GetMapping("/auth-info/me")
    public FoundMemberAuthInfoResDto findAuthInfo(
            @AuthRequest Long memberId
    ) {
        return queryMemberAuthInfoByIdService.execute(memberId);
    }

    @Operation(summary = "인정 정보 조회", description = "맴버 id로 인증 정보를 조회합니다.")
    @GetMapping("/auth-info/{memberId}")
    public FoundMemberAuthInfoResDto findAuthInfoByMemberId(
            @PathVariable Long memberId
    ) {
        return queryMemberAuthInfoByIdService.execute(memberId);
    }
}
