package team.themoment.hellogsmv3.domain.member.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import team.themoment.hellogsmv3.domain.member.repo.MemberRepository;

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
}
