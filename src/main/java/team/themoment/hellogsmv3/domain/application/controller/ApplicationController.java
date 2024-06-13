package team.themoment.hellogsmv3.domain.application.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.themoment.hellogsmv3.domain.application.dto.request.ApplicationReqDto;
import team.themoment.hellogsmv3.domain.application.service.CreateApplicationService;
import team.themoment.hellogsmv3.domain.application.service.ModifyApplicationService;
import team.themoment.hellogsmv3.global.common.response.ApiResponse;
import team.themoment.hellogsmv3.global.security.auth.AuthenticatedUserManager;

import java.util.Map;

@RestController
@RequestMapping("/application/v3")
@RequiredArgsConstructor
public class ApplicationController {

    private final AuthenticatedUserManager manager;
    private final CreateApplicationService createApplicationService;
    private final ModifyApplicationService modifyApplicationService;

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

    @PutMapping("/application/{userId}")
    public ResponseEntity<ApiResponse> modifyOne(
            @RequestBody @Valid ApplicationReqDto reqDto,
            @PathVariable Long userId) {
        modifyApplicationService.execute(reqDto, userId, true);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("수정되었습니다."));
    }

}
