package team.themoment.hellogsmv3.domain.common.operation.service;

import static team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo.*;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team.themoment.hellogsmv3.domain.common.operation.entity.OperationTestResult;
import team.themoment.hellogsmv3.domain.common.operation.repository.OperationTestResultRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.EntranceTestResultRepository;
import team.themoment.sdk.exception.ExpectedException;

@Service
@RequiredArgsConstructor
public class AnnounceFirstTestResultService {

    private final OperationTestResultRepository operationTestResultRepository;
    private final EntranceTestResultRepository entranceTestResultRepository;

    @Transactional
    public void execute() {
        validateAllFirstTestResultsExist();

        OperationTestResult testResult = operationTestResultRepository.findTestResult()
                .orElseThrow(() -> new ExpectedException("시험 운영 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
        validateDuplicateAnnouncement(testResult);

        testResult.announceFirstTestResult();

        operationTestResultRepository.save(testResult);
    }

    private void validateAllFirstTestResultsExist() {
        if (entranceTestResultRepository.existsByFirstTestPassYnIsNull()) {
            throw new ExpectedException("아직 입력되지 않은 1차 시험 결과가 있습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateDuplicateAnnouncement(OperationTestResult testResult) {
        if (testResult.getFirstTestResultAnnouncementYn().equals(YES)) {
            throw new ExpectedException("이미 1차 결과를 발표했습니다.", HttpStatus.BAD_REQUEST);
        }
    }
}
