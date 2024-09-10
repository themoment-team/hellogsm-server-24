package team.themoment.hellogsmv3.domain.common.date.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import team.themoment.hellogsmv3.domain.common.date.dto.DateResDto;
import team.themoment.hellogsmv3.domain.common.date.service.QueryDateService;

@RestController
@RequiredArgsConstructor
public class DateController {

    private final QueryDateService queryDateService;

    @Operation(summary = "일정 조회", description = "FE에서 페이지 트리거가 되는 date를 반환합니다.")
    @GetMapping("/date")
    public DateResDto getDate() {
        return queryDateService.execute();
    }
}
