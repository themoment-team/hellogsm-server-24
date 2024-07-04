package team.themoment.hellogsmv3.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsmv3.domain.member.dto.request.ModifyMemberReqDto;
import team.themoment.hellogsmv3.domain.member.entity.AuthenticationCode;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.member.repo.MemberRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

@Service
@RequiredArgsConstructor
public class ModifyMemberService {

    private final MemberRepository memberRepository;
    private final CommonCodeService commonCodeService;

    @Transactional
    public void execute(ModifyMemberReqDto reqDto, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ExpectedException("존재하지 않는 지원자입니다. member ID: " + memberId, HttpStatus.NOT_FOUND));

        commonCodeService.validateAndDelete(memberId, reqDto.code(), reqDto.phoneNumber());

        Member newMember = Member.builder()
                .id(member.getId())
                .email(member.getEmail())
                .authReferrerType(member.getAuthReferrerType())
                .name(reqDto.name())
                .birth(reqDto.birth())
                .phoneNumber(reqDto.phoneNumber())
                .sex(reqDto.sex())
                .role(member.getRole())
                .build();

        memberRepository.save(newMember);
    }
}
