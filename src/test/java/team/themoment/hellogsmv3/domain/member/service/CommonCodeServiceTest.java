package team.themoment.hellogsmv3.domain.member.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import team.themoment.hellogsmv3.domain.member.entity.AuthenticationCode;
import team.themoment.hellogsmv3.domain.member.repo.CodeRepository;

import java.time.LocalDateTime;
import java.util.Optional;

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
    }
}
