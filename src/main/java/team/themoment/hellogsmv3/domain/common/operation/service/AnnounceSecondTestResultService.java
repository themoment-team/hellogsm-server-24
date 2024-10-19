package team.themoment.hellogsmv3.domain.common.operation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsmv3.domain.common.operation.entity.OperationTestResult;
import team.themoment.hellogsmv3.domain.common.operation.repo.OperationTestResultRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.EntranceTestResultRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;
import team.themoment.hellogsmv3.global.security.data.ScheduleEnvironment;

import java.time.LocalDateTime;

import static team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo.YES;

@Service
@RequiredArgsConstructor
public class AnnounceSecondTestResultService {

    private final OperationTestResultRepository operationTestResultRepository;
    private final EntranceTestResultRepository entranceTestResultRepository;
    private final ScheduleEnvironment scheduleEnv;

    @Transactional
    public void execute() {
        validateSecondTestResultAnnouncementPeriod();

        OperationTestResult testResult = operationTestResultRepository.findTestResult();
        validateDuplicateAnnouncement(testResult);

        testResult.announceSecondTestResult();

        operationTestResultRepository.save(testResult);
    }

    private void validateSecondTestResultAnnouncementPeriod() {
        if (LocalDateTime.now().isBefore(scheduleEnv.finalResultsAnnouncement()) || entranceTestResultRepository.existsBySecondTestPassYnIsNull()) {
            throw new ExpectedException("2차 결과 발표 기간 이전에 발표 여부를 수정할 수 없습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateDuplicateAnnouncement(OperationTestResult testResult) {
        if (testResult.getSecondTestResultAnnouncementYn().equals(YES)) {
            throw new ExpectedException("이미 2차 결과를 발표했습니다.", HttpStatus.BAD_REQUEST);
        }
    }

}
