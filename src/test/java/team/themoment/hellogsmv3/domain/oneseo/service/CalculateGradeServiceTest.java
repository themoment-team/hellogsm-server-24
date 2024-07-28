package team.themoment.hellogsmv3.domain.oneseo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import team.themoment.hellogsmv3.domain.oneseo.dto.request.MiddleSchoolAchievementReqDto;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;
import team.themoment.hellogsmv3.domain.oneseo.entity.EntranceTestFactorsDetail;
import team.themoment.hellogsmv3.domain.oneseo.entity.EntranceTestResult;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.GraduationType;
import team.themoment.hellogsmv3.domain.oneseo.repository.EntranceTestFactorsDetailRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.EntranceTestResultRepository;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("CalculateGradeService 클래스의")
class CalculateGradeServiceTest {

    @Mock
    private EntranceTestResultRepository entranceTestResultRepository;

    @Mock
    private EntranceTestFactorsDetailRepository entranceTestFactorsDetailRepository;

    @InjectMocks
    private CalculateGradeService calculateGradeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("execute 메소드는")
    class Describe_execute {

        private MiddleSchoolAchievementReqDto reqDto;
        private Oneseo oneseo;

        @BeforeEach
        void setUp() {
            reqDto = MiddleSchoolAchievementReqDto.builder()
                    .achievement1_2(Arrays.asList(5, 5, 5, 5, 5, 5, 5, 5, 0))
                    .achievement2_1(Arrays.asList(5, 5, 5, 5, 5, 5, 5, 5, 4))
                    .achievement2_2(Arrays.asList(5, 5, 5, 5, 5, 5, 5, 5, 4))
                    .artsPhysicalAchievement(Arrays.asList(5, 5, 5, 5, 5, 5, 5, 5, 5))
                    .absentDays(Arrays.asList(3, 0, 0))
                    .attendanceDays(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0))
                    .volunteerTime(Arrays.asList(10, 0, 2))
                    .liberalSystem("자유학기제")
                    .freeSemester("3-1")
                    .build();

            oneseo = mock(Oneseo.class);
        }

        @Nested
        @DisplayName("올바른 graduationType이 주어지면")
        class Context_with_valid_graduationType {

            @Test
            @DisplayName("내신 성적을 계산하고 결과를 저장한다")
            void it_calculates_and_saves_results() {
                calculateGradeService.execute(reqDto, oneseo, GraduationType.CANDIDATE);

                verify(entranceTestFactorsDetailRepository).save(any(EntranceTestFactorsDetail.class));
                verify(entranceTestResultRepository).save(any(EntranceTestResult.class));
            }

            @Test
            @DisplayName("내신 성적을 계산하고 결과를 반환한다")
            void it_calculates_and_returns_results() {
                calculateGradeService.execute(reqDto, oneseo, GraduationType.CANDIDATE);

                ArgumentCaptor<EntranceTestFactorsDetail> entranceTestFactorsDetailArgumentCaptor = ArgumentCaptor.forClass(EntranceTestFactorsDetail.class);
                ArgumentCaptor<EntranceTestResult> entranceTestResultArgumentCaptor = ArgumentCaptor.forClass(EntranceTestResult.class);

                verify(entranceTestFactorsDetailRepository).save(entranceTestFactorsDetailArgumentCaptor.capture());
                verify(entranceTestResultRepository).save(entranceTestResultArgumentCaptor.capture());

                EntranceTestFactorsDetail capturedEntranceTestFactorsDetailArgument = entranceTestFactorsDetailArgumentCaptor.getValue();
                EntranceTestResult capturedEntranceTestResult = entranceTestResultArgumentCaptor.getValue();

                assertEquals(BigDecimal.valueOf(272.20), capturedEntranceTestResult.getDocumentEvaluationScore());
            }
        }
    }
}
