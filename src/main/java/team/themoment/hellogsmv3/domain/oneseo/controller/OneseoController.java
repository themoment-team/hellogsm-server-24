package team.themoment.hellogsmv3.domain.oneseo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.themoment.hellogsmv3.domain.oneseo.dto.request.AptitudeEvaluationScoreReqDto;
import team.themoment.hellogsmv3.domain.oneseo.dto.request.OneseoReqDto;
import team.themoment.hellogsmv3.domain.oneseo.dto.response.AdmissionTicketsResDto;
import team.themoment.hellogsmv3.domain.oneseo.dto.response.ArrivedStatusResDto;
import team.themoment.hellogsmv3.domain.oneseo.service.*;
import team.themoment.hellogsmv3.global.common.handler.annotation.AuthRequest;
import team.themoment.hellogsmv3.global.common.response.CommonApiResponse;

import java.util.List;

@RestController
@RequestMapping("/oneseo/v3")
@RequiredArgsConstructor
public class OneseoController {

    private final CreateOneseoService createOneseoService;
    private final ModifyOneseoService modifyOneseoService;
    private final ModifyRealOneseoArrivedYnService modifyRealOneseoArrivedYnService;
    private final ModifyAptitudeEvaluationScoreService modifyAptitudeEvaluationScoreService;
    private final DeleteOneseoService deleteOneseoService;
    private final QueryAdmissionTicketsService queryAdmissionTicketsService;

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
}
