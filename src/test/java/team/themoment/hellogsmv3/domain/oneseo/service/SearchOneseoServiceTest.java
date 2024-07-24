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
import team.themoment.hellogsmv3.domain.oneseo.dto.request.TestResultTag;
import team.themoment.hellogsmv3.domain.oneseo.dto.response.SearchOneseosResDto;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo;
import team.themoment.hellogsmv3.domain.oneseo.repository.EntranceTestResultRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoPrivacyDetailRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoRepository;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@DisplayName("SearchOneseoService 클래스의")
class SearchOneseoServiceAdditionalTests {

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
        private final int size = 10;
        private final TestResultTag testResultTag = TestResultTag.ALL;
        private final ScreeningCategory screeningTag = ScreeningCategory.GENERAL;
        private final YesNo isSubmitted = YesNo.YES;
        private final String keyword = "홍길동";

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
    }
}
