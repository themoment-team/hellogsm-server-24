package team.themoment.hellogsmv3.domain.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import team.themoment.hellogsmv3.domain.member.dto.response.FoundMemberAuthInfoResDto;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.member.entity.type.AuthReferrerType;
import team.themoment.hellogsmv3.domain.member.entity.type.Role;
import team.themoment.hellogsmv3.domain.member.repo.MemberRepository;
import team.themoment.hellogsmv3.domain.member.service.QueryMemberAuthInfoByIdService;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@DisplayName("QueryMemberAuthInfoByIdService 클래스의")
public class QueryMemberAuthInfoByIdServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private QueryMemberAuthInfoByIdService queryMemberAuthInfoByIdService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("execute 메소드는")
    class Describe_execute {

        private final Long memberId = 1L;

        @Nested
        @DisplayName("존재하는 회원 ID가 주어지면")
        class Context_with_existing_member_id {

            @BeforeEach
            void setUp() {
                Member member = Member.builder()
                        .id(memberId)
                        .email("email@email.com")
                        .authReferrerType(AuthReferrerType.GOOGLE)
                        .role(Role.APPLICANT)
                        .build();
                given(memberRepository.findById(memberId)).willReturn(Optional.of(member));
            }

            @Test
            @DisplayName("회원 인증 정보를 반환한다.")
            void it_return_member_auth_info() {
                FoundMemberAuthInfoResDto result = queryMemberAuthInfoByIdService.execute(memberId);

                assertEquals(memberId, result.memberId());
                assertEquals("email@email.com", result.email());
                assertEquals(AuthReferrerType.GOOGLE, result.authReferrerType());
                assertEquals(Role.APPLICANT, result.role());
                assertEquals(Role.APPLICANT, result.role());
            }
        }

        @Nested
        @DisplayName("존재하지 않는 회원 ID가 주어지면")
        class Context_with_non_existing_member_id {

            @BeforeEach
            void setUp() {
                given(memberRepository.findById(memberId)).willReturn(Optional.empty());
            }

            @Test
            @DisplayName("ExpectedException을 던진다")
            void it_throws_expected_exception() {
                ExpectedException exception = assertThrows(ExpectedException.class, () -> queryMemberAuthInfoByIdService.execute(memberId));

                assertEquals("존재하지 않는 지원자입니다. member ID: " + memberId, exception.getMessage());
                assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
            }
        }
    }
}
