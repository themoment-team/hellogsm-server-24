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
import team.themoment.hellogsmv3.domain.member.entity.type.Sex;
import team.themoment.hellogsmv3.domain.member.service.MemberService;
import team.themoment.hellogsmv3.domain.oneseo.dto.response.*;
import team.themoment.hellogsmv3.domain.oneseo.entity.*;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.DesiredMajors;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.GraduationType;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.Major;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening;
import team.themoment.hellogsmv3.domain.oneseo.repository.MiddleSchoolAchievementRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoPrivacyDetailRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@DisplayName("QueryOneseoByIdService 클래스의")
class QueryOneseoByIdServiceTest {

    @Mock
    private OneseoService oneseoService;

    @Mock
    private MemberService memberService;

    @Mock
    private OneseoPrivacyDetailRepository oneseoPrivacyDetailRepository;

    @Mock
    private MiddleSchoolAchievementRepository middleSchoolAchievementRepository;

    @InjectMocks
    private QueryOneseoByIdService queryOneseoByIdService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("execute 메소드는")
    class Describe_execute {

        private final Long memberId = 1L;

        @Nested
        @DisplayName("존재하는 회원 ID가 주어졌을 때")
        class Context_with_existing_member_id {

            private Member member;
            private Oneseo oneseo;
            private OneseoPrivacyDetail oneseoPrivacyDetail;
            private MiddleSchoolAchievement middleSchoolAchievement;

            void setUp_it_returns_oneseo() {
                member = buildMember(memberId);
                oneseo = buildOneseo(member);
                oneseoPrivacyDetail = buildOneseoPrivacyDetail();
                middleSchoolAchievement = buildMiddleSchoolAchievement();

                given(memberService.findByIdOrThrow(memberId)).willReturn(member);
                given(oneseoService.findByMemberOrThrow(member)).willReturn(oneseo);
                given(oneseoPrivacyDetailRepository.findByOneseo(oneseo)).willReturn(oneseoPrivacyDetail);
                given(middleSchoolAchievementRepository.findByOneseo(oneseo)).willReturn(middleSchoolAchievement);
            }

            @Test
            @DisplayName("원서가 존재한다면 원서 관련 정보(개인정보, 중학교성취도, 전형 등)를 반환한다")
            void it_returns_oneseo() {
                setUp_it_returns_oneseo();

                FoundOneseoResDto result = queryOneseoByIdService.execute(memberId);

                assertEquals(oneseo.getId(), result.oneseoId());
                assertEquals(oneseo.getOneseoSubmitCode(), result.submitCode());
                assertEquals(oneseo.getWantedScreening(), result.wantedScreening());
                assertEquals(oneseo.getDesiredMajors().getFirstDesiredMajor(), result.desiredMajors().firstDesiredMajor());
                assertEquals(member.getName(), result.privacyDetail().name());
                assertEquals(oneseoPrivacyDetail.getGuardianName(), result.privacyDetail().guardianName());
                assertEquals(middleSchoolAchievement.getAchievement3_1(), result.middleSchoolAchievement().achievement3_1());
                assertEquals(middleSchoolAchievement.getGedTotalScore(), result.middleSchoolAchievement().gedTotalScore());
            }

            void setUp_it_throws_expected_exception() {
                member = buildMember(memberId);

                given(memberService.findByIdOrThrow(memberId)).willReturn(member);
                when(oneseoService.findByMemberOrThrow(member)).thenThrow(
                        new ExpectedException("원서를 찾을 수 없습니다. member ID: " + memberId, HttpStatus.NOT_FOUND)
                );
            }

            @Test
            @DisplayName("원서가 존재하지 않는다면 ExpectedException을 던진다")
            void it_throws_expected_exception() {
                setUp_it_throws_expected_exception();

                ExpectedException exception = assertThrows(ExpectedException.class, () -> {
                    queryOneseoByIdService.execute(memberId);
                });

                assertEquals("원서를 찾을 수 없습니다. member ID: " + memberId, exception.getMessage());
                assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
            }
        }

        @Nested
        @DisplayName("존재하지 않는 회원 ID가 주어지면")
        class Context_with_non_existing_member_id {

            @BeforeEach
            void setUp() {
                when(memberService.findByIdOrThrow(memberId)).thenThrow(
                        new ExpectedException("존재하지 않는 지원자입니다. member ID: " + memberId, HttpStatus.NOT_FOUND)
                );
            }

            @Test
            @DisplayName("ExpectedException을 던진다")
            void it_throws_expected_exception() {
                ExpectedException exception = assertThrows(ExpectedException.class, () -> {
                    queryOneseoByIdService.execute(memberId);
                });

                assertEquals("존재하지 않는 지원자입니다. member ID: " + memberId, exception.getMessage());
                assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
            }
        }
    }

    private Member buildMember(Long memberId) {
        return Member.builder()
                .id(memberId)
                .name("최장우")
                .sex(Sex.MALE)
                .birth(LocalDate.of(2006, 3, 6))
                .phoneNumber("01012345678")
                .build();
    }

    private Oneseo buildOneseo(Member member) {
        return Oneseo.builder()
                .member(member)
                .id(1L)
                .oneseoSubmitCode("submitCode")
                .wantedScreening(Screening.GENERAL)
                .desiredMajors(new DesiredMajors(Major.SW, Major.IOT, Major.AI))
                .build();
    }

    private OneseoPrivacyDetail buildOneseoPrivacyDetail() {
        return OneseoPrivacyDetail.builder()
                .graduationType(GraduationType.GRADUATE)
                .address("거주 주소")
                .detailAddress("상세 주소")
                .guardianName("홍길동")
                .guardianPhoneNumber("01087654321")
                .relationshipWithGuardian("부")
                .schoolName("양산중학교")
                .schoolAddress("학교 주소")
                .schoolTeacherName("김철수")
                .schoolTeacherPhoneNumber("01012341234")
                .profileImg("https://example.com")
                .build();
    }

    private MiddleSchoolAchievement buildMiddleSchoolAchievement() {
        List<Integer> integerList = List.of(1, 2, 3, 4, 5);
        List<String> stringList = List.of("과목1", "과목2", "과목3");
        BigDecimal bigDecimal = BigDecimal.TEN;

        return MiddleSchoolAchievement.builder()
                .achievement1_2(integerList)
                .achievement2_1(integerList)
                .achievement2_2(integerList)
                .achievement3_1(integerList)
                .achievement3_2(integerList)
                .generalSubjects(stringList)
                .newSubjects(stringList)
                .artsPhysicalAchievement(integerList)
                .artsPhysicalSubjects(stringList)
                .absentDays(integerList)
                .attendanceDays(integerList)
                .volunteerTime(integerList)
                .liberalSystem("자유학년제")
                .freeSemester(null)
                .gedTotalScore(bigDecimal)
                .gedMaxScore(bigDecimal)
                .build();
    }
}

