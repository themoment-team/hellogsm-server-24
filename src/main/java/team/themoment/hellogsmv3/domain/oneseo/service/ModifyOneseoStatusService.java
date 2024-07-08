package team.themoment.hellogsmv3.domain.oneseo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.member.service.MemberService;
import team.themoment.hellogsmv3.domain.oneseo.dto.OneseoStatusReqDto;
import team.themoment.hellogsmv3.domain.oneseo.entity.EntranceTestResult;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;
import team.themoment.hellogsmv3.domain.oneseo.repository.EntranceTestResultRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

@Service
@RequiredArgsConstructor
public class ModifyOneseoStatusService {

    private final OneseoRepository oneseoRepository;
    private final EntranceTestResultRepository entranceTestResultRepository;
    private final MemberService memberService;

    public void execute(Long memberId, OneseoStatusReqDto oneseoStatusReqDto) {
        Member member = memberService.findById(memberId);
        Oneseo oneseo = oneseoRepository.findByMember(member)
                .orElseThrow(() -> new ExpectedException("해당 지원자의 원서를 찾을 수 없습니다. member ID : " + memberId, HttpStatus.NOT_FOUND));
        EntranceTestResult entranceTestResult = entranceTestResultRepository.findByOneseo(oneseo)
                .orElseThrow(() -> new ExpectedException("해당 지원자의 원서로 입학 시험 결과를 찾을 수 없습니다. member ID : " + oneseo.getMember().getId(), HttpStatus.NOT_FOUND));

        saveModifiedOneseo(oneseo, oneseoStatusReqDto);
        saveModifiedEntranceTestResult(entranceTestResult, oneseoStatusReqDto);
    }

    private void saveModifiedOneseo(Oneseo oneseo, OneseoStatusReqDto oneseoStatusReqDto) {
        Oneseo modifiedOneseo = Oneseo.builder()
                .id(oneseo.getId())
                .member(oneseo.getMember())
                .oneseoSubmitCode(oneseo.getOneseoSubmitCode())
                .desiredMajors(oneseo.getDesiredMajors())
                .realOneseoArrivedYn(oneseoStatusReqDto.realOneseoArrivedYn())
                .finalSubmittedYn(oneseoStatusReqDto.finalSubmittedYn())
                .appliedScreening(oneseoStatusReqDto.appliedScreening())
                .build();

        oneseoRepository.save(modifiedOneseo);
    }

    private void saveModifiedEntranceTestResult(EntranceTestResult entranceTestResult, OneseoStatusReqDto oneseoStatusReqDto) {
        EntranceTestResult modifiedEntranceTestResult = EntranceTestResult.builder()
                .id(entranceTestResult.getId())
                .oneseo(entranceTestResult.getOneseo())
                .firstTestResultScore(entranceTestResult.getFirstTestResultScore())
                .firstTestPassYn(oneseoStatusReqDto.firstTestPassYn())
                .secondTestPassYn(oneseoStatusReqDto.secondTestPassYn())
                .secondTestResultScore(oneseoStatusReqDto.secondTestResultScore())
                .interviewScore(oneseoStatusReqDto.interviewScore())
                .decidedMajor(oneseoStatusReqDto.decidedMajor())
                .build();

        entranceTestResultRepository.save(modifiedEntranceTestResult);
    }
}
