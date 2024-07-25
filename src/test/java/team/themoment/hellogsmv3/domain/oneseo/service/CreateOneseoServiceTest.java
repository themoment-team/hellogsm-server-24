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
import team.themoment.hellogsmv3.domain.member.repo.MemberRepository;
import team.themoment.hellogsmv3.domain.oneseo.dto.request.MiddleSchoolAchievementReqDto;
import team.themoment.hellogsmv3.domain.oneseo.dto.request.OneseoReqDto;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;
import team.themoment.hellogsmv3.domain.oneseo.entity.OneseoPrivacyDetail;
import team.themoment.hellogsmv3.domain.oneseo.entity.MiddleSchoolAchievement;
import team.themoment.hellogsmv3.domain.oneseo.repository.MiddleSchoolAchievementRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoPrivacyDetailRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@DisplayName("CreateOneseoService 클래스의")
class CreateOneseoServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private OneseoRepository oneseoRepository;
    @Mock
    private OneseoPrivacyDetailRepository oneseoPrivacyDetailRepository;
    @Mock
    private MiddleSchoolAchievementRepository middleSchoolAchievementRepository;

    @InjectMocks
    private CreateOneseoService createOneseoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("execute 메서드는")
    class Describe_execute {

        private final Long memberId = 1L;
        private final OneseoReqDto reqDto = mock(OneseoReqDto.class);

        @Nested
        @DisplayName("유효한 회원 ID와 요청 데이터가 주어지면")
        class Context_with_valid_member_id_and_request_data {

            private Member existingMember;
            private MiddleSchoolAchievementReqDto middleSchoolAchievementReqDto;

            @BeforeEach
            void setUp() {
                existingMember = mock(Member.class);
                middleSchoolAchievementReqDto = mock(MiddleSchoolAchievementReqDto.class);

                given(memberRepository.findById(memberId)).willReturn(Optional.of(existingMember));
                given(oneseoRepository.existsByMember(existingMember)).willReturn(false);
                given(reqDto.middleSchoolAchievement()).willReturn(middleSchoolAchievementReqDto);
            }

            @Test
            @DisplayName("새로운 Oneseo, OneseoPrivacyDetail, MiddleSchoolAchievement 엔티티를 생성하고 저장한다")
            void it_creates_and_saves_new_entities() {
                createOneseoService.execute(reqDto, memberId);

                verify(oneseoRepository).save(any(Oneseo.class));
                verify(oneseoPrivacyDetailRepository).save(any(OneseoPrivacyDetail.class));
                verify(middleSchoolAchievementRepository).save(any(MiddleSchoolAchievement.class));
            }
        }

        @Nested
        @DisplayName("회원 ID가 유효하지 않으면")
        class Context_with_invalid_member_id {

            @BeforeEach
            void setUp() {
                given(memberRepository.findById(memberId)).willReturn(Optional.empty());
            }

            @Test
            @DisplayName("ExpectedException을 던진다")
            void it_throws_expected_exception() {
                ExpectedException exception = assertThrows(ExpectedException.class, () -> createOneseoService.execute(reqDto, memberId));

                assertEquals("존재하지 않는 지원자입니다. member ID: " + memberId, exception.getMessage());
                assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
            }
        }

        @Nested
        @DisplayName("이미 Oneseo가 존재하면")
        class Context_with_existing_oneseo {

            @BeforeEach
            void setUp() {
                given(memberRepository.findById(memberId)).willReturn(Optional.of(mock(Member.class)));
                given(oneseoRepository.existsByMember(any(Member.class))).willReturn(true);
            }

            @Test
            @DisplayName("ExpectedException을 던진다")
            void it_throws_expected_exception() {
                ExpectedException exception = assertThrows(ExpectedException.class, () -> createOneseoService.execute(reqDto, memberId));

                assertEquals("이미 원서가 존재합니다.", exception.getMessage());
                assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
            }
        }


        @Nested
        @DisplayName("유효하지 않은 교과 성적 점수가 입력되었다면")
        class Context_with_invalid_general_achievement {

            private Member existingMember;
            private MiddleSchoolAchievementReqDto middleSchoolAchievementReqDto;

            @BeforeEach
            void setUp() {
                existingMember = mock(Member.class);
                middleSchoolAchievementReqDto = mock(MiddleSchoolAchievementReqDto.class);

                given(memberRepository.findById(memberId)).willReturn(Optional.of(existingMember));
                given(oneseoRepository.existsByMember(existingMember)).willReturn(false);
                given(reqDto.middleSchoolAchievement()).willReturn(middleSchoolAchievementReqDto);

                List<Integer> invalidAchievements = Arrays.asList(6, 0);
                given(middleSchoolAchievementReqDto.achievement1_2()).willReturn(invalidAchievements);
                given(middleSchoolAchievementReqDto.achievement2_1()).willReturn(invalidAchievements);
                given(middleSchoolAchievementReqDto.achievement2_2()).willReturn(invalidAchievements);
                given(middleSchoolAchievementReqDto.achievement3_1()).willReturn(invalidAchievements);
                given(middleSchoolAchievementReqDto.achievement3_2()).willReturn(invalidAchievements);
            }

            @Test
            @DisplayName("ExpectedException을 던진다")
            void it_throws_expected_exception() {
                ExpectedException exception = assertThrows(ExpectedException.class, () -> createOneseoService.execute(reqDto, memberId));

                assertEquals("올바르지 않은 일반교과 등급이 입력되었습니다.", exception.getMessage());
                assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
            }
        }

        @Nested
        @DisplayName("유효하지 않은 예체능 성적 점수가 입력되었다면")
        class Context_with_invalid_arts_physical_achievement {

            private Member existingMember;
            private MiddleSchoolAchievementReqDto middleSchoolAchievementReqDto;

            @BeforeEach
            void setUp() {
                existingMember = mock(Member.class);
                middleSchoolAchievementReqDto = mock(MiddleSchoolAchievementReqDto.class);

                given(memberRepository.findById(memberId)).willReturn(Optional.of(existingMember));
                given(oneseoRepository.existsByMember(existingMember)).willReturn(false);
                given(reqDto.middleSchoolAchievement()).willReturn(middleSchoolAchievementReqDto);

                List<Integer> invalidAchievements = Arrays.asList(0, 1, 2, 6);
                given(middleSchoolAchievementReqDto.artsPhysicalAchievement()).willReturn(invalidAchievements);
            }

            @Test
            @DisplayName("ExpectedException을 던진다")
            void it_throws_expected_exception() {
                ExpectedException exception = assertThrows(ExpectedException.class, () -> createOneseoService.execute(reqDto, memberId));

                assertEquals("올바르지 않은 예체능 등급이 입력되었습니다.", exception.getMessage());
                assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
            }
        }
    }
}
