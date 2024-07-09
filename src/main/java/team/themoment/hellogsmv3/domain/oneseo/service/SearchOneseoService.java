package team.themoment.hellogsmv3.domain.oneseo.service;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.oneseo.dto.response.SearchOneseoPageInfoDto;
import team.themoment.hellogsmv3.domain.oneseo.dto.response.SearchOneseoResDto;
import team.themoment.hellogsmv3.domain.oneseo.dto.response.SearchOneseosResDto;
import team.themoment.hellogsmv3.domain.oneseo.dto.type.SearchTag;
import team.themoment.hellogsmv3.domain.oneseo.entity.EntranceTestResult;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;
import team.themoment.hellogsmv3.domain.oneseo.entity.OneseoPrivacyDetail;
import team.themoment.hellogsmv3.domain.oneseo.repository.EntranceTestResultRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoPrivacyDetailRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SearchOneseoService {

    private final OneseoRepository oneseoRepository;
    private final OneseoPrivacyDetailRepository oneseoPrivacyDetailRepository;
    private final EntranceTestResultRepository entranceTestResultRepository;

    public SearchOneseosResDto execute(
            Integer page,
            Integer size,
            @Nullable SearchTag tag,
            @Nullable String keyword
    ) {
        Pageable pageable = PageRequest.of(page, size); // TODO 정렬 기능 필요 검토
        Page<Oneseo> oneseoPage = findOneseoByTagAndKeyword(tag, keyword, pageable);

        SearchOneseoPageInfoDto infoDto = SearchOneseoPageInfoDto.builder()
                .totalPages(oneseoPage.getTotalPages())
                .totalElements(oneseoPage.getTotalElements())
                .build();

        List<Oneseo> oneseos = oneseoPage.getContent();
        List<SearchOneseoResDto> searchOneseoResDtos = oneseos.stream()
                .map(this::buildSearchOneseoResDto)
                .toList();

        return SearchOneseosResDto.builder()
                .info(infoDto)
                .oneseos(searchOneseoResDtos)
                .build();
    }

    private Page<Oneseo> findOneseoByTagAndKeyword(
            SearchTag tag,
            String keyword,
            Pageable pageable
    ) {
        if (tag == null) {
            return oneseoRepository.findAllByFinalSubmitted(pageable);    // TODO queryDSL로 구현
        }
        return switch (tag) {
            case NAME -> oneseoRepository.findAllByFinalSubmittedAndMemberNameContaining(keyword, pageable);
            case SCHOOL -> oneseoRepository.findAllByFinalSubmittedAndSchoolNameContaining(keyword, pageable);
            case PHONE_NUMBER -> oneseoRepository.findAllByFinalSubmittedAndPhoneNumberContaining(keyword, pageable);
        };
    }

    private SearchOneseoResDto buildSearchOneseoResDto(Oneseo oneseo) {
        Member member = oneseo.getMember();
        OneseoPrivacyDetail oneseoPrivacyDetail = oneseoPrivacyDetailRepository.findByOneseo(oneseo);
        Optional<EntranceTestResult> entranceTestResultOpt = entranceTestResultRepository.findByOneseo(oneseo);

        return SearchOneseoResDto.builder()
                .memberId(member.getId())
                .submitCode(oneseo.getOneseoSubmitCode())
                .realOneseoArrivedYn(oneseo.getRealOneseoArrivedYn())
                .name(member.getName())
                .screening(oneseo.getAppliedScreening())
                .schoolName(oneseoPrivacyDetail.getSchoolName())
                .phoneNumber(member.getPhoneNumber())
                .guardianPhoneNumber(oneseoPrivacyDetail.getGuardianPhoneNumber())
                .schoolTeacherPhoneNumber(oneseoPrivacyDetail.getSchoolTeacherPhoneNumber())
                .firstTestPassYn(entranceTestResultOpt.map(EntranceTestResult::getFirstTestPassYn).orElse(null))
                .aptitudeEvaluationScore(entranceTestResultOpt.map(EntranceTestResult::getSecondTestResultScore).orElse(null))
                .interviewScore(entranceTestResultOpt.map(EntranceTestResult::getInterviewScore).orElse(null))
                .secondTestPassYn(entranceTestResultOpt.map(EntranceTestResult::getSecondTestPassYn).orElse(null))
                .build();
    }
}
