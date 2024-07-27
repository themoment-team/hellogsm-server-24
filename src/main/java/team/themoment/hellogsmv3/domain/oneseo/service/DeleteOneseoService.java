package team.themoment.hellogsmv3.domain.oneseo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.member.service.MemberService;
import team.themoment.hellogsmv3.domain.oneseo.entity.MiddleSchoolAchievement;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;
import team.themoment.hellogsmv3.domain.oneseo.entity.OneseoPrivacyDetail;
import team.themoment.hellogsmv3.domain.oneseo.repository.MiddleSchoolAchievementRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoPrivacyDetailRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

import static team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo.YES;

@Service
@RequiredArgsConstructor
public class DeleteOneseoService {

    private final MemberService memberService;
    private final OneseoService oneseoService;
    private final OneseoRepository oneseoRepository;
    private final OneseoPrivacyDetailRepository oneseoPrivacyDetailRepository;
    private final MiddleSchoolAchievementRepository middleSchoolAchievementRepository;

    @Transactional
    public void execute(Long memberId) {
        Member member = memberService.findByIdOrThrow(memberId);
        Oneseo oneseo = oneseoService.findByMemberOrThrow(member);

        if (oneseo.getFinalSubmittedYn().equals(YES)) {
            throw new ExpectedException("최종제출을 완료한 원서입니다.", HttpStatus.BAD_REQUEST);
        }

        OneseoPrivacyDetail oneseoPrivacyDetail = oneseoPrivacyDetailRepository.findByOneseo(oneseo);
        MiddleSchoolAchievement middleSchoolAchievement = middleSchoolAchievementRepository.findByOneseo(oneseo);

        oneseoPrivacyDetailRepository.delete(oneseoPrivacyDetail);
        middleSchoolAchievementRepository.delete(middleSchoolAchievement);
        oneseoRepository.delete(oneseo);
    }
}
