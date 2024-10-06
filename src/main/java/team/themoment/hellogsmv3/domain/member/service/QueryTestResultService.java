package team.themoment.hellogsmv3.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.member.dto.response.FoundMemberTestResDto;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.oneseo.entity.EntranceTestResult;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;
import team.themoment.hellogsmv3.domain.oneseo.service.OneseoService;

@Service
@RequiredArgsConstructor
public class QueryTestResultService {

    private final MemberService memberService;
    private final OneseoService oneseoService;

    public FoundMemberTestResDto execute(Long memberId) {
        Member member = memberService.findByIdOrThrow(memberId);
        Oneseo oneseo = oneseoService.findByMemberOrThrow(member);

        EntranceTestResult entranceTestResult = oneseo.getEntranceTestResult();

        return new FoundMemberTestResDto(
                entranceTestResult.getFirstTestPassYn(),
                entranceTestResult.getSecondTestPassYn(),
                oneseo.getDecidedMajor()
        );
    }
}
