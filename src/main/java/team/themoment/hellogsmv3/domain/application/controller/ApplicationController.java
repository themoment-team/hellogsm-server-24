package team.themoment.hellogsmv3.domain.application.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.themoment.hellogsmv3.domain.application.dto.request.ApplicationReqDto;
import team.themoment.hellogsmv3.domain.application.service.CreateApplicationService;
import team.themoment.hellogsmv3.domain.application.service.ModifyApplicationService;
import team.themoment.hellogsmv3.domain.application.service.UpdateFinalSubmissionService;
import team.themoment.hellogsmv3.global.security.auth.AuthenticatedUserManager;

import java.util.Map;

@RestController
@RequestMapping("/application/v3")
@RequiredArgsConstructor
public class ApplicationController {

    private final AuthenticatedUserManager manager;
    private final CreateApplicationService createApplicationService;
    private final ModifyApplicationService modifyApplicationService;
    private final UpdateFinalSubmissionService updateFinalSubmissionService;

    @PostMapping("/application/me")
    public ResponseEntity<Map<String, String>> create(@RequestBody @Valid ApplicationReqDto dto) {
        createApplicationService.execute(dto, manager.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "생성되었습니다"));
    }

    @PutMapping("/application/me")
    public ResponseEntity<Map<String, String>> modify(@RequestBody @Valid ApplicationReqDto dto) {
        modifyApplicationService.execute(dto, manager.getId(), false);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "수정되었습니다"));
    }

    @PutMapping("/application/{userId}")
    public ResponseEntity<Map<String, String>> modifyOne(
            @RequestBody @Valid ApplicationReqDto body,
            @PathVariable Long userId) {
        modifyApplicationService.execute(body, userId, true);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "수정되었습니다"));
    }

    @PutMapping("/final-submit")
    public ResponseEntity<Map<String, String>> finalSubmission() {
        updateFinalSubmissionService.execute(manager.getId());
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "수정되었습니다"));
    }

}
