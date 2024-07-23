package team.themoment.hellogsmv3.domain.member.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import team.themoment.hellogsmv3.domain.member.dto.request.ModifyMemberReqDto;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.member.entity.type.AuthReferrerType;
import team.themoment.hellogsmv3.domain.member.entity.type.Role;
import team.themoment.hellogsmv3.domain.member.entity.type.Sex;
import team.themoment.hellogsmv3.domain.member.repo.MemberRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.then;

@DisplayName("ModifyMemberService 클래스의")
class ModifyMemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private CommonCodeService commonCodeService;

    @InjectMocks
    private ModifyMemberService modifyMemberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("execute 메소드는")
    class Describe_execute {

        private final Long memberId = 1L;
        private final ModifyMemberReqDto reqDto = new ModifyMemberReqDto(
                "validCode",
                "최장우",
                "01012345678",
                Sex.MALE,
                LocalDate.of(2006, 3, 6)
        );

        @Nested
        @DisplayName("유효한 회원 ID와 요청 데이터가 주어지면")
        class Context_with_valid_member_id_and_request_data {

            private Member existingMember;

            @BeforeEach
            void setUp() {
                existingMember = Member.builder()
                        .id(memberId)
                        .email("jangwooooo@example.com")
                        .authReferrerType(AuthReferrerType.GOOGLE)
                        .name("이승제")
                        .birth(LocalDate.of(2006, 5, 10))
                        .phoneNumber("01012345678")
                        .sex(Sex.MALE)
                        .role(Role.APPLICANT)
                        .createdTime(LocalDateTime.now())
                        .updatedTime(LocalDateTime.now())
                        .build();

                given(memberRepository.findById(memberId)).willReturn(Optional.of(existingMember));
                willDoNothing().given(commonCodeService).validateAndDelete(memberId, reqDto.code(), reqDto.phoneNumber());
            }

            @Test
            @DisplayName("회원 정보를 수정하고 저장한다")
            void it_modifies_and_saves_member() {
                modifyMemberService.execute(reqDto, memberId);

                Member modifiedMember = existingMember.modifyMember(
                        reqDto.name(),
                        reqDto.birth(),
                        reqDto.phoneNumber(),
                        reqDto.sex()
                );

                then(memberRepository).should().save(modifiedMember);
            }
        }
    }
}
