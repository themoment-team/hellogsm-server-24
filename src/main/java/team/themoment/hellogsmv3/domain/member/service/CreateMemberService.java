package team.themoment.hellogsmv3.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.member.dto.CreateMemberReqDto;
import team.themoment.hellogsmv3.domain.member.entity.AuthenticationCode;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.member.entity.type.Role;
import team.themoment.hellogsmv3.domain.member.repo.MemberRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

@Service
@RequiredArgsConstructor
public class CreateMemberService {

    private final MemberRepository memberRepository;
    private final CommonCodeService commonCodeService;

    public Role execute(CreateMemberReqDto reqDto, Long memberId) {

        AuthenticationCode code = commonCodeService.validateAndGetRecentCode(memberId, reqDto.code(), reqDto.phoneNumber());

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ExpectedException("존재하지 않는 지원자입니다. member ID: " + memberId, HttpStatus.NOT_FOUND));

        Member newMember = Member.builder()     // TODO 새로운 객체 생성 후 저장 시 auditing이 정상작동 하는지
                .id(member.getId())
                .email(member.getEmail())
                .authReferrerType(member.getAuthReferrerType())
                .name(reqDto.name())
                .birth(reqDto.birth())
                .phoneNumber(reqDto.phoneNumber())
                .sex(reqDto.sex())
                .role(Role.APPLICANT)
                .build();

        memberRepository.save(newMember);

        commonCodeService.deleteCode(code);

        return newMember.getRole();
    }
}
