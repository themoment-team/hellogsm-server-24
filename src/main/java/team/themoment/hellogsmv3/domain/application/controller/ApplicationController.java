package team.themoment.hellogsmv3.domain.application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.themoment.hellogsmv3.domain.application.dto.response.ApplicationListResDto;
import team.themoment.hellogsmv3.domain.applicant.dto.response.AdmissionTicketsResDto;
import team.themoment.hellogsmv3.domain.application.dto.request.ApplicationStatusReqDto;
import team.themoment.hellogsmv3.domain.application.dto.response.FoundApplicationResDto;
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

    @GetMapping("/application/me")
    public ResponseEntity<FoundApplicationResDto> findMe() {
        FoundApplicationResDto foundApplicationResDto = queryApplicationByIdService.execute(manager.getId());
        return ResponseEntity.status(HttpStatus.OK).body(foundApplicationResDto);
    }

    @GetMapping("/application/{applicantId}")
    public ResponseEntity<FoundApplicationResDto> findOne(@PathVariable("applicantId") Long applicantId) {
        FoundApplicationResDto foundApplicationResDto = queryApplicationByIdService.execute(applicantId);
        return ResponseEntity.status(HttpStatus.OK).body(foundApplicationResDto);
    }

    @GetMapping("/application/all")
    public ResponseEntity<ApplicationListResDto> findAll(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size
    ) {
        if (page < 0 || size < 0)
            throw new ExpectedException("page, size는 0 이상만 가능합니다", HttpStatus.BAD_REQUEST);
        ApplicationListResDto applicationListResDto = queryAllApplicationService.execute(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(applicationListResDto);
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
    public ResponseEntity<List<AdmissionTicketsResDto>> findAdmissionTickets(
    ) {
        List<AdmissionTicketsResDto> admissionTicketsResDto = queryAdmissionTicketsService.execute();
        return ResponseEntity.status(HttpStatus.OK).body(admissionTicketsResDto);
    }
}
