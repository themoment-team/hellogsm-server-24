package team.themoment.hellogsmv3.domain.oneseo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.member.repo.MemberRepository;
import team.themoment.hellogsmv3.domain.oneseo.dto.request.MiddleSchoolAchievementReqDto;
import team.themoment.hellogsmv3.domain.oneseo.dto.request.OneseoReqDto;
import team.themoment.hellogsmv3.domain.oneseo.entity.MiddleSchoolAchievement;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;
import team.themoment.hellogsmv3.domain.oneseo.entity.OneseoPrivacyDetail;
import team.themoment.hellogsmv3.domain.oneseo.entity.ScreeningChangeHistory;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.DesiredMajors;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening;
import team.themoment.hellogsmv3.domain.oneseo.repository.MiddleSchoolAchievementRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoPrivacyDetailRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.ScreeningChangeHistoryRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

import java.math.BigDecimal;
import java.util.List;

import static team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo.*;

@Service
@RequiredArgsConstructor
public class ModifyOneseoService {

    private final MemberRepository memberRepository;
    private final OneseoRepository oneseoRepository;
    private final OneseoPrivacyDetailRepository oneseoPrivacyDetailRepository;
    private final MiddleSchoolAchievementRepository middleSchoolAchievementRepository;
    private final ScreeningChangeHistoryRepository screeningChangeHistoryRepository;

    @Transactional
    public void execute(OneseoReqDto reqDto, Long memberId, boolean isAdmin) {
        Member currentMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new ExpectedException("존재하지 않는 지원자입니다. member ID: " + memberId, HttpStatus.NOT_FOUND));
        Oneseo oneseo = oneseoRepository.findByMember(currentMember)
                .orElseThrow(() -> new ExpectedException("원서 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        isNotFinalSubmitted(isAdmin, oneseo);

        OneseoPrivacyDetail oneseoPrivacyDetail = oneseoPrivacyDetailRepository.findByOneseo(oneseo);
        MiddleSchoolAchievement middleSchoolAchievement = middleSchoolAchievementRepository.findByOneseo(oneseo);

        saveHistoryIfScreeningChange(reqDto.screening(), oneseo);

        Oneseo modifiedOneseo = buildOneseo(reqDto, oneseo, currentMember);
        OneseoPrivacyDetail modifiedOneseoPrivacyDetail = buildOneseoPrivacyDetail(reqDto, oneseoPrivacyDetail, oneseo);
        MiddleSchoolAchievement modifiedMiddleSchoolAchievement = buildMiddleSchoolAchievement(reqDto, middleSchoolAchievement, oneseo);

        saveModifiedEntities(modifiedOneseo, modifiedOneseoPrivacyDetail, modifiedMiddleSchoolAchievement);
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
                .finalSubmittedYn(oneseo.getFinalSubmittedYn())
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

    private void isNotFinalSubmitted(boolean isAdmin, Oneseo oneseo) {
        if (!isAdmin && oneseo.getFinalSubmittedYn().equals(YES))
            throw new ExpectedException("최종제출이 완료된 원서는 수정할 수 없습니다.", HttpStatus.BAD_REQUEST);
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
            if (achievement > 5 || achievement < 3) throw new ExpectedException("올바르지 않은 예체능 등급이 입력되었습니다.", HttpStatus.BAD_REQUEST);
        });

        return achievements;
    }

}
