package team.themoment.hellogsmv3.domain.application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.themoment.hellogsmv3.domain.application.dto.response.ApplicationListResDto;
import team.themoment.hellogsmv3.domain.applicant.dto.response.AdmissionTicketsResDto;
import team.themoment.hellogsmv3.domain.application.dto.request.ApplicationStatusReqDto;
import team.themoment.hellogsmv3.domain.application.dto.response.FoundApplicationResDto;
import team.themoment.hellogsmv3.domain.application.service.DeleteApplicationByAuthIdService;
import team.themoment.hellogsmv3.domain.application.service.QueryAllApplicationService;
import team.themoment.hellogsmv3.domain.application.service.QueryAdmissionTicketsService;
import team.themoment.hellogsmv3.domain.application.service.QueryApplicationByIdService;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;
import jakarta.validation.Valid;
import team.themoment.hellogsmv3.domain.application.dto.request.ApplicationReqDto;
import team.themoment.hellogsmv3.domain.application.service.CreateApplicationService;
import team.themoment.hellogsmv3.domain.application.service.ModifyApplicationService;
import team.themoment.hellogsmv3.domain.application.service.UpdateFinalSubmissionService;
import team.themoment.hellogsmv3.global.common.response.CommonApiMessageResponse;
import team.themoment.hellogsmv3.domain.application.service.UpdateApplicationStatusService;
import team.themoment.hellogsmv3.global.security.auth.AuthenticatedUserManager;

import java.util.List;

@RestController
@RequestMapping("/application/v3")
@RequiredArgsConstructor
public class ApplicationController {

    private final AuthenticatedUserManager manager;
    private final QueryApplicationByIdService queryApplicationByIdService;
    private final QueryAllApplicationService queryAllApplicationService;
    private final CreateApplicationService createApplicationService;
    private final ModifyApplicationService modifyApplicationService;
    private final UpdateFinalSubmissionService updateFinalSubmissionService;
    private final UpdateApplicationStatusService updateApplicationStatusService;
    private final QueryAdmissionTicketsService queryAdmissionTicketsService;
    private final DeleteApplicationByAuthIdService deleteApplicationByAuthIdService;

    @GetMapping("/application/me")
    public FoundApplicationResDto findMe() {
        return queryApplicationByIdService.execute(manager.getId());
    }

    @GetMapping("/application/{applicantId}")
    public FoundApplicationResDto findOne(@PathVariable("applicantId") Long applicantId) {
        return queryApplicationByIdService.execute(applicantId);
    }

    @GetMapping("/application/all")
    public ApplicationListResDto findAll(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size
    ) {
        if (page < 0 || size < 0)
            throw new ExpectedException("page, size는 0 이상만 가능합니다", HttpStatus.BAD_REQUEST);
        return queryAllApplicationService.execute(page, size);
    }

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


    @PutMapping("/status/{applicantId}")
    public CommonApiMessageResponse updateStatus(
            @PathVariable Long applicantId,
            @RequestBody ApplicationStatusReqDto applicationStatusReqDto
    ) {
        updateApplicationStatusService.execute(applicantId, applicationStatusReqDto);
        return CommonApiMessageResponse.success("수정되었습니다.");
    }

    @GetMapping("/admission-tickets")
    public List<AdmissionTicketsResDto> findAdmissionTickets(
    ) {
        return queryAdmissionTicketsService.execute();
    }

    @DeleteMapping("/application/me")
    public CommonApiMessageResponse deleteApplication(
    ) {
        deleteApplicationByAuthIdService.execute(manager.getId());
        return CommonApiMessageResponse.success("삭제되었습니다.");
    }
}
