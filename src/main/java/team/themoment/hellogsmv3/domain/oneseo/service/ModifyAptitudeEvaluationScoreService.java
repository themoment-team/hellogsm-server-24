package team.themoment.hellogsmv3.domain.oneseo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.member.service.MemberService;
import team.themoment.hellogsmv3.domain.oneseo.dto.request.AptitudeEvaluationScoreReqDto;
import team.themoment.hellogsmv3.domain.oneseo.entity.EntranceTestResult;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;
import team.themoment.hellogsmv3.domain.oneseo.repository.EntranceTestResultRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ModifyAptitudeEvaluationScoreService {

    private final MemberService memberService;
    private final OneseoService oneseoService;
    private final EntranceTestResultRepository entranceTestResultRepository;

    public void execute(Long memberId, AptitudeEvaluationScoreReqDto aptitudeEvaluationScoreReqDto) {
        Member member = memberService.findByIdOrThrow(memberId);
        Oneseo oneseo = oneseoService.findByMemberOrThrow(member);

        EntranceTestResult entranceTestResult = oneseo.getEntranceTestResult();
        OneseoService.isBeforeSecondTest(entranceTestResult.getSecondTestPassYn());

        BigDecimal aptitudeEvaluationScore = aptitudeEvaluationScoreReqDto.aptitudeEvaluationScore();
        entranceTestResult.modifyAptitudeEvaluationScore(aptitudeEvaluationScore);

        entranceTestResultRepository.save(entranceTestResult);
    }
}
