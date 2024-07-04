package team.themoment.hellogsmv3.domain.member.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.themoment.hellogsmv3.domain.member.dto.CreateMemberReqDto;
import team.themoment.hellogsmv3.domain.member.dto.FoundMemberResDto;
import team.themoment.hellogsmv3.domain.member.entity.type.Role;
import team.themoment.hellogsmv3.domain.member.service.CreateMemberService;
import team.themoment.hellogsmv3.domain.member.service.QueryMemberByIdService;
import team.themoment.hellogsmv3.global.common.handler.annotation.AuthRequest;
import team.themoment.hellogsmv3.global.common.response.CommonApiResponse;
import team.themoment.hellogsmv3.global.security.auth.AuthenticatedUserManager;

@RestController
@RequestMapping("/member/v3")
@RequiredArgsConstructor
public class MemberController {

    private final AuthenticatedUserManager manager;
    private final QueryMemberByIdService queryMemberByIdService;
    private final CreateMemberService createMemberService;

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
}
