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
import team.themoment.hellogsmv3.domain.oneseo.entity.type.DesiredMajors;
import team.themoment.hellogsmv3.domain.oneseo.repository.MiddleSchoolAchievementRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoPrivacyDetailRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

import static team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo.*;

@Service
@RequiredArgsConstructor
public class CreateOneseoService {

    private final MemberRepository memberRepository;
    private final OneseoRepository oneseoRepository;
    private final OneseoPrivacyDetailRepository oneseoPrivacyDetailRepository;
    private final MiddleSchoolAchievementRepository middleSchoolAchievementRepository;

    @Transactional
    public void execute(OneseoReqDto reqDto, Long memberId) {
        Member currentMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new ExpectedException("존재하지 않는 지원자입니다. member ID: " + memberId, HttpStatus.NOT_FOUND));

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
        MiddleSchoolAchievementReqDto transcript = reqDto.transcript();

        return MiddleSchoolAchievement.builder()
                .oneseo(oneseo)
                .achievement1_1(transcript.achievement1_1())
                .achievement1_2(transcript.achievement1_2())
                .achievement2_1(transcript.achievement2_1())
                .achievement2_2(transcript.achievement2_2())
                .achievement3_1(transcript.achievement3_1())
                .generalSubjects(transcript.generalSubjects())
                .newSubjects(transcript.newSubjects())
                .artsPhysicalAchievement(transcript.artsPhysicalAchievement())
                .artsPhysicalSubjects(transcript.artsPhysicalSubjects())
                .absentDays(transcript.absentDays())
                .attendanceDays(transcript.attendanceDays())
                .volunteerTime(transcript.volunteerTime())
                .liberalSystem(transcript.liberalSystem())
                .freeSemester(transcript.freeSemester())
                .gedTotalScore(transcript.gedTotalScore())
                .gedMaxScore(transcript.gedMaxScore())
                .build();
    }

    private void isExistOneseo(Member currentMember) {
        if (oneseoRepository.existsByMember(currentMember)) {
            throw new ExpectedException("이미 원서가 존재합니다.", HttpStatus.BAD_REQUEST);
        }
    }

}
