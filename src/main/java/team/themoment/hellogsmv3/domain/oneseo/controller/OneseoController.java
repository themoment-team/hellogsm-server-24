package team.themoment.hellogsmv3.domain.oneseo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.themoment.hellogsmv3.domain.oneseo.dto.request.OneseoReqDto;
import team.themoment.hellogsmv3.domain.oneseo.service.CreateOneseoService;
import team.themoment.hellogsmv3.domain.oneseo.service.ModifyOneseoService;
import team.themoment.hellogsmv3.domain.oneseo.service.UpdateFinalSubmissionService;
import team.themoment.hellogsmv3.global.common.handler.annotation.AuthRequest;
import team.themoment.hellogsmv3.global.common.response.CommonApiResponse;

@RestController
@RequestMapping("/oneseo/v3")
@RequiredArgsConstructor
public class OneseoController {

    private final CreateOneseoService createOneseoService;
    private final ModifyOneseoService modifyOneseoService;
    private final UpdateFinalSubmissionService updateFinalSubmissionService;

    @PostMapping("/oneseo/me")
    public CommonApiResponse create(
            @RequestBody @Valid OneseoReqDto reqDto,
            @AuthRequest Long memberId
    ) {
        createOneseoService.execute(reqDto, memberId);
        return CommonApiResponse.created("생성되었습니다.");
    }

    @PutMapping("/oneseo/me")
    public CommonApiResponse modify(
            @RequestBody @Valid OneseoReqDto reqDto,
            @AuthRequest Long memberId
    ) {
        modifyOneseoService.execute(reqDto, memberId, false);
        return CommonApiResponse.success("수정되었습니다.");
    }

    @PutMapping("/oneseo/{memberId}")
    public CommonApiResponse modifyByAdmin(
            @RequestBody @Valid OneseoReqDto reqDto,
            @PathVariable("memberId") Long memberId
    ) {
        modifyOneseoService.execute(reqDto, memberId, true);
        return CommonApiResponse.success("수정되었습니다.");
    }

    @PatchMapping("/final-submit")
    public CommonApiResponse finalSubmission(
            @AuthRequest Long memberId
    ) {
        updateFinalSubmissionService.execute(memberId);
        return CommonApiResponse.success("수정되었습니다.");
    }

}
