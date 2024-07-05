package team.themoment.hellogsmv3.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsmv3.domain.member.dto.request.ModifyMemberReqDto;
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

        Member modifiedMember = member.modifyMember(
                reqDto.name(),
                reqDto.birth(),
                reqDto.phoneNumber(),
                reqDto.sex()
        );
        memberRepository.save(modifiedMember);
    }
}
