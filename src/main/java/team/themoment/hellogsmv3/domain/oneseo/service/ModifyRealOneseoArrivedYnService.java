package team.themoment.hellogsmv3.domain.oneseo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.application.type.ScreeningCategory;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.member.service.MemberService;
import team.themoment.hellogsmv3.domain.oneseo.dto.response.ArrivedStatusResDto;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoRepository;

@Service
@RequiredArgsConstructor
public class ModifyRealOneseoArrivedYnService {

    private final MemberService memberService;
    private final OneseoService oneseoService;
    private final OneseoRepository oneseoRepository;

    @Transactional
    public ArrivedStatusResDto execute(Long memberId) {
        Member member = memberService.findByIdOrThrow(memberId);

        Oneseo oneseo = oneseoService.findByMemberOrThrow(member);

        oneseo.switchRealOneseoArrivedYn();
        assignSubmitCode(oneseo);
        Oneseo modifiedOneseo = oneseoRepository.save(oneseo);

        return new ArrivedStatusResDto(modifiedOneseo.getRealOneseoArrivedYn());
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
