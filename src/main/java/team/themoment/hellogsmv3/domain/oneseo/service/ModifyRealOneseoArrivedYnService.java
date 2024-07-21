package team.themoment.hellogsmv3.domain.oneseo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.member.service.MemberService;
import team.themoment.hellogsmv3.domain.oneseo.dto.response.ArrivedStatusResDto;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

@Service
@RequiredArgsConstructor
public class ModifyRealOneseoArrivedYnService {

    private final MemberService memberService;
    private final OneseoRepository oneseoRepository;

    @Transactional
    public ArrivedStatusResDto execute(Long memberId) {
        Member member = memberService.findByIdOrThrow(memberId);

        Oneseo oneseo = oneseoRepository.findByMember(member)
                .orElseThrow(() -> new ExpectedException("해당 지원자의 원서를 찾을 수 없습니다. member ID: " + memberId, HttpStatus.NOT_FOUND));

        Oneseo modifiedOneseo = oneseoRepository.save(switchRealOneseoArrivedYn(oneseo));

        return new ArrivedStatusResDto(modifiedOneseo.getRealOneseoArrivedYn());
    }

    private Oneseo switchRealOneseoArrivedYn(Oneseo oneseo) {
        return Oneseo.builder()
                .id(oneseo.getId())
                .member(oneseo.getMember())
                .desiredMajors(oneseo.getDesiredMajors())
                .oneseoSubmitCode(oneseo.getOneseoSubmitCode())
                .realOneseoArrivedYn(oneseo.getRealOneseoArrivedYn() == YesNo.YES ? YesNo.NO : YesNo.YES)
                .appliedScreening(oneseo.getAppliedScreening())
                .build();
    }
}
