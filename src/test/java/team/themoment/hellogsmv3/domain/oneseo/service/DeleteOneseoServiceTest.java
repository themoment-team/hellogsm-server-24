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
import team.themoment.hellogsmv3.domain.member.service.MemberService;
import team.themoment.hellogsmv3.domain.oneseo.entity.MiddleSchoolAchievement;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;
import team.themoment.hellogsmv3.domain.oneseo.entity.OneseoPrivacyDetail;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo;
import team.themoment.hellogsmv3.domain.oneseo.repository.MiddleSchoolAchievementRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoPrivacyDetailRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@DisplayName("DeleteOneseoService 클래스의")
public class DeleteOneseoServiceTest {

    @Mock
    private MemberService memberService;
    @Mock
    private OneseoService oneseoService;
    @Mock
    private OneseoRepository oneseoRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private OneseoPrivacyDetailRepository oneseoPrivacyDetailRepository;
    @Mock
    private MiddleSchoolAchievementRepository middleSchoolAchievementRepository;

    @InjectMocks
    private DeleteOneseoService deleteOneseoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("execute 메소드는")
    class Describe_execute {

        private final Long memberId = 1L;

        @Nested
        @DisplayName("존재하는 회원 ID가 주어지면")
        class Context_with_existing_member_id {
            private Oneseo oneseo;

            void setUp(Member member, Oneseo oneseo) {
                OneseoPrivacyDetail oneseoPrivacyDetail = OneseoPrivacyDetail.builder()
                        .oneseo(oneseo)
                        .build();

                MiddleSchoolAchievement middleSchoolAchievement = MiddleSchoolAchievement.builder()
                        .oneseo(oneseo)
                        .build();

                given(oneseoPrivacyDetailRepository.findByOneseo(oneseo)).willReturn(oneseoPrivacyDetail);
                given(middleSchoolAchievementRepository.findByOneseo(oneseo)).willReturn(middleSchoolAchievement);
                given(memberService.findByIdOrThrow(memberId)).willReturn(member);
                given(oneseoService.findByMemberOrThrow(member)).willReturn(oneseo);
            }

            @Test
            @DisplayName("원서가 최종 제출되지 않았으면 삭제한다.")
            void it_delete_oneseo_if_not_final_submitted() {
                Member member = Member.builder()
                        .id(memberId)
                        .build();

                oneseo = Oneseo.builder()
                        .member(member)
                        .finalSubmittedYn(YesNo.NO)
                        .build();

                setUp(member, oneseo);

                deleteOneseoService.execute(memberId);

                verify(oneseoRepository, times(1)).delete(oneseo);
            }

            @Test
            @DisplayName("원서가 최종 제출 되었으면 ExpectedException을 던진다.")
            void it_throw_exception_if_oneseo_is_final_submitted() {
                Member member = Member.builder()
                        .id(memberId)
                        .build();

                oneseo = Oneseo.builder()
                        .member(member)
                        .finalSubmittedYn(YesNo.YES)
                        .build();

                setUp(member, oneseo);

                ExpectedException exception = assertThrows(ExpectedException.class, () ->
                        deleteOneseoService.execute(memberId)
                );

                assertEquals("최종제출을 완료한 원서입니다.", exception.getMessage());
                assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());

                verify(oneseoRepository, times(0)).delete(oneseo);
            }
        }

        @Nested
        @DisplayName("존재하지 않는 회원 ID가 주어지면")
        class Context_with_non_existing_member_id {

            @BeforeEach
            void setUp() {
                given(memberService.findByIdOrThrow(memberId)).willThrow(
                        new ExpectedException("존재하지 않는 지원자입니다. member ID: " + memberId, HttpStatus.NOT_FOUND));
            }

            @Test
            @DisplayName("ExpectedException을 던진다.")
            void it_throws_expected_exception() {
                ExpectedException exception = assertThrows(ExpectedException.class, () -> {
                    deleteOneseoService.execute(memberId);
                });

                assertEquals("존재하지 않는 지원자입니다. member ID: " + memberId, exception.getMessage());
                assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
            }
        }
    }
}
