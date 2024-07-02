package team.themoment.hellogsmv3.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.themoment.hellogsmv3.domain.applicant.dto.response.FoundApplicantResDto;
import team.themoment.hellogsmv3.domain.member.dto.FoundMemberResDto;
import team.themoment.hellogsmv3.domain.member.service.QueryMemberByIdService;
import team.themoment.hellogsmv3.global.common.handler.annotation.AuthRequest;

@RestController
@RequestMapping("/member/v3")
@RequiredArgsConstructor
public class MemberController {

    private final QueryMemberByIdService queryMemberByIdService;

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
