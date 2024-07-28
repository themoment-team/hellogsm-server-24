package team.themoment.hellogsmv3.domain.oneseo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import team.themoment.hellogsmv3.domain.application.type.ScreeningCategory;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.oneseo.dto.request.TestResultTag;
import team.themoment.hellogsmv3.domain.oneseo.dto.response.SearchOneseoPageInfoDto;
import team.themoment.hellogsmv3.domain.oneseo.dto.response.SearchOneseoResDto;
import team.themoment.hellogsmv3.domain.oneseo.dto.response.SearchOneseosResDto;
import team.themoment.hellogsmv3.domain.oneseo.entity.EntranceTestResult;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;
import team.themoment.hellogsmv3.domain.oneseo.entity.OneseoPrivacyDetail;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo;
import team.themoment.hellogsmv3.domain.oneseo.repository.EntranceTestResultRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoPrivacyDetailRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoRepository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@DisplayName("SearchOneseoService 클래스의")
class SearchOneseoServiceTest {

    @Mock
    private OneseoRepository oneseoRepository;

    @Mock
    private OneseoPrivacyDetailRepository oneseoPrivacyDetailRepository;

    @Mock
    private EntranceTestResultRepository entranceTestResultRepository;

    @InjectMocks
    private SearchOneseoService searchOneseoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("execute 메소드는")
    class Describe_execute {

        private final int page = 0;
        private final int size = 3;
        private final TestResultTag testResultTag = TestResultTag.ALL;
        private final ScreeningCategory screeningTag = ScreeningCategory.GENERAL;
        private final YesNo isSubmitted = YesNo.YES;
        private final String keyword = "최장우";

        @Nested
        @DisplayName("검색 결과가 없을 때")
        class Context_with_no_search_results {

            @BeforeEach
            void setUp() {
                Pageable pageable = PageRequest.of(page, size);
                Page<Oneseo> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

                given(oneseoRepository.findAllByKeywordAndScreeningAndSubmissionStatusAndTestResult(
                        keyword, screeningTag, isSubmitted, testResultTag, pageable
                )).willReturn(emptyPage);
            }

            @Test
            @DisplayName("빈 결과를 반환한다")
            void it_returns_empty_result() {
                SearchOneseosResDto result = searchOneseoService.execute(page, size, testResultTag, screeningTag, isSubmitted, keyword);

                assertEquals(0, result.info().totalElements());
                assertEquals(0, result.info().totalPages());
                assertEquals(0, result.oneseos().size());
            }
        }

        @Nested
        @DisplayName("필터링 조건에 따라")
        class Context_with_filtering {

            private Member member;
            private Oneseo oneseo;
            private OneseoPrivacyDetail oneseoPrivacyDetail;
            private EntranceTestResult entranceTestResult;

            @BeforeEach
            void setUp() {
                Pageable pageable = PageRequest.of(page, size);
                member = buildMember();
                oneseo = buildOneseo(member);
                Page<Oneseo> oneseoPage = new PageImpl<>(List.of(oneseo), pageable, 1);

                oneseoPrivacyDetail = buildOneseoPrivacyDetail();
                entranceTestResult = mock(EntranceTestResult.class);

                given(oneseoRepository.findAllByKeywordAndScreeningAndSubmissionStatusAndTestResult(
                        keyword, screeningTag, isSubmitted, testResultTag, pageable
                )).willReturn(oneseoPage);
                given(oneseoPrivacyDetailRepository.findByOneseo(oneseo)).willReturn(oneseoPrivacyDetail);
                given(entranceTestResultRepository.findByOneseo(oneseo)).willReturn(entranceTestResult);
                stubEntranceTestResult(entranceTestResult);
            }

            @Test
            @DisplayName("적절한 데이터를 반환한다")
            void it_returns_filtered_results() {
                SearchOneseosResDto result = searchOneseoService.execute(page, size, testResultTag, screeningTag, isSubmitted, keyword);

                SearchOneseoPageInfoDto searchOneseoPageInfoDto = result.info();
                assertEquals(1, searchOneseoPageInfoDto.totalElements());
                assertEquals(1, searchOneseoPageInfoDto.totalPages());

                SearchOneseoResDto searchOneseoResDto = result.oneseos().get(0);
                assertEquals(member.getId(), searchOneseoResDto.memberId());
                assertEquals(oneseo.getOneseoSubmitCode(), searchOneseoResDto.submitCode());
                assertEquals(oneseo.getRealOneseoArrivedYn(), searchOneseoResDto.realOneseoArrivedYn());
                assertEquals(member.getName(), searchOneseoResDto.name());
                assertEquals(oneseo.getAppliedScreening(), searchOneseoResDto.screening());
                assertEquals(oneseoPrivacyDetail.getSchoolName(), searchOneseoResDto.schoolName());
                assertEquals(member.getPhoneNumber(), searchOneseoResDto.phoneNumber());
                assertEquals(oneseoPrivacyDetail.getGuardianPhoneNumber(), searchOneseoResDto.guardianPhoneNumber());
                assertEquals(oneseoPrivacyDetail.getSchoolTeacherPhoneNumber(), searchOneseoResDto.schoolTeacherPhoneNumber());
                assertEquals(entranceTestResult.getFirstTestPassYn(), searchOneseoResDto.firstTestPassYn());
                assertEquals(entranceTestResult.getAptitudeEvaluationScore(), searchOneseoResDto.aptitudeEvaluationScore());
                assertEquals(entranceTestResult.getInterviewScore(), searchOneseoResDto.interviewScore());
                assertEquals(entranceTestResult.getSecondTestPassYn(), searchOneseoResDto.secondTestPassYn());
            }
        }
    }

    private OneseoPrivacyDetail buildOneseoPrivacyDetail() {
        return OneseoPrivacyDetail.builder()
                .schoolName("양산중학교")
                .guardianPhoneNumber("01012345678")
                .schoolTeacherPhoneNumber("01087654321")
                .build();
    }

    private Member buildMember() {
        return Member.builder()
                .id(1L)
                .name("최장우")
                .phoneNumber("01044448888")
                .build();
    }

    private Oneseo buildOneseo(Member member) {
        return Oneseo.builder()
                .member(member)
                .oneseoSubmitCode("submit code")
                .realOneseoArrivedYn(YesNo.YES)
                .appliedScreening(Screening.GENERAL)
                .build();
    }

    private void stubEntranceTestResult(EntranceTestResult entranceTestResult) {
        given(entranceTestResult.getFirstTestPassYn()).willReturn(YesNo.YES);
        given(entranceTestResult.getAptitudeEvaluationScore()).willReturn(BigDecimal.TEN);
        given(entranceTestResult.getInterviewScore()).willReturn(BigDecimal.TEN);
        given(entranceTestResult.getFirstTestPassYn()).willReturn(YesNo.YES);
    }
}
