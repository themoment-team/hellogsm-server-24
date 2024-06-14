package team.themoment.hellogsmv3.domain.application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.themoment.hellogsmv3.domain.application.dto.response.ApplicationListResDto;
import team.themoment.hellogsmv3.domain.application.dto.response.FoundApplicationResDto;
import team.themoment.hellogsmv3.domain.application.service.QueryAllApplicationService;
import team.themoment.hellogsmv3.domain.application.service.QueryApplicationByIdService;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;
import team.themoment.hellogsmv3.global.security.auth.AuthenticatedUserManager;

@RestController
@RequestMapping("/application/v3")
@RequiredArgsConstructor
public class ApplicationController {

    private final AuthenticatedUserManager manager;
    private final QueryApplicationByIdService queryApplicationByIdService;
    private final QueryAllApplicationService queryAllApplicationService;

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
}
