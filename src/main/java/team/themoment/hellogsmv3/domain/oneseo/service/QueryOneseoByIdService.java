package team.themoment.hellogsmv3.domain.oneseo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.member.service.MemberService;
import team.themoment.hellogsmv3.domain.oneseo.dto.response.*;
import team.themoment.hellogsmv3.domain.oneseo.entity.*;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.DesiredMajors;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.GraduationType;
import team.themoment.hellogsmv3.domain.oneseo.repository.MiddleSchoolAchievementRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoPrivacyDetailRepository;

import java.util.List;

import static team.themoment.hellogsmv3.domain.oneseo.service.OneseoService.ONESEO_CACHE_VALUE;

@Service
@RequiredArgsConstructor
public class QueryOneseoByIdService {

    private final OneseoPrivacyDetailRepository oneseoPrivacyDetailRepository;
    private final MiddleSchoolAchievementRepository middleSchoolAchievementRepository;
    private final MemberService memberService;
    private final OneseoService oneseoService;

    @Cacheable(value = ONESEO_CACHE_VALUE, key = "#memberId")
    public FoundOneseoResDto execute(Long memberId) {
        Member member = memberService.findByIdOrThrow(memberId);
        Oneseo oneseo = oneseoService.findByMemberOrThrow(member);
        OneseoPrivacyDetail oneseoPrivacyDetail = oneseoPrivacyDetailRepository.findByOneseo(oneseo);
        MiddleSchoolAchievement middleSchoolAchievement = middleSchoolAchievementRepository.findByOneseo(oneseo);

        CalculatedScoreResDto calculatedScoreResDto = buildCalculatedScoreResDto(oneseo, oneseoPrivacyDetail.getGraduationType());

        OneseoPrivacyDetailResDto oneseoPrivacyDetailResDto = buildOneseoPrivacyDetailResDto(member, oneseoPrivacyDetail);
        MiddleSchoolAchievementResDto middleSchoolAchievementResDto = buildMiddleSchoolAchievementResDto(middleSchoolAchievement);

        return buildFoundOneseoResDto(
                oneseo,
                oneseoPrivacyDetailResDto,
                middleSchoolAchievementResDto,
                calculatedScoreResDto
        );
    }
    private CalculatedScoreResDto buildCalculatedScoreResDto(Oneseo oneseo, GraduationType graduationType) {
        EntranceTestResult entranceTestResult = oneseo.getEntranceTestResult();
        EntranceTestFactorsDetail entranceTestFactorsDetail = entranceTestResult.getEntranceTestFactorsDetail();

        return switch (graduationType) {
            case CANDIDATE, GRADUATE ->
                CalculatedScoreResDto.builder()
                        .generalSubjectsScore(entranceTestFactorsDetail.getGeneralSubjectsScore())
                        .artsPhysicalSubjectsScore(entranceTestFactorsDetail.getArtsPhysicalSubjectsScore())
                        .attendanceScore(entranceTestFactorsDetail.getAttendanceScore())
                        .volunteerScore(entranceTestFactorsDetail.getVolunteerScore())
                        .totalScore(entranceTestResult.getDocumentEvaluationScore())
                        .build();
            case GED ->
                CalculatedScoreResDto.builder()
                        .totalSubjectsScore(entranceTestFactorsDetail.getTotalSubjectsScore())
                        .attendanceScore(entranceTestFactorsDetail.getAttendanceScore())
                        .volunteerScore(entranceTestFactorsDetail.getVolunteerScore())
                        .totalScore(entranceTestResult.getDocumentEvaluationScore())
                        .build();
        };
    }

    private OneseoPrivacyDetailResDto buildOneseoPrivacyDetailResDto(
            Member member,
            OneseoPrivacyDetail oneseoPrivacyDetail
    ) {
        return OneseoPrivacyDetailResDto.builder()
                .name(member.getName())
                .sex(member.getSex())
                .birth(member.getBirth())
                .phoneNumber(member.getPhoneNumber())
                .graduationType(oneseoPrivacyDetail.getGraduationType())
                .graduationDate(oneseoPrivacyDetail.getGraduationDate())
                .address(oneseoPrivacyDetail.getAddress())
                .detailAddress(oneseoPrivacyDetail.getDetailAddress())
                .guardianName(oneseoPrivacyDetail.getGuardianName())
                .guardianPhoneNumber(oneseoPrivacyDetail.getGuardianPhoneNumber())
                .relationshipWithGuardian(oneseoPrivacyDetail.getRelationshipWithGuardian())
                .schoolName(oneseoPrivacyDetail.getSchoolName())
                .schoolAddress(oneseoPrivacyDetail.getSchoolAddress())
                .schoolTeacherName(oneseoPrivacyDetail.getSchoolTeacherName())
                .schoolTeacherPhoneNumber(oneseoPrivacyDetail.getSchoolTeacherPhoneNumber())
                .profileImg(oneseoPrivacyDetail.getProfileImg())
                .build();
    }

    private MiddleSchoolAchievementResDto buildMiddleSchoolAchievementResDto(
            MiddleSchoolAchievement middleSchoolAchievement
    ) {

        List<Integer> absentDays = middleSchoolAchievement.getAbsentDays();
        List<Integer> attendanceDays = middleSchoolAchievement.getAttendanceDays();
        Integer absentDaysCount = OneseoService.calcAbsentDaysCount(absentDays, attendanceDays);

        return MiddleSchoolAchievementResDto.builder()
                .achievement1_2(middleSchoolAchievement.getAchievement1_2())
                .achievement2_1(middleSchoolAchievement.getAchievement2_1())
                .achievement2_2(middleSchoolAchievement.getAchievement2_2())
                .achievement3_1(middleSchoolAchievement.getAchievement3_1())
                .achievement3_2(middleSchoolAchievement.getAchievement3_2())
                .generalSubjects(middleSchoolAchievement.getGeneralSubjects())
                .newSubjects(middleSchoolAchievement.getNewSubjects())
                .artsPhysicalAchievement(middleSchoolAchievement.getArtsPhysicalAchievement())
                .artsPhysicalSubjects(middleSchoolAchievement.getArtsPhysicalSubjects())
                .absentDays(absentDays)
                .absentDaysCount(absentDaysCount)
                .attendanceDays(attendanceDays)
                .volunteerTime(middleSchoolAchievement.getVolunteerTime())
                .liberalSystem(middleSchoolAchievement.getLiberalSystem())
                .freeSemester(middleSchoolAchievement.getFreeSemester())
                .gedTotalScore(middleSchoolAchievement.getGedTotalScore())
                .build();
    }

    private FoundOneseoResDto buildFoundOneseoResDto(
            Oneseo oneseo,
            OneseoPrivacyDetailResDto oneseoPrivacyDetailResDto,
            MiddleSchoolAchievementResDto middleSchoolAchievementResDto,
            CalculatedScoreResDto calculatedScoreResDto
    ) {
        DesiredMajors desiredMajors = oneseo.getDesiredMajors();

        return FoundOneseoResDto.builder()
                .oneseoId(oneseo.getId())
                .submitCode(oneseo.getOneseoSubmitCode())
                .wantedScreening(oneseo.getWantedScreening())
                .desiredMajors(DesiredMajorsResDto.builder()
                        .firstDesiredMajor(desiredMajors.getFirstDesiredMajor())
                        .secondDesiredMajor(desiredMajors.getSecondDesiredMajor())
                        .thirdDesiredMajor(desiredMajors.getThirdDesiredMajor())
                        .build())
                .privacyDetail(oneseoPrivacyDetailResDto)
                .middleSchoolAchievement(middleSchoolAchievementResDto)
                .calculatedScoreResDto(calculatedScoreResDto)
                .build();
    }
}
