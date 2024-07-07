package team.themoment.hellogsmv3.domain.oneseo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.member.repo.MemberRepository;
import team.themoment.hellogsmv3.domain.oneseo.dto.response.FoundOneseoResDto;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoRepository;

@Service
@RequiredArgsConstructor
public class QueryOneseoByIdService {

    private final OneseoRepository oneseoRepository;
    private final MemberRepository memberRepository;

    public FoundOneseoResDto execute(Long memberId) {



        return buildFoundOneseoResDto();
    }

    private FoundOneseoResDto buildFoundOneseoResDto() {

    }
}
