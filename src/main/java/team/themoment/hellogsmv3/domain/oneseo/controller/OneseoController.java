package team.themoment.hellogsmv3.domain.oneseo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.themoment.hellogsmv3.domain.oneseo.dto.OneseoStatusReqDto;
import team.themoment.hellogsmv3.domain.oneseo.service.ModifyOneseoStatusService;
import team.themoment.hellogsmv3.global.common.response.CommonApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oneseo/v3")
public class OneseoController {

    private final ModifyOneseoStatusService modifyOneseoStatusService;

    @PutMapping("/status/{memberId}")
    public CommonApiResponse modifyOneseoStatus(
            @PathVariable Long memberId,
            @RequestBody OneseoStatusReqDto oneseoStatusReqDto
    ) {
        modifyOneseoStatusService.execute(memberId, oneseoStatusReqDto);
        return CommonApiResponse.success("수정되었습니다.");
    }
}
