package team.themoment.hellogsmv3.domain.oneseo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.member.repo.MemberRepository;
import team.themoment.hellogsmv3.domain.member.service.MemberService;
import team.themoment.hellogsmv3.domain.oneseo.dto.request.MiddleSchoolAchievementReqDto;
import team.themoment.hellogsmv3.domain.oneseo.entity.MiddleSchoolAchievement;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;
import team.themoment.hellogsmv3.domain.oneseo.entity.OneseoPrivacyDetail;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.GraduationType;
import team.themoment.hellogsmv3.domain.oneseo.repository.MiddleSchoolAchievementRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoPrivacyDetailRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

import static team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo.*;

@Service
@RequiredArgsConstructor
public class UpdateFinalSubmissionService {

    private final OneseoPrivacyDetailRepository oneseoPrivacyDetailRepository;
    private final MiddleSchoolAchievementRepository middleSchoolAchievementRepository;
    private final CalculateGradeService calculateGradeService;
    private final CalculateGedService calculateGedService;
    private final MemberService memberService;
    private final OneseoService oneseoService;

    @Transactional
    public void execute(Long memberId) {
        Member currentMember = memberService.findByIdOrThrow(memberId);
        Oneseo oneseo = oneseoService.findByMemberOrThrow(currentMember);

        if (oneseo.getFinalSubmittedYn().equals(YES))
            throw new ExpectedException("이미 최종제출 되었습니다.", HttpStatus.BAD_REQUEST);

        oneseo.updateFinalSubmission();

        OneseoPrivacyDetail oneseoPrivacyDetail = oneseoPrivacyDetailRepository.findByOneseo(oneseo);
        MiddleSchoolAchievement middleSchoolAchievement = middleSchoolAchievementRepository.findByOneseo(oneseo);

        GraduationType graduationType = oneseoPrivacyDetail.getGraduationType();
        MiddleSchoolAchievementReqDto data = MiddleSchoolAchievementReqDto.builder()
                .achievement1_2(middleSchoolAchievement.getAchievement1_2())
                .achievement2_1(middleSchoolAchievement.getAchievement2_1())
                .achievement2_2(middleSchoolAchievement.getAchievement2_2())
                .achievement3_1(middleSchoolAchievement.getAchievement3_1())
                .achievement3_2(middleSchoolAchievement.getAchievement3_2())
                .artsPhysicalAchievement(middleSchoolAchievement.getArtsPhysicalAchievement())
                .absentDays(middleSchoolAchievement.getAbsentDays())
                .attendanceDays(middleSchoolAchievement.getAttendanceDays())
                .volunteerTime(middleSchoolAchievement.getVolunteerTime())
                .liberalSystem(middleSchoolAchievement.getLiberalSystem())
                .freeSemester(middleSchoolAchievement.getFreeSemester())
                .gedTotalScore(middleSchoolAchievement.getGedTotalScore())
                .gedMaxScore(middleSchoolAchievement.getGedMaxScore())
                .build();

        switch (graduationType) {
            case CANDIDATE, GRADUATE -> calculateGradeService.execute(data, oneseo, graduationType);
            case GED -> calculateGedService.execute(data, oneseo, graduationType);
        }
    }

}
