package team.themoment.hellogsmv3.domain.application.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.themoment.hellogsmv3.domain.application.dto.request.ApplicationReqDto;
import team.themoment.hellogsmv3.domain.application.service.CreateApplicationService;
import team.themoment.hellogsmv3.domain.application.service.ModifyApplicationService;
import team.themoment.hellogsmv3.domain.application.service.UpdateFinalSubmissionService;
import team.themoment.hellogsmv3.global.common.response.CommonApiMessageResponse;
import team.themoment.hellogsmv3.global.security.auth.AuthenticatedUserManager;

@RestController
@RequestMapping("/application/v3")
@RequiredArgsConstructor
public class ApplicationController {

    private final AuthenticatedUserManager manager;
    private final CreateApplicationService createApplicationService;
    private final ModifyApplicationService modifyApplicationService;
    private final UpdateFinalSubmissionService updateFinalSubmissionService;

    @PostMapping("/application/me")
    public CommonApiMessageResponse create(@RequestBody @Valid ApplicationReqDto reqDto) {
        createApplicationService.execute(reqDto, manager.getId());
        return CommonApiMessageResponse.created("생성되었습니다.");
    }

    @PutMapping("/application/me")
    public CommonApiMessageResponse modify(@RequestBody @Valid ApplicationReqDto reqDto) {
        modifyApplicationService.execute(reqDto, manager.getId(), false);
        return CommonApiMessageResponse.success("수정되었습니다.");
    }

    @PutMapping("/application/{applicantId}")
    public CommonApiMessageResponse modifyOne(
            @RequestBody @Valid ApplicationReqDto reqDto,
            @PathVariable Long applicantId) {
        modifyApplicationService.execute(reqDto, applicantId, true);
        return CommonApiMessageResponse.success("수정되었습니다.");
    }

    @PutMapping("/final-submit")
    public CommonApiMessageResponse finalSubmission() {
        updateFinalSubmissionService.execute(manager.getId());
        return CommonApiMessageResponse.success("수정되었습니다.");
    }

}
