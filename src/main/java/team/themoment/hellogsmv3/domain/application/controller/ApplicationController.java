package team.themoment.hellogsmv3.domain.application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.themoment.hellogsmv3.domain.application.dto.response.FoundApplicationResDto;
import team.themoment.hellogsmv3.domain.application.service.QueryApplicationByIdService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.themoment.hellogsmv3.domain.application.dto.request.ApplicationReqDto;
import team.themoment.hellogsmv3.domain.application.service.CreateApplicationService;
import team.themoment.hellogsmv3.domain.application.service.ModifyApplicationService;
import team.themoment.hellogsmv3.domain.application.service.UpdateFinalSubmissionService;
import team.themoment.hellogsmv3.global.common.response.ApiResponse;
import team.themoment.hellogsmv3.global.security.auth.AuthenticatedUserManager;

@RestController
@RequestMapping("/application/v3")
@RequiredArgsConstructor
public class ApplicationController {

    private final AuthenticatedUserManager manager;
    private final QueryApplicationByIdService queryApplicationByIdService;
    private final CreateApplicationService createApplicationService;
    private final ModifyApplicationService modifyApplicationService;
    private final UpdateFinalSubmissionService updateFinalSubmissionService;

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

    @PostMapping("/application/me")
    public ResponseEntity<ApiResponse> create(@RequestBody @Valid ApplicationReqDto reqDto) {
        createApplicationService.execute(reqDto, manager.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("생성되었습니다."));
    }

    @PutMapping("/application/me")
    public ResponseEntity<ApiResponse> modify(@RequestBody @Valid ApplicationReqDto reqDto) {
        modifyApplicationService.execute(reqDto, manager.getId(), false);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("수정되었습니다."));
    }

    @PutMapping("/application/{applicantId}")
    public ResponseEntity<ApiResponse> modifyOne(
            @RequestBody @Valid ApplicationReqDto reqDto,
            @PathVariable Long applicantId) {
        modifyApplicationService.execute(reqDto, applicantId, true);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("수정되었습니다."));
    }

    @PutMapping("/final-submit")
    public ResponseEntity<ApiResponse> finalSubmission() {
        updateFinalSubmissionService.execute(manager.getId());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("수정되었습니다."));
    }

}
