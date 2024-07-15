package team.themoment.hellogsmv3.domain.oneseo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.member.service.MemberService;
import team.themoment.hellogsmv3.domain.oneseo.dto.request.AptitudeEvaluationScoreReqDto;
import team.themoment.hellogsmv3.domain.oneseo.entity.EntranceTestResult;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;
import team.themoment.hellogsmv3.domain.oneseo.repository.EntranceTestResultRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ModifyAptitudeEvaluationScoreService {

    private final MemberService memberService;
    private final OneseoRepository oneseoRepository;
    private final EntranceTestResultRepository entranceTestResultRepository;

    public void execute(Long memberId, AptitudeEvaluationScoreReqDto aptitudeEvaluationScoreReqDto) {
        Member member = memberService.findByIdOrThrow(memberId);

        Oneseo oneseo = oneseoRepository.findByMember(member)
                .orElseThrow(() -> new ExpectedException("해당 지원자의 원서를 찾을 수 없습니다. member ID: " + memberId, HttpStatus.NOT_FOUND));

        EntranceTestResult entranceTestResult = entranceTestResultRepository.findEntranceTestResultByOneseo(oneseo)
                .orElseThrow(() -> new ExpectedException("해당 지원자의 입학 시험 결과를 찾을 수 없습니다. member ID: " + memberId, HttpStatus.NOT_FOUND));

        EntranceTestResult modifiedEntranceTestResult = modifyAptitudeEvaluationScore(entranceTestResult, aptitudeEvaluationScoreReqDto.aptitudeEvaluationScore());

        entranceTestResultRepository.save(modifiedEntranceTestResult);
    }

    private EntranceTestResult modifyAptitudeEvaluationScore(EntranceTestResult entranceTestResult, BigDecimal aptitudeEvaluationScore) {
        return EntranceTestResult.builder()
                .id(entranceTestResult.getId())
                .oneseo(entranceTestResult.getOneseo())
                .entranceTestFactorsDetail(entranceTestResult.getEntranceTestFactorsDetail())
                .documentEvaluationScore(entranceTestResult.getDocumentEvaluationScore())
                .firstTestPassYn(entranceTestResult.getFirstTestPassYn())
                .aptitudeEvaluationScore(aptitudeEvaluationScore)
                .interviewScore(entranceTestResult.getInterviewScore())
                .secondTestPassYn(entranceTestResult.getSecondTestPassYn())
                .decidedMajor(entranceTestResult.getDecidedMajor())
                .build();
    }
}
