package team.themoment.hellogsmv3.domain.oneseo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.*;
import team.themoment.hellogsmv3.domain.oneseo.dto.request.*;
import team.themoment.hellogsmv3.domain.oneseo.dto.response.*;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.ScreeningCategory;
import team.themoment.hellogsmv3.domain.oneseo.service.*;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.GraduationType;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo;
import team.themoment.hellogsmv3.domain.oneseo.service.CreateOneseoService;
import team.themoment.hellogsmv3.domain.oneseo.service.ModifyOneseoService;
import team.themoment.hellogsmv3.domain.oneseo.service.SearchOneseoService;
import team.themoment.hellogsmv3.domain.oneseo.service.CalculateMockScoreService;
import team.themoment.hellogsmv3.domain.oneseo.service.QueryOneseoByIdService;
import team.themoment.hellogsmv3.domain.oneseo.service.ModifyRealOneseoArrivedYnService;
import team.themoment.hellogsmv3.global.common.handler.annotation.AuthRequest;
import team.themoment.hellogsmv3.global.common.response.CommonApiResponse;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Tag(name = "Oneseo API", description = "원서 관련 API입니다.")
@RestController
@RequestMapping("/oneseo/v3")
@RequiredArgsConstructor
public class OneseoController {

    private final CreateOneseoService createOneseoService;
    private final ModifyOneseoService modifyOneseoService;
    private final ModifyRealOneseoArrivedYnService modifyRealOneseoArrivedYnService;
    private final ModifyAptitudeEvaluationScoreService modifyAptitudeEvaluationScoreService;
    private final ModifyInterviewScoreService modifyInterviewScoreService;
    private final QueryAdmissionTicketsService queryAdmissionTicketsService;
    private final DownloadExcelService downloadExcelService;
    private final SearchOneseoService searchOneseoService;
    private final QueryOneseoByIdService queryOneseoByIdService;
    private final CalculateMockScoreService calculateMockScoreService;
    private final OneseoTempStorageService oneseoTempStorageService;
    private final ModifyEntranceIntentionService modifyEntranceIntentionService;
    private final QueryOneseoEditabilityService queryOneseoEditabilityService;

    @Operation(summary = "내 원서 등록", description = "원서를 등록합니다.")
    @PostMapping("/oneseo/me")
    public CommonApiResponse create(
            @RequestBody @Valid OneseoReqDto reqDto,
            @AuthRequest Long memberId
    ) {
        createOneseoService.execute(reqDto, memberId);
        return CommonApiResponse.created("생성되었습니다.");
    }

    @Operation(summary = "원서 수정", description = "맴버 id로 원서를 수정합니다.")
    @PutMapping("/oneseo/{memberId}")
    public CommonApiResponse modifyByAdmin(
            @RequestBody @Valid OneseoReqDto reqDto,
            @PathVariable("memberId") Long memberId
    ) {
        modifyOneseoService.execute(reqDto, memberId);
        return CommonApiResponse.success("수정되었습니다.");
    }

    @Operation(summary = "실물 원서 제출 여부 수정", description = "맴버 id로 원서의 실물 원서 제출 여부를 수정합니다.")
    @PatchMapping("/arrived-status/{memberId}")
    public ArrivedStatusResDto modifyArrivedStatus(
            @PathVariable Long memberId
    ) {
        return modifyRealOneseoArrivedYnService.execute(memberId);
    }

    @Operation(summary = "적성 검사 점수 기입", description = "맴버 id로 원서의 적성 검사 점수를 기입합니다.")
    @PatchMapping("/aptitude-score/{memberId}")
    public CommonApiResponse modifyAptitudeScore(
            @PathVariable Long memberId,
            @RequestBody @Valid AptitudeEvaluationScoreReqDto aptitudeEvaluationScoreReqDto
    ) {
        modifyAptitudeEvaluationScoreService.execute(memberId, aptitudeEvaluationScoreReqDto);
        return CommonApiResponse.success("수정되었습니다.");
    }

    @Operation(summary = "심층 면접 검사 점수 기입", description = "맴버 id로 원서의 심층 면접 검사 점수를 기입합니다.")
    @PatchMapping("/interview-score/{memberId}")
    public CommonApiResponse modifyInterviewScore(
            @PathVariable Long memberId,
            @RequestBody @Valid InterviewScoreReqDto reqDto
    ) {
        modifyInterviewScoreService.execute(memberId, reqDto);
        return CommonApiResponse.success("수정되었습니다.");
    }

    @Operation(summary = "원서 검색", description = "조건을 파라미터로 받아 원서를 검색합니다.")
    @GetMapping("/oneseo/search")
    public SearchOneseosResDto search(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @Schema(description = "합격, 불합격 여부", defaultValue = "ALL", allowableValues = {"ALL", "FIRST_PASS", "FINAL_PASS", "FALL"})
            @RequestParam(name = "testResultTag") String testResultParam,
            @Schema(description = "지원 전형", defaultValue = "GENERAL", allowableValues = {"GENERAL", "SPECIAL", "EXTRA"})
            @RequestParam(name = "screeningTag", required = false) String screeningParam,
            @Schema(description = "서류 제출 여부", defaultValue = "YES", allowableValues = {"YES", "NO"})
            @RequestParam(name = "isSubmitted", required = false) String isSubmittedParam,
            @RequestParam(name = "keyword", required = false) String keyword
    ) {
        if (page < 0 || size < 0) {
            throw new ExpectedException("page, size는 0 이상만 가능합니다", HttpStatus.BAD_REQUEST);
        }

        try {
            TestResultTag tag = TestResultTag.valueOf(testResultParam);
            ScreeningCategory screeningCategory = (screeningParam != null) ? ScreeningCategory.valueOf(screeningParam) : null;
            YesNo isSubmitted = (isSubmittedParam != null) ? YesNo.valueOf(isSubmittedParam) : null;

            return searchOneseoService.execute(page, size, tag, screeningCategory, isSubmitted, keyword);
        } catch (IllegalArgumentException e) {
            throw new ExpectedException("유효하지 않은 매개변수 값입니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "내 원서 조회", description = "내 원서 정보를 조회합니다. 임시 저장된 원서가 있다면 임시 저장된 원서를 조회합니다.")
    @GetMapping("/oneseo/me")
    public FoundOneseoResDto find(
            @AuthRequest Long memberId
    ) {
        return queryOneseoByIdService.execute(memberId);
    }

    @Operation(summary = "원서 조회", description = "맴버 id로 원서 정보를 조회합니다.")
    @GetMapping("/oneseo/{memberId}")
    public FoundOneseoResDto findByAdmin(
            @PathVariable Long memberId
    ) {
        return queryOneseoByIdService.execute(memberId);
    }

    @Operation(summary = "모의 성적 계산", description = "성적 점수를 입력받아 모의 성적 환산값을 반환합니다.")
    @PostMapping("/calculate-mock-score")
    public CalculatedScoreResDto calcMockScore(
            @RequestBody MiddleSchoolAchievementReqDto dto,
            @RequestParam("graduationType") GraduationType graduationType
    ) {
        return calculateMockScoreService.execute(dto, graduationType);
    }

    @Operation(summary = "수험표 출력", description = "모든 원서의 수험표 정보를 반환합니다.")
    @GetMapping("/admission-tickets")
    public List<AdmissionTicketsResDto> getAdmissionTickets(
    ) {
        return queryAdmissionTicketsService.execute();
    }

    @Operation(summary = "원서 임시 저장", description = "원서 정보를 임시 저장합니다.")
    @PostMapping("/temp-storage")
    public CommonApiResponse temp(
            @RequestBody @Valid OneseoTempReqDto reqDto,
            @RequestParam Integer step,
            @AuthRequest Long memberId
    ) {
        oneseoTempStorageService.execute(reqDto, step, memberId);
        return CommonApiResponse.success("임시저장되었습니다.");
    }

    @Operation(summary = "엑셀 출력", description = "모든 원서의 정보를 엑셀 파일로 반환합니다.")
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

    @Operation(summary = "입학등록 동의서 제출여부 수정", description = "맴버 id로 원서의 입학등록 동의서 제출여부를 수정합니다.")
    @PatchMapping("/entrance-intention/{memberId}")
    public CommonApiResponse modifyEntranceIntention(
            @PathVariable Long memberId,
            @RequestBody @Valid EntranceIntentionReqDto reqDto
    ) {
        modifyEntranceIntentionService.execute(memberId, reqDto);
        return CommonApiResponse.success("수정되었습니다.");
    }

    @Operation(summary = "원서 수정 가능여부", description = "원서 수정이 가능한지에 대해 반환합니다.")
    @GetMapping("/editability")
    public OneseoEditabilityResDto getOneseoEditability(
    ) {
        return queryOneseoEditabilityService.execute();
    }
}
