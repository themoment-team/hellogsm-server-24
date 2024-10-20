package team.themoment.hellogsmv3.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.common.operation.entity.OperationTestResult;
import team.themoment.hellogsmv3.domain.common.operation.repo.OperationTestResultRepository;
import team.themoment.hellogsmv3.domain.member.dto.response.FoundMemberSecondTestResDto;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.oneseo.entity.EntranceTestResult;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;
import team.themoment.hellogsmv3.domain.oneseo.repository.EntranceTestResultRepository;
import team.themoment.hellogsmv3.domain.oneseo.service.OneseoService;
import team.themoment.hellogsmv3.global.security.data.ScheduleEnvironment;

import java.time.LocalDateTime;

import static team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo.NO;

@Service
@RequiredArgsConstructor
public class QuerySecondTestResultService {

    private final MemberService memberService;
    private final OneseoService oneseoService;
    private final ScheduleEnvironment scheduleEnv;
    private final EntranceTestResultRepository entranceTestResultRepository;
    private final OperationTestResultRepository operationTestResultRepository;

    public FoundMemberSecondTestResDto execute(Long memberId) {
        Member member = memberService.findByIdOrThrow(memberId);
        Oneseo oneseo = oneseoService.findByMemberOrThrow(member);

        // no content response status
        if (validateSecondTestResultAnnouncement()) return null;

        EntranceTestResult entranceTestResult = oneseo.getEntranceTestResult();
        return new FoundMemberSecondTestResDto(
                entranceTestResult.getSecondTestPassYn(),
                oneseo.getDecidedMajor()
        );
    }

    private boolean validateSecondTestResultAnnouncement() {
        OperationTestResult testResult = operationTestResultRepository.findTestResult();

        return testResult.getSecondTestResultAnnouncementYn().equals(NO) ||
                LocalDateTime.now().isBefore(scheduleEnv.firstResultsAnnouncement()) ||
                entranceTestResultRepository.existsByFirstTestPassYnIsNull();
    }
}
