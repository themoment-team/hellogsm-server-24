package team.themoment.hellogsmv3.domain.oneseo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.member.service.MemberService;
import team.themoment.hellogsmv3.domain.oneseo.dto.request.EntranceIntentionReqDto;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoRepository;

@Service
@RequiredArgsConstructor
public class ModifyEntranceIntentionService {

    private final OneseoRepository oneseoRepository;
    private final MemberService memberService;
    private final OneseoService oneseoService;

    @Transactional
    public void execute(Long memberId, EntranceIntentionReqDto reqDto) {
        Member member = memberService.findByIdOrThrow(memberId);
        Oneseo oneseo = oneseoService.findByMemberOrThrow(member);

        oneseo.modifyEntranceIntentionYn(reqDto.entranceIntentionYn());

        oneseoRepository.save(oneseo);
    }
}
