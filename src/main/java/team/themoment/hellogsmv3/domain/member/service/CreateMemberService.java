package team.themoment.hellogsmv3.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsmv3.domain.member.dto.CreateMemberReqDto;
import team.themoment.hellogsmv3.domain.member.entity.AuthenticationCode;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.member.entity.type.Role;
import team.themoment.hellogsmv3.domain.member.repo.MemberRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

import static team.themoment.hellogsmv3.domain.member.entity.type.Role.*;

@Service
@RequiredArgsConstructor
public class CreateMemberService {

    private final MemberRepository memberRepository;
    private final CommonCodeService commonCodeService;

    @Transactional
    public Role execute(CreateMemberReqDto reqDto, Long memberId) {

        commonCodeService.validateAndDelete(memberId, reqDto.code(), reqDto.phoneNumber());

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ExpectedException("존재하지 않는 지원자입니다. member ID: " + memberId, HttpStatus.NOT_FOUND));

        if (member.getRole().equals(APPLICANT))
            throw new ExpectedException("이미 회원가입 하였습니다.", HttpStatus.BAD_REQUEST);

        Member newMember = Member.builder()
                .id(member.getId())
                .email(member.getEmail())
                .authReferrerType(member.getAuthReferrerType())
                .name(reqDto.name())
                .birth(reqDto.birth())
                .phoneNumber(reqDto.phoneNumber())
                .sex(reqDto.sex())
                .role(APPLICANT)
                .build();

        memberRepository.save(newMember);

        return newMember.getRole();
    }
}
