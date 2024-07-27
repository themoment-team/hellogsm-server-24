package team.themoment.hellogsmv3.domain.oneseo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.member.service.MemberService;
import team.themoment.hellogsmv3.domain.oneseo.dto.request.InterviewScoreReqDto;
import team.themoment.hellogsmv3.domain.oneseo.entity.EntranceTestResult;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;
import team.themoment.hellogsmv3.domain.oneseo.repository.EntranceTestResultRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@DisplayName("ModifyInterviewScoreService 클래스의")
class ModifyInterviewScoreServiceTest {

    @Mock
    private MemberService memberService;

    @Mock
    private OneseoService oneseoService;

    @Mock
    private EntranceTestResultRepository entranceTestResultRepository;

    @InjectMocks
    private ModifyInterviewScoreService modifyInterviewScoreService;

    private Long memberId;
    private InterviewScoreReqDto reqDto;
    private Member member;
    private Oneseo oneseo;
    private EntranceTestResult entranceTestResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        memberId = 1L;
        reqDto = new InterviewScoreReqDto(BigDecimal.valueOf(100));
        member = mock(Member.class);
        oneseo = mock(Oneseo.class);
        entranceTestResult = mock(EntranceTestResult.class);
    }

    @Nested
    @DisplayName("execute 메소드는")
    class Describe_execute {

        @Nested
        @DisplayName("해당 memberId로 회원을 찾을 수 없는 경우")
        class Context_when_member_not_found {

            @BeforeEach
            void setUp() {
                given(memberService.findByIdOrThrow(memberId)).willThrow(new ExpectedException("존재하지 않는 지원자입니다. member ID: " + memberId, HttpStatus.NOT_FOUND));
            }

            @Test
            @DisplayName("ExpectedException을 던진다")
            void it_throws_ExpectedException() {
                ExpectedException exception = assertThrows(ExpectedException.class, () -> modifyInterviewScoreService.execute(memberId, reqDto));
                assertEquals("존재하지 않는 지원자입니다. member ID: " + memberId, exception.getMessage());
                assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
            }
        }

        @Nested
        @DisplayName("해당 memberId로 원서를 찾을 수 없는 경우")
        class Context_when_oneseo_not_found {

            @BeforeEach
            void setUp() {
                given(memberService.findByIdOrThrow(memberId)).willReturn(member);
                given(oneseoService.findByMemberOrThrow(member)).willThrow(new ExpectedException("해당 지원자의 원서를 찾을 수 없습니다. member ID: " + memberId, HttpStatus.NOT_FOUND));
            }

            @Test
            @DisplayName("ExpectedException을 던진다")
            void it_throws_ExpectedException() {
                ExpectedException exception = assertThrows(ExpectedException.class, () -> modifyInterviewScoreService.execute(memberId, reqDto));
                assertEquals("해당 지원자의 원서를 찾을 수 없습니다. member ID: " + memberId, exception.getMessage());
                assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
            }
        }

        @Nested
        @DisplayName("해당 원서의 입학 시험 결과를 찾을 수 없는 경우")
        class Context_when_entrance_test_result_not_found {

            @BeforeEach
            void setUp() {
                given(memberService.findByIdOrThrow(memberId)).willReturn(member);
                given(oneseoService.findByMemberOrThrow(member)).willReturn(oneseo);
                given(entranceTestResultRepository.findEntranceTestResultByOneseo(oneseo)).willReturn(Optional.empty());
            }

            @Test
            @DisplayName("ExpectedException을 던진다")
            void it_throws_ExpectedException() {
                ExpectedException exception = assertThrows(ExpectedException.class, () -> modifyInterviewScoreService.execute(memberId, reqDto));
                assertEquals("해당 지원자의 입학 시험 결과를 찾을 수 없습니다. member ID: " + memberId, exception.getMessage());
                assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
            }
        }

        @Nested
        @DisplayName("모든 유효성 검증이 성공한 경우")
        class Context_with_valid_data {

            @BeforeEach
            void setUp() {
                given(memberService.findByIdOrThrow(memberId)).willReturn(member);
                given(oneseoService.findByMemberOrThrow(member)).willReturn(oneseo);
                given(entranceTestResultRepository.findEntranceTestResultByOneseo(oneseo)).willReturn(Optional.of(entranceTestResult));
            }

            @Test
            @DisplayName("변경된 심층면점 점수를 수정하고 저장한다")
            void it_modifies_and_saves_interview_score() {
                modifyInterviewScoreService.execute(memberId, reqDto);

                verify(entranceTestResult).modifyInterviewScore(reqDto.interviewScore());
                verify(entranceTestResultRepository).save(entranceTestResult);
            }
        }
    }
}
