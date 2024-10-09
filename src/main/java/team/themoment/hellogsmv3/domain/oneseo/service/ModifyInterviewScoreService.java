package team.themoment.hellogsmv3.domain.oneseo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.member.service.MemberService;
import team.themoment.hellogsmv3.domain.oneseo.dto.request.InterviewScoreReqDto;
import team.themoment.hellogsmv3.domain.oneseo.entity.EntranceTestResult;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;
import team.themoment.hellogsmv3.domain.oneseo.repository.EntranceTestResultRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ModifyInterviewScoreService {

    private final MemberService memberService;
    private final OneseoService oneseoService;
    private final EntranceTestResultRepository entranceTestResultRepository;

    @Transactional
    public void execute(Long memberId, InterviewScoreReqDto reqDto) {
        Member member = memberService.findByIdOrThrow(memberId);
        Oneseo oneseo = oneseoService.findByMemberOrThrow(member);

        EntranceTestResult entranceTestResult = oneseo.getEntranceTestResult();
        OneseoService.isBeforeSecondTest(entranceTestResult.getSecondTestPassYn());

        BigDecimal interviewScore = reqDto.interviewScore();
        OneseoService.validateEvaluationScore(interviewScore);

        entranceTestResult.modifyInterviewScore(interviewScore);
        entranceTestResultRepository.save(entranceTestResult);
    }

}
