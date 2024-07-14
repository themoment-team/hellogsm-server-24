package team.themoment.hellogsmv3.domain.oneseo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.application.type.ScreeningCategory;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.oneseo.dto.request.ApplicantStatusTag;
import team.themoment.hellogsmv3.domain.oneseo.dto.response.SearchOneseoPageInfoDto;
import team.themoment.hellogsmv3.domain.oneseo.dto.response.SearchOneseoResDto;
import team.themoment.hellogsmv3.domain.oneseo.dto.response.SearchOneseosResDto;
import team.themoment.hellogsmv3.domain.oneseo.entity.EntranceTestResult;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;
import team.themoment.hellogsmv3.domain.oneseo.entity.OneseoPrivacyDetail;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo;
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
            ApplicantStatusTag applicantStatusTag,
            ScreeningCategory screeningTag,
            YesNo isSubmitted,
            String keyword
    ) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Oneseo> oneseoPage = findOneseoByTagsAndKeyword(
                applicantStatusTag,
                screeningTag,
                isSubmitted,
                keyword,
                pageable
        );

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

    private Page<Oneseo> findOneseoByTagsAndKeyword(
            ApplicantStatusTag applicantStatusTag,
            ScreeningCategory screeningTag,
            YesNo isSubmitted,
            String keyword,
            Pageable pageable
    ) {
        return oneseoRepository.findAllByKeywordAndScreeningAndSubmissionStatusAndTestResult(
                keyword,
                screeningTag,
                isSubmitted,
                applicantStatusTag,
                pageable
        );
    }

    private SearchOneseoResDto buildSearchOneseoResDto(Oneseo oneseo) {
        Member member = oneseo.getMember();
        OneseoPrivacyDetail oneseoPrivacyDetail = oneseoPrivacyDetailRepository.findByOneseo(oneseo);
        Optional<EntranceTestResult> entranceTestResultOpt = entranceTestResultRepository.findByOneseo(oneseo); // TODO 최종제출된 원서는 EntranceTestResult가 무조건 존재해서 Opt 필요없을듯

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
                .aptitudeEvaluationScore(entranceTestResultOpt.map(EntranceTestResult::getAptitudeEvaluationScore).orElse(null))
                .interviewScore(entranceTestResultOpt.map(EntranceTestResult::getInterviewScore).orElse(null))
                .secondTestPassYn(entranceTestResultOpt.map(EntranceTestResult::getSecondTestPassYn).orElse(null))
                .build();
    }
}
