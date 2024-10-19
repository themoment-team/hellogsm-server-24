package team.themoment.hellogsmv3.domain.common.operation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.themoment.hellogsmv3.domain.common.operation.dto.response.AnnounceTestResultResDto;
import team.themoment.hellogsmv3.domain.common.operation.service.QueryAnnounceTestResultService;

@Tag(name = "operation API", description = "결과 발표 관련 operation API입니다.")
@RestController
@RequestMapping("/operation/v3")
@RequiredArgsConstructor
public class OperationTestResultController {

    private final QueryAnnounceTestResultService queryAnnounceTestResultService;

    @Operation(summary = "결과 발표 여부 조회", description = "현재 결과 발표 여부를 조회합니다.")
    @GetMapping("/operation/status")
    public AnnounceTestResultResDto queryTestResult() {
        return queryAnnounceTestResultService.execute();
    }

}
