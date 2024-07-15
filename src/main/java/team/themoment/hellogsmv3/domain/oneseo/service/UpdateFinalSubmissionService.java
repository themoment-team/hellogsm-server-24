package team.themoment.hellogsmv3.domain.oneseo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.member.repo.MemberRepository;
import team.themoment.hellogsmv3.domain.oneseo.dto.MiddleSchoolAchievementData;
import team.themoment.hellogsmv3.domain.oneseo.entity.MiddleSchoolAchievement;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;
import team.themoment.hellogsmv3.domain.oneseo.entity.OneseoPrivacyDetail;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.GraduationType;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo;
import team.themoment.hellogsmv3.domain.oneseo.repository.MiddleSchoolAchievementRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoPrivacyDetailRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

import static team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo.*;

@Service
@RequiredArgsConstructor
public class UpdateFinalSubmissionService {

    private final MemberRepository memberRepository;
    private final OneseoRepository oneseoRepository;
    private final OneseoPrivacyDetailRepository oneseoPrivacyDetailRepository;
    private final MiddleSchoolAchievementRepository middleSchoolAchievementRepository;
    private final CalculateGradeService calculateGradeService;
    private final CalculateGedService calculateGedService;

    @Transactional
    public void execute(Long memberId) {
        Member currentMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new ExpectedException("존재하지 않는 지원자입니다. member ID: " + memberId, HttpStatus.NOT_FOUND));
        Oneseo oneseo = oneseoRepository.findByMember(currentMember)
                .orElseThrow(() -> new ExpectedException("원서 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        if (oneseo.getFinalSubmittedYn().equals(YES))
            throw new ExpectedException("이미 최종제출 되었습니다.", HttpStatus.BAD_REQUEST);

        oneseo.updateFinalSubmission();

        OneseoPrivacyDetail oneseoPrivacyDetail = oneseoPrivacyDetailRepository.findByOneseo(oneseo);
        MiddleSchoolAchievement middleSchoolAchievement = middleSchoolAchievementRepository.findByOneseo(oneseo);

        GraduationType graduationType = oneseoPrivacyDetail.getGraduationType();
        MiddleSchoolAchievementData data = MiddleSchoolAchievementData.builder()
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
