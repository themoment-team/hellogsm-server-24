package team.themoment.hellogsmv3.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.member.dto.response.FoundMemberAuthInfoResDto;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.member.repo.MemberRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

@Service
@RequiredArgsConstructor
public class QueryMemberAuthInfoByIdService {

    private final MemberService memberService;

    public FoundMemberAuthInfoResDto execute(Long memberId) {
        Member member = memberService.findById(memberId);

        return FoundMemberAuthInfoResDto.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .authReferrerType(member.getAuthReferrerType())
                .role(member.getRole())
                .build();
    }
}
