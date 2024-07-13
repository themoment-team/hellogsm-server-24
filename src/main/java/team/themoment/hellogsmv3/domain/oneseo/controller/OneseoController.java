package team.themoment.hellogsmv3.domain.oneseo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team.themoment.hellogsmv3.domain.application.type.ApplicationSearchTag;
import team.themoment.hellogsmv3.domain.oneseo.dto.request.OneseoReqDto;
import team.themoment.hellogsmv3.domain.oneseo.dto.response.SearchOneseosResDto;
import team.themoment.hellogsmv3.domain.oneseo.dto.type.SearchTag;
import team.themoment.hellogsmv3.domain.oneseo.service.CreateOneseoService;
import team.themoment.hellogsmv3.domain.oneseo.service.ModifyOneseoService;
import team.themoment.hellogsmv3.domain.oneseo.service.SearchOneseoService;
import team.themoment.hellogsmv3.global.common.handler.annotation.AuthRequest;
import team.themoment.hellogsmv3.global.common.response.CommonApiResponse;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

@RestController
@RequestMapping("/oneseo/v3")
@RequiredArgsConstructor
public class OneseoController {

    private final CreateOneseoService createOneseoService;
    private final ModifyOneseoService modifyOneseoService;
    private final SearchOneseoService searchOneseoService;

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

    @GetMapping("/oneseo/search")
    public SearchOneseosResDto search(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(name = "tag", required = false) String tag,
            @RequestParam(name = "keyword", required = false) String keyword
    ) {
        validatePageAndSize(page, size);
        SearchTag searchTag = validateAndConvertTag(tag);
        return searchOneseoService.execute(page, size, searchTag, keyword);
    }

    private void validatePageAndSize(Integer page, Integer size) {
        if (page < 0 || size < 0)
            throw new ExpectedException("page, size는 0 이상만 가능합니다", HttpStatus.BAD_REQUEST);
    }

    private SearchTag validateAndConvertTag(String tag) {
        if (tag == null)
            return null;
        try {
            return SearchTag.valueOf(tag);
        } catch (IllegalArgumentException e) {
            throw new ExpectedException("유효하지 않은 tag입니다", HttpStatus.BAD_REQUEST);
        }
    }
}
