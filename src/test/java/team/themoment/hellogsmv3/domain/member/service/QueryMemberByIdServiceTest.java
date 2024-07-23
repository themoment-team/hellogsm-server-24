package team.themoment.hellogsmv3.domain.member.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import team.themoment.hellogsmv3.domain.member.dto.response.FoundMemberResDto;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.member.entity.type.Sex;
import team.themoment.hellogsmv3.domain.member.repo.MemberRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@DisplayName("QueryMemberByIdService 클래스의")
class QueryMemberByIdServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private QueryMemberByIdService queryMemberByIdService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("execute 메소드는")
    class Describe_execute {

        @Nested
        @DisplayName("존재하는 회원 ID가 주어지면")
        class Context_with_existing_member_id {

            private final Long memberId = 1L;
            private Member member;

            @BeforeEach
            void setUp() {
                member = Member.builder()
                        .id(memberId)
                        .name("최장우")
                        .birth(LocalDate.of(2006, 3, 6))
                        .phoneNumber("01012345678")
                        .sex(Sex.MALE)
                        .build();

                given(memberRepository.findById(memberId)).willReturn(Optional.of(member));
            }

            @Test
            @DisplayName("회원 정보를 반환한다")
            void it_returns_member_info() {
                FoundMemberResDto result = queryMemberByIdService.execute(memberId);

                assertEquals(memberId, result.memberId());
                assertEquals("최장우", result.name());
                assertEquals(LocalDate.of(2006, 3, 6), result.birth());
                assertEquals("01012345678", result.phoneNumber());
                assertEquals(Sex.MALE, result.sex());
            }
        }
    }
}
