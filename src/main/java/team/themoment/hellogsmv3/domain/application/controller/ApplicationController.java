package team.themoment.hellogsmv3.domain.application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.themoment.hellogsmv3.domain.applicant.dto.response.AdmissionTicketsResDto;
import team.themoment.hellogsmv3.domain.application.dto.request.ApplicationStatusReqDto;
import team.themoment.hellogsmv3.domain.application.dto.response.FoundApplicationResDto;
import team.themoment.hellogsmv3.domain.application.service.QueryAdmissionTicketsService;
import team.themoment.hellogsmv3.domain.application.service.QueryApplicationByIdService;
import team.themoment.hellogsmv3.domain.application.service.UpdateApplicationStatusService;
import team.themoment.hellogsmv3.global.security.auth.AuthenticatedUserManager;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/application/v3")
@RequiredArgsConstructor
public class ApplicationController {

    private final AuthenticatedUserManager manager;
    private final QueryApplicationByIdService queryApplicationByIdService;
    private final UpdateApplicationStatusService updateApplicationStatusService;
    private final QueryAdmissionTicketsService queryAdmissionTicketsService;

    @GetMapping("/application/me")
    public ResponseEntity<FoundApplicationResDto> findMe() {
        FoundApplicationResDto foundApplicationResDto = queryApplicationByIdService.execute(manager.getId());
        return ResponseEntity.status(HttpStatus.OK).body(foundApplicationResDto);
    }

    @GetMapping("/application/{authenticationId}")
    public ResponseEntity<FoundApplicationResDto> findOne(@PathVariable("authenticationId") Long userId) {
        FoundApplicationResDto foundApplicationResDto = queryApplicationByIdService.execute(userId);
        return ResponseEntity.status(HttpStatus.OK).body(foundApplicationResDto);
    }

    @PutMapping("/status/{applicantId}")
    public ResponseEntity<Map<String, String>> updateStatus(
            @PathVariable Long applicantId,
            @RequestBody ApplicationStatusReqDto applicationStatusReqDto
    ) {
        updateApplicationStatusService.execute(applicantId, applicationStatusReqDto);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "수정되었습니다."));
    }

    @GetMapping("/admission-tickets")
    public ResponseEntity<List<AdmissionTicketsResDto>> findAdmissionTickets(
    ) {
        List<AdmissionTicketsResDto> admissionTicketsResDto = queryAdmissionTicketsService.execute();
        return ResponseEntity.status(HttpStatus.OK).body(admissionTicketsResDto);
    }
}
