package team.themoment.hellogsmv3.domain.oneseo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.member.service.MemberService;
import team.themoment.hellogsmv3.domain.oneseo.dto.request.MiddleSchoolAchievementReqDto;
import team.themoment.hellogsmv3.domain.oneseo.dto.request.OneseoReqDto;
import team.themoment.hellogsmv3.domain.oneseo.entity.MiddleSchoolAchievement;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;
import team.themoment.hellogsmv3.domain.oneseo.entity.OneseoPrivacyDetail;
import team.themoment.hellogsmv3.domain.oneseo.entity.ScreeningChangeHistory;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.DesiredMajors;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.GraduationType;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening;
import team.themoment.hellogsmv3.domain.oneseo.repository.MiddleSchoolAchievementRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoPrivacyDetailRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.ScreeningChangeHistoryRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModifyOneseoService {

    private final OneseoRepository oneseoRepository;
    private final OneseoPrivacyDetailRepository oneseoPrivacyDetailRepository;
    private final MiddleSchoolAchievementRepository middleSchoolAchievementRepository;
    private final ScreeningChangeHistoryRepository screeningChangeHistoryRepository;
    private final OneseoService oneseoService;
    private final MemberService memberService;
    private final CalculateGradeService calculateGradeService;
    private final CalculateGedService calculateGedService;

    @Transactional
    public void execute(OneseoReqDto reqDto, Long memberId) {
        Member currentMember = memberService.findByIdOrThrow(memberId);
        Oneseo oneseo = oneseoService.findByMemberOrThrow(currentMember);

        OneseoPrivacyDetail oneseoPrivacyDetail = oneseoPrivacyDetailRepository.findByOneseo(oneseo);
        MiddleSchoolAchievement middleSchoolAchievement = middleSchoolAchievementRepository.findByOneseo(oneseo);

        saveHistoryIfScreeningChange(reqDto.screening(), oneseo);

        Oneseo modifiedOneseo = buildOneseo(reqDto, oneseo, currentMember);
        OneseoPrivacyDetail modifiedOneseoPrivacyDetail = buildOneseoPrivacyDetail(reqDto, oneseoPrivacyDetail, oneseo);
        MiddleSchoolAchievement modifiedMiddleSchoolAchievement = buildMiddleSchoolAchievement(reqDto, middleSchoolAchievement, oneseo);

        saveModifiedEntities(modifiedOneseo, modifiedOneseoPrivacyDetail, modifiedMiddleSchoolAchievement);

        calculateMiddleSchoolAchievement(oneseoPrivacyDetail.getGraduationType(), middleSchoolAchievement, oneseo);
    }

    private void calculateMiddleSchoolAchievement(GraduationType graduationType, MiddleSchoolAchievement middleSchoolAchievement, Oneseo oneseo) {
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

    private Oneseo buildOneseo(OneseoReqDto reqDto, Oneseo oneseo, Member currentMember) {
        return Oneseo.builder()
                .id(oneseo.getId())
                .member(currentMember)
                .desiredMajors(DesiredMajors.builder()
                        .firstDesiredMajor(reqDto.firstDesiredMajor())
                        .secondDesiredMajor(reqDto.secondDesiredMajor())
                        .thirdDesiredMajor(reqDto.thirdDesiredMajor())
                        .build())
                .realOneseoArrivedYn(oneseo.getRealOneseoArrivedYn())
                .wantedScreening(reqDto.screening())
                .appliedScreening(reqDto.screening())
                .build();
    }

    private OneseoPrivacyDetail buildOneseoPrivacyDetail(OneseoReqDto reqDto, OneseoPrivacyDetail oneseoPrivacyDetail, Oneseo oneseo) {
        return OneseoPrivacyDetail.builder()
                .id(oneseoPrivacyDetail.getId())
                .oneseo(oneseo)
                .graduationType(reqDto.graduationType())
                .address(reqDto.address())
                .detailAddress(reqDto.detailAddress())
                .profileImg(reqDto.profileImg())
                .guardianName(reqDto.guardianName())
                .guardianPhoneNumber(reqDto.guardianPhoneNumber())
                .relationshipWithGuardian(reqDto.relationshipWithGuardian())
                .schoolAddress(reqDto.schoolAddress())
                .schoolName(reqDto.schoolName())
                .schoolTeacherName(reqDto.schoolTeacherName())
                .schoolTeacherPhoneNumber(reqDto.schoolTeacherPhoneNumber())
                .build();
    }

    private MiddleSchoolAchievement buildMiddleSchoolAchievement(OneseoReqDto reqDto, MiddleSchoolAchievement middleSchoolAchievement, Oneseo oneseo) {
        MiddleSchoolAchievementReqDto updatedMiddleSchoolAchievement = reqDto.middleSchoolAchievement();

        return MiddleSchoolAchievement.builder()
                .id(middleSchoolAchievement.getId())
                .oneseo(oneseo)
                .achievement1_2(validationGeneralAchievement(updatedMiddleSchoolAchievement.achievement1_2()))
                .achievement2_1(validationGeneralAchievement(updatedMiddleSchoolAchievement.achievement2_1()))
                .achievement2_2(validationGeneralAchievement(updatedMiddleSchoolAchievement.achievement2_2()))
                .achievement3_1(validationGeneralAchievement(updatedMiddleSchoolAchievement.achievement3_1()))
                .achievement3_2(validationGeneralAchievement(updatedMiddleSchoolAchievement.achievement3_2()))
                .generalSubjects(updatedMiddleSchoolAchievement.generalSubjects())
                .newSubjects(updatedMiddleSchoolAchievement.newSubjects())
                .artsPhysicalAchievement(validationArtsPhysicalAchievement(updatedMiddleSchoolAchievement.artsPhysicalAchievement()))
                .artsPhysicalSubjects(updatedMiddleSchoolAchievement.artsPhysicalSubjects())
                .absentDays(updatedMiddleSchoolAchievement.absentDays())
                .attendanceDays(updatedMiddleSchoolAchievement.attendanceDays())
                .volunteerTime(updatedMiddleSchoolAchievement.volunteerTime())
                .liberalSystem(updatedMiddleSchoolAchievement.liberalSystem())
                .freeSemester(updatedMiddleSchoolAchievement.freeSemester())
                .gedTotalScore(updatedMiddleSchoolAchievement.gedTotalScore())
                .gedMaxScore(updatedMiddleSchoolAchievement.gedMaxScore())
                .build();
    }

    private void saveModifiedEntities(Oneseo modifiedOneseo, OneseoPrivacyDetail modifiedOneseoPrivacyDetail, MiddleSchoolAchievement modifiedMiddleSchoolAchievement) {
        oneseoRepository.save(modifiedOneseo);
        oneseoPrivacyDetailRepository.save(modifiedOneseoPrivacyDetail);
        middleSchoolAchievementRepository.save(modifiedMiddleSchoolAchievement);
    }

    private void saveHistoryIfScreeningChange(Screening afterScreening, Oneseo oneseo) {
        if (oneseo.getAppliedScreening() != afterScreening) {
            ScreeningChangeHistory screeningChangeHistory = ScreeningChangeHistory.builder()
                    .beforeScreening(oneseo.getAppliedScreening())
                    .afterScreening(afterScreening)
                    .oneseo(oneseo).build();

            screeningChangeHistoryRepository.save(screeningChangeHistory);
        }
    }

    private List<Integer> validationGeneralAchievement(List<Integer> achievements)  {
        if (achievements == null) return null;

        achievements.forEach(achievement -> {
            if (achievement > 5 || achievement < 0) throw new ExpectedException("올바르지 않은 일반교과 등급이 입력되었습니다.", HttpStatus.BAD_REQUEST);
        });

        return achievements;
    }

    private List<Integer> validationArtsPhysicalAchievement(List<Integer> achievements)  {
        if (achievements == null) return null;

        achievements.forEach(achievement -> {
            if (achievement != 0 && (achievement > 5 || achievement < 3)) throw new ExpectedException("올바르지 않은 예체능 등급이 입력되었습니다.", HttpStatus.BAD_REQUEST);
        });

        return achievements;
    }

}
