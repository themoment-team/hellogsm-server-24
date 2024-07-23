package team.themoment.hellogsmv3.domain.member.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import team.themoment.hellogsmv3.domain.member.entity.AuthenticationCode;
import team.themoment.hellogsmv3.domain.member.repo.CodeRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@DisplayName("CommonCodeService 클래스의")
class CommonCodeServiceTest {

    @Mock
    private CodeRepository codeRepository;

    @InjectMocks
    private CommonCodeService commonCodeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("validateAndDelete 메소드는")
    class Describe_validateAndDelete {

        private final Long memberId = 1L;
        private final String validCode = "validCode";
        private final String validPhoneNumber = "010-1234-5678";
        private AuthenticationCode authenticationCode;

        @BeforeEach
        void setUp() {
            authenticationCode = new AuthenticationCode(
                    memberId,
                    validCode,
                    validPhoneNumber,
                    LocalDateTime.now()
            );
        }

        @Nested
        @DisplayName("유효한 회원 ID와 코드와 전화번호가 주어지면")
        class Context_with_valid_code_and_phone_number {

            @BeforeEach
            void setUp() {
                authenticationCode.authenticatedAuthenticationCode();
                given(codeRepository.findByMemberId(memberId)).willReturn(Optional.of(authenticationCode));
            }

            @Test
            @DisplayName("코드를 삭제한다")
            void it_deletes_the_code() {
                commonCodeService.validateAndDelete(memberId, validCode, validPhoneNumber);

                verify(codeRepository).delete(authenticationCode);
            }
        }

        @Nested
        @DisplayName("인증 코드가 존재하지 않으면")
        class Context_with_non_existing_code {

            @BeforeEach
            void setUp() {
                given(codeRepository.findByMemberId(anyLong())).willReturn(Optional.empty());
            }

            @Test
            @DisplayName("ExpectedException을 던진다")
            void it_throws_expected_exception() {
                ExpectedException exception = assertThrows(ExpectedException.class, () -> {
                    commonCodeService.validateAndDelete(memberId, validCode, validPhoneNumber);
                });

                assertEquals("사용자의 code가 존재하지 않습니다. 사용자의 ID : " + memberId, exception.getMessage());
                assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
            }
        }
    }
}
