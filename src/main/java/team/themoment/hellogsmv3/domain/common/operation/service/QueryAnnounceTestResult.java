package team.themoment.hellogsmv3.domain.common.operation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.common.operation.dto.response.AnnounceTestResultResDto;
import team.themoment.hellogsmv3.domain.common.operation.entity.OperationTestResult;
import team.themoment.hellogsmv3.domain.common.operation.repo.OperationTestResultRepository;

@Service
@RequiredArgsConstructor
public class QueryAnnounceTestResult {

    private final OperationTestResultRepository operationTestResultRepository;

    public AnnounceTestResultResDto execute() {
        OperationTestResult testResult = operationTestResultRepository.getTestResult();

        return AnnounceTestResultResDto.builder()
                .firstTestResultAnnouncementYn(testResult.getFirstTestResultAnnouncementYn())
                .secondTestResultAnnouncementYn(testResult.getSecondTestResultAnnouncementYn())
                .build();
    }
}
