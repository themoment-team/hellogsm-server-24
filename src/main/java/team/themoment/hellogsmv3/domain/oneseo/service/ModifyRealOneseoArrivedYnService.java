package team.themoment.hellogsmv3.domain.oneseo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.application.type.ScreeningCategory;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.member.service.MemberService;
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
    public void execute(Long memberId) {
        Member member = memberService.findByIdOrThrow(memberId);

        Oneseo oneseo = oneseoRepository.findByMember(member)
                .orElseThrow(() -> new ExpectedException("해당 지원자의 원서를 찾을 수 없습니다. member ID: " + memberId, HttpStatus.NOT_FOUND));

        Oneseo modifiedOneseo = switchRealOneseoArrivedYn(oneseo);
        assignSubmitCode(modifiedOneseo);

        oneseoRepository.save(modifiedOneseo);
    }

    private Oneseo switchRealOneseoArrivedYn(Oneseo oneseo) {
        return Oneseo.builder()
                .id(oneseo.getId())
                .member(oneseo.getMember())
                .oneseoSubmitCode(oneseo.getOneseoSubmitCode())
                .desiredMajors(oneseo.getDesiredMajors())
                .realOneseoArrivedYn(oneseo.getRealOneseoArrivedYn() == YesNo.YES ? YesNo.NO : YesNo.YES)
                .finalSubmittedYn(oneseo.getFinalSubmittedYn())
                .wantedScreening(oneseo.getWantedScreening())
                .appliedScreening(oneseo.getAppliedScreening())
                .build();
    }

    private void assignSubmitCode(Oneseo oneseo) {
        if (oneseo.getRealOneseoArrivedYn().equals(YesNo.NO)) {
            oneseo.setOneseoSubmitCode(null);
            return;
        }

        Integer maxSubmitCodeNumber = oneseoRepository.findMaxSubmitCodeByScreening(oneseo.getAppliedScreening());
        int newSubmitCodeNumber = (maxSubmitCodeNumber != null ? maxSubmitCodeNumber : 0) + 1;

        String submitCode;
        ScreeningCategory screeningCategory = oneseo.getAppliedScreening().getScreeningCategory();
        switch (screeningCategory) {
            case GENERAL -> submitCode = "A-" + newSubmitCodeNumber;
            case SPECIAL -> submitCode = "B-" + newSubmitCodeNumber;
            case EXTRA -> submitCode = "C-" + newSubmitCodeNumber;
            default -> throw new IllegalArgumentException("Unexpected value: " + screeningCategory);
        }

        oneseo.setOneseoSubmitCode(submitCode);
    }
}
