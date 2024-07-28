package team.themoment.hellogsmv3.domain.oneseo.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.*;
import team.themoment.hellogsmv3.domain.oneseo.dto.request.*;
import team.themoment.hellogsmv3.domain.application.type.ScreeningCategory;
import team.themoment.hellogsmv3.domain.oneseo.dto.response.AdmissionTicketsResDto;
import team.themoment.hellogsmv3.domain.oneseo.dto.response.ArrivedStatusResDto;
import team.themoment.hellogsmv3.domain.oneseo.service.*;
import team.themoment.hellogsmv3.domain.oneseo.dto.response.SearchOneseosResDto;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.GraduationType;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo;
import team.themoment.hellogsmv3.domain.oneseo.service.CreateOneseoService;
import team.themoment.hellogsmv3.domain.oneseo.service.ModifyOneseoService;
import team.themoment.hellogsmv3.domain.oneseo.service.SearchOneseoService;
import team.themoment.hellogsmv3.domain.oneseo.dto.response.MockScoreResDto;
import team.themoment.hellogsmv3.domain.oneseo.service.CalculateMockScoreService;
import team.themoment.hellogsmv3.domain.oneseo.dto.response.FoundOneseoResDto;
import team.themoment.hellogsmv3.domain.oneseo.service.QueryOneseoByIdService;
import team.themoment.hellogsmv3.domain.oneseo.service.UpdateFinalSubmissionService;
import team.themoment.hellogsmv3.domain.oneseo.service.ModifyRealOneseoArrivedYnService;
import team.themoment.hellogsmv3.global.common.handler.annotation.AuthRequest;
import team.themoment.hellogsmv3.global.common.response.CommonApiResponse;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/oneseo/v3")
@RequiredArgsConstructor
public class OneseoController {

    private final CreateOneseoService createOneseoService;
    private final ModifyOneseoService modifyOneseoService;
    private final ModifyRealOneseoArrivedYnService modifyRealOneseoArrivedYnService;
    private final ModifyAptitudeEvaluationScoreService modifyAptitudeEvaluationScoreService;
    private final ModifyInterviewScoreService modifyInterviewScoreService;
    private final DeleteOneseoService deleteOneseoService;
    private final QueryAdmissionTicketsService queryAdmissionTicketsService;
    private final DownloadExcelService downloadExcelService;
    private final SearchOneseoService searchOneseoService;
    private final QueryOneseoByIdService queryOneseoByIdService;
    private final UpdateFinalSubmissionService updateFinalSubmissionService;
    private final CalculateMockScoreService calculateMockScoreService;

    @PostMapping("/oneseo/me")
    public CommonApiResponse create(
            @RequestBody @Valid OneseoReqDto reqDto,
            @AuthRequest Long memberId
    ) {
        createOneseoService.execute(reqDto, memberId);
        return CommonApiResponse.created("생성되었습니다.");
    }

    @PutMapping("/oneseo/me")
    public CommonApiResponse modify(
            @RequestBody @Valid OneseoReqDto reqDto,
            @AuthRequest Long memberId
    ) {
        modifyOneseoService.execute(reqDto, memberId, false);
        return CommonApiResponse.success("수정되었습니다.");
    }

    @PutMapping("/oneseo/{memberId}")
    public CommonApiResponse modifyByAdmin(
            @RequestBody @Valid OneseoReqDto reqDto,
            @PathVariable("memberId") Long memberId
    ) {
        modifyOneseoService.execute(reqDto, memberId, true);
        return CommonApiResponse.success("수정되었습니다.");
    }

    @PatchMapping("/arrived-status/{memberId}")
    public ArrivedStatusResDto modifyArrivedStatus(
            @PathVariable Long memberId
    ) {
        return modifyRealOneseoArrivedYnService.execute(memberId);
    }

    @PatchMapping("/aptitude-score/{memberId}")
    public CommonApiResponse modifyAptitudeScore(
            @PathVariable Long memberId,
            @RequestBody @Valid AptitudeEvaluationScoreReqDto aptitudeEvaluationScoreReqDto
    ) {
        modifyAptitudeEvaluationScoreService.execute(memberId, aptitudeEvaluationScoreReqDto);
        return CommonApiResponse.success("수정되었습니다.");
    }

    @PatchMapping("/interview-score/{memberId}")
    public CommonApiResponse modifyInterviewScore(
            @PathVariable Long memberId,
            @RequestBody @Valid InterviewScoreReqDto reqDto
    ) {
        modifyInterviewScoreService.execute(memberId, reqDto);
        return CommonApiResponse.success("수정되었습니다.");
    }

    @GetMapping("/oneseo/search")
    public SearchOneseosResDto search(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(name = "testResultTag") TestResultTag testResultTag,
            @RequestParam(name = "screeningTag", required = false) ScreeningCategory screeningTag,
            @RequestParam(name = "isSubmitted", required = false) YesNo isSubmitted,
            @RequestParam(name = "keyword", required = false) String keyword
    ) {
        if (page < 0 || size < 0)
            throw new ExpectedException("page, size는 0 이상만 가능합니다", HttpStatus.BAD_REQUEST);
        return searchOneseoService.execute(page, size, testResultTag, screeningTag, isSubmitted, keyword);
    }

    @GetMapping("/oneseo/me")
    public FoundOneseoResDto find(
            @AuthRequest Long memberId
    ) {
        return queryOneseoByIdService.execute(memberId);
    }

    @GetMapping("/oneseo/{memberId}")
    public FoundOneseoResDto findByAdmin(
            @PathVariable Long memberId
    ) {
        return queryOneseoByIdService.execute(memberId);
    }

    @PatchMapping("/final-submit")
    public CommonApiResponse finalSubmit(
            @AuthRequest Long memberId
    ) {
        updateFinalSubmissionService.execute(memberId);
        return CommonApiResponse.success("수정되었습니다.");
    }

    @PostMapping("/calculate-mock-score")
    public MockScoreResDto calcMockScore(
            @RequestBody MiddleSchoolAchievementReqDto dto,
            @RequestParam("graduationType") GraduationType graduationType
        ) {
        return calculateMockScoreService.execute(dto, graduationType);
    }

    @DeleteMapping("/oneseo/me")
    public CommonApiResponse deleteMyOneseo(
            @AuthRequest Long memberId
    ) {
        deleteOneseoService.execute(memberId);
        return CommonApiResponse.success("삭제되었습니다.");
    }

    @GetMapping("/admission-tickets")
    public List<AdmissionTicketsResDto> getAdmissionTickets(
    ) {
        return queryAdmissionTicketsService.execute();
    }

    @GetMapping("/excel")
    public void downloadExcel(
            HttpServletResponse response
    ) {
        Workbook workbook = downloadExcelService.execute();
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("지원자 입학정보.xlsx", StandardCharsets.UTF_8).replace("+", "%20"));
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (IOException ex) {
            throw new RuntimeException("파일 작성과정에서 예외가 발생하였습니다.", ex);
        }
    }
}
