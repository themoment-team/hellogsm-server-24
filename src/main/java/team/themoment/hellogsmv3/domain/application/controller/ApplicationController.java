package team.themoment.hellogsmv3.domain.application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team.themoment.hellogsmv3.domain.application.dto.response.FoundApplicationResDto;
import team.themoment.hellogsmv3.domain.application.dto.response.SearchApplicationsResDto;
import team.themoment.hellogsmv3.domain.application.dto.response.ApplicationListResDto;
import team.themoment.hellogsmv3.domain.applicant.dto.response.AdmissionTicketsResDto;
import team.themoment.hellogsmv3.domain.application.dto.request.ApplicationStatusReqDto;
import team.themoment.hellogsmv3.domain.application.service.DeleteApplicationByAuthIdService;
import team.themoment.hellogsmv3.domain.application.service.QueryAllApplicationService;
import team.themoment.hellogsmv3.domain.application.service.QueryAdmissionTicketsService;
import team.themoment.hellogsmv3.domain.application.service.QueryApplicationByIdService;
import team.themoment.hellogsmv3.domain.application.service.SearchApplicationService;
import team.themoment.hellogsmv3.domain.application.type.SearchTag;
import team.themoment.hellogsmv3.global.common.handler.annotation.AuthRequest;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;
import jakarta.validation.Valid;
import team.themoment.hellogsmv3.domain.application.dto.request.ApplicationReqDto;
import team.themoment.hellogsmv3.domain.application.service.CreateApplicationService;
import team.themoment.hellogsmv3.domain.application.service.ModifyApplicationService;
import team.themoment.hellogsmv3.domain.application.service.UpdateFinalSubmissionService;
import team.themoment.hellogsmv3.global.common.response.CommonApiMessageResponse;
import team.themoment.hellogsmv3.domain.application.service.UpdateApplicationStatusService;


import java.util.List;

@RestController
@RequestMapping("/application/v3")
@RequiredArgsConstructor
public class ApplicationController {

    private final QueryApplicationByIdService queryApplicationByIdService;
    private final SearchApplicationService searchApplicationService;
    private final QueryAllApplicationService queryAllApplicationService;
    private final CreateApplicationService createApplicationService;
    private final ModifyApplicationService modifyApplicationService;
    private final UpdateFinalSubmissionService updateFinalSubmissionService;
    private final UpdateApplicationStatusService updateApplicationStatusService;
    private final QueryAdmissionTicketsService queryAdmissionTicketsService;
    private final DeleteApplicationByAuthIdService deleteApplicationByAuthIdService;

    @GetMapping("/application/me")
    public FoundApplicationResDto findMe(@AuthRequest Long authId) {
        return queryApplicationByIdService.execute(authId);
    }

    @GetMapping("/application/{applicantId}")
    public FoundApplicationResDto findOne(@PathVariable("applicantId") Long applicantId) {
        return queryApplicationByIdService.execute(applicantId);
    }

    @GetMapping("/application/search")
    public SearchApplicationsResDto search(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(name = "tag", required = false) String tag,
            @RequestParam(name = "keyword", required = false) String keyword
    ) {
        validatePageAndSize(page, size);
        SearchTag searchTag = validateAndConvertTag(tag);
        return searchApplicationService.execute(page, size, searchTag, keyword);
    }

    @GetMapping("/application/all")
    public ApplicationListResDto findAll(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size
    ) {
        validatePageAndSize(page, size);
        return queryAllApplicationService.execute(page, size);
    }

    @PostMapping("/application/me")
    public CommonApiMessageResponse create(@RequestBody @Valid ApplicationReqDto reqDto,
                                           @AuthRequest Long authId) {
        createApplicationService.execute(reqDto, authId);
        return CommonApiMessageResponse.created("생성되었습니다.");
    }

    @PutMapping("/application/me")
    public CommonApiMessageResponse modify(@RequestBody @Valid ApplicationReqDto reqDto,
                                           @AuthRequest Long authId) {
        modifyApplicationService.execute(reqDto, authId, false);
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
    public CommonApiMessageResponse finalSubmission(@AuthRequest Long authId) {
        updateFinalSubmissionService.execute(authId);
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
    public List<AdmissionTicketsResDto> findAdmissionTickets() {
        return queryAdmissionTicketsService.execute();
    }

    @DeleteMapping("/application/me")
    public CommonApiMessageResponse deleteApplication(@AuthRequest Long authId) {
        deleteApplicationByAuthIdService.execute(authId);
        return CommonApiMessageResponse.success("삭제되었습니다.");
    }

    private void validatePageAndSize(Integer page, Integer size) {
        if (page < 0 || size < 0)
            throw new ExpectedException("page, size는 0 이상만 가능합니다", HttpStatus.BAD_REQUEST);
    }

    private SearchTag validateAndConvertTag(String tag) {
        if (tag == null) {
            return null;
        }
        try {
            return SearchTag.valueOf(tag);
        } catch (IllegalArgumentException e) {
            throw new ExpectedException("유효하지 않은 tag입니다", HttpStatus.BAD_REQUEST);
        }
    }
}
