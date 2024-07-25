package team.themoment.hellogsmv3.domain.oneseo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.member.repo.MemberRepository;
import team.themoment.hellogsmv3.domain.member.service.MemberService;
import team.themoment.hellogsmv3.domain.oneseo.dto.request.MiddleSchoolAchievementReqDto;
import team.themoment.hellogsmv3.domain.oneseo.dto.request.OneseoReqDto;
import team.themoment.hellogsmv3.domain.oneseo.entity.MiddleSchoolAchievement;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;
import team.themoment.hellogsmv3.domain.oneseo.entity.OneseoPrivacyDetail;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.DesiredMajors;
import team.themoment.hellogsmv3.domain.oneseo.repository.MiddleSchoolAchievementRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoPrivacyDetailRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

import java.util.List;

import static team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo.*;

@Service
@RequiredArgsConstructor
public class CreateOneseoService {

    private final OneseoRepository oneseoRepository;
    private final OneseoPrivacyDetailRepository oneseoPrivacyDetailRepository;
    private final MiddleSchoolAchievementRepository middleSchoolAchievementRepository;
    private final MemberService memberService;

    @Transactional
    public void execute(OneseoReqDto reqDto, Long memberId) {
        Member currentMember = memberService.findByIdOrThrow(memberId);

        isExistOneseo(currentMember);

        Oneseo oneseo = buildOneseo(reqDto, currentMember);
        OneseoPrivacyDetail oneseoPrivacyDetail = buildOneseoPrivacyDetail(reqDto, oneseo);
        MiddleSchoolAchievement middleSchoolAchievement = buildMiddleSchoolAchievement(reqDto, oneseo);

        saveEntities(oneseo, oneseoPrivacyDetail, middleSchoolAchievement);
    }

    private void saveEntities(Oneseo oneseo, OneseoPrivacyDetail oneseoPrivacyDetail, MiddleSchoolAchievement middleSchoolAchievement) {
        oneseoRepository.save(oneseo);
        oneseoPrivacyDetailRepository.save(oneseoPrivacyDetail);
        middleSchoolAchievementRepository.save(middleSchoolAchievement);
    }

    private Oneseo buildOneseo(OneseoReqDto reqDto, Member currentMember) {
        return Oneseo.builder()
                .member(currentMember)
                .desiredMajors(DesiredMajors.builder()
                        .firstDesiredMajor(reqDto.firstDesiredMajor())
                        .secondDesiredMajor(reqDto.secondDesiredMajor())
                        .thirdDesiredMajor(reqDto.thirdDesiredMajor())
                        .build())
                .realOneseoArrivedYn(NO)
                .finalSubmittedYn(NO)
                .appliedScreening(reqDto.screening())
                .build();
    }

    private OneseoPrivacyDetail buildOneseoPrivacyDetail(OneseoReqDto reqDto, Oneseo oneseo) {
        return OneseoPrivacyDetail.builder()
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

    private MiddleSchoolAchievement buildMiddleSchoolAchievement(OneseoReqDto reqDto, Oneseo oneseo) {
        MiddleSchoolAchievementReqDto middleSchoolAchievement = reqDto.middleSchoolAchievement();

        return MiddleSchoolAchievement.builder()
                .oneseo(oneseo)
                .achievement1_2(validationGeneralAchievement(middleSchoolAchievement.achievement1_2()))
                .achievement2_1(validationGeneralAchievement(middleSchoolAchievement.achievement2_1()))
                .achievement2_2(validationGeneralAchievement(middleSchoolAchievement.achievement2_2()))
                .achievement3_1(validationGeneralAchievement(middleSchoolAchievement.achievement3_1()))
                .achievement3_2(validationGeneralAchievement(middleSchoolAchievement.achievement3_2()))
                .generalSubjects(middleSchoolAchievement.generalSubjects())
                .newSubjects(middleSchoolAchievement.newSubjects())
                .artsPhysicalAchievement(validationArtsPhysicalAchievement(middleSchoolAchievement.artsPhysicalAchievement()))
                .artsPhysicalSubjects(middleSchoolAchievement.artsPhysicalSubjects())
                .absentDays(middleSchoolAchievement.absentDays())
                .attendanceDays(middleSchoolAchievement.attendanceDays())
                .volunteerTime(middleSchoolAchievement.volunteerTime())
                .liberalSystem(middleSchoolAchievement.liberalSystem())
                .freeSemester(middleSchoolAchievement.freeSemester())
                .gedTotalScore(middleSchoolAchievement.gedTotalScore())
                .gedMaxScore(middleSchoolAchievement.gedMaxScore())
                .build();
    }

    private void isExistOneseo(Member currentMember) {
        if (oneseoRepository.existsByMember(currentMember)) {
            throw new ExpectedException("이미 원서가 존재합니다.", HttpStatus.BAD_REQUEST);
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
