package team.themoment.hellogsmv3.domain.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.themoment.hellogsmv3.domain.member.dto.request.AuthenticateCodeReqDto;
import team.themoment.hellogsmv3.domain.member.dto.request.GenerateCodeReqDto;
import team.themoment.hellogsmv3.domain.member.dto.response.FoundMemberResDto;
import team.themoment.hellogsmv3.domain.member.service.AuthenticateCodeService;
import team.themoment.hellogsmv3.domain.member.service.QueryMemberByIdService;
import team.themoment.hellogsmv3.domain.member.service.impl.GenerateCodeServiceImpl;
import team.themoment.hellogsmv3.domain.member.service.impl.GenerateTestCodeServiceImpl;
import team.themoment.hellogsmv3.global.common.handler.annotation.AuthRequest;
import team.themoment.hellogsmv3.global.common.response.CommonApiResponse;

@RestController
@RequestMapping("/member/v3")
@RequiredArgsConstructor
public class MemberController {

    private final GenerateCodeServiceImpl generateCodeService;
    private final GenerateTestCodeServiceImpl generateTestCodeService;
    private final AuthenticateCodeService authenticateCodeService;
    private final QueryMemberByIdService queryMemberByIdService;

    @PostMapping("/member/me/send-code")
    public CommonApiResponse sendCode(@AuthRequest Long memberId, @RequestBody GenerateCodeReqDto reqDto) {
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
}
