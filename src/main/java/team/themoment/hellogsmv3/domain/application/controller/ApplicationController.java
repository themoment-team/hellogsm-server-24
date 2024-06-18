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
import team.themoment.hellogsmv3.global.security.auth.AuthenticatedUserManager;

@RestController
@RequestMapping("/application/v3")
@RequiredArgsConstructor
public class ApplicationController {

    private final AuthenticatedUserManager manager;
    private final QueryApplicationByIdService queryApplicationByIdService;

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
}
