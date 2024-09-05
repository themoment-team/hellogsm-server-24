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
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@DisplayName("OneseoService 클래스의")
public class OneseoServiceTest {

    @Mock
    private OneseoRepository oneseoRepository;

    @InjectMocks
    private OneseoService oneseoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("findByMemberOrThrow 메소드는")
    class Describe_findByMemberOrThrow {

        private final Long memberId = 1L;
        private final Member member = Member.builder().id(memberId).build();
        private final Oneseo oneseo = Oneseo.builder().member(member).build();

        @Nested
        @DisplayName("존재하는 회원과 원서가 주어지면")
        class Context_with_existing_member_and_oneseo {

            @BeforeEach
            void setUp() {
                given(oneseoRepository.findByMember(member)).willReturn(Optional.of(oneseo));
            }

            @Test
            @DisplayName("Oneseo 객체를 반환한다.")
            void it_returns_oneseo() {
                Oneseo foundOneseo = oneseoService.findByMemberOrThrow(member);
                assertEquals(oneseo, foundOneseo);
            }
        }

        @Nested
        @DisplayName("존재하지 않는 원서가 주어지면")
        class Context_with_non_existing_oneseo {

            @BeforeEach
            void setUp() {
                given(oneseoRepository.findByMember(member)).willReturn(Optional.empty());
            }

            @Test
            @DisplayName("ExpectedException을 던진다.")
            void it_throws_expected_exception() {
                ExpectedException exception = assertThrows(ExpectedException.class, () ->
                        oneseoService.findByMemberOrThrow(member)
                );

                assertEquals("해당 지원자의 원서를 찾을 수 없습니다. member ID: " + memberId, exception.getMessage());
                assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
            }
        }
    }

    @Nested
    @DisplayName("calcAbsentDaysCount 메소드는")
    class Describe_calcAbsentDaysCount {

        @Nested
        @DisplayName("결석 횟수 list와 지각, 조퇴, 결과 횟수 list를 보내면")
        class Context_with_absent_attendance_days {

            List<Integer> absentDays = List.of(3, 0, 0);
            List<Integer> attendanceDays = List.of(0, 0, 0, 1, 0, 1, 0, 2, 2);

            @Test
            @DisplayName("환산일수를 반환한다.")
            void it_returns_oneseo() {
                Integer absentDaysCount = OneseoService.calcAbsentDaysCount(absentDays, attendanceDays);
                assertEquals(absentDaysCount, 5);
            }
        }

        @Nested
        @DisplayName("null 값이 결석 횟수 list와 지각, 조퇴, 결과 횟수 list를 보내면")
        class Context_with_null_absent_attendance_days {

            List<Integer> nullAbsentDays = null;
            List<Integer> nullAttendanceDays = null;

            @Test
            @DisplayName("null 값을 반환한다.")
            void it_returns_oneseo() {
                Integer absentDaysCount = OneseoService.calcAbsentDaysCount(nullAbsentDays, nullAttendanceDays);
                assertEquals(absentDaysCount, null);
            }
        }
    }

}
