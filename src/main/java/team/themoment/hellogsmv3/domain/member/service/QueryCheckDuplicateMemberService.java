package team.themoment.hellogsmv3.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.member.dto.response.FoundDuplicateMemberResDto;
import team.themoment.hellogsmv3.domain.member.repo.MemberRepository;

import static team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo.*;

@Service
@RequiredArgsConstructor
public class QueryCheckDuplicateMemberService {

    private final MemberRepository memberRepository;

    public FoundDuplicateMemberResDto execute(String phoneNumber) {
        return new FoundDuplicateMemberResDto(
                memberRepository.existsByPhoneNumber(phoneNumber)
                    ? YES
                    : NO
        );
    }
}
