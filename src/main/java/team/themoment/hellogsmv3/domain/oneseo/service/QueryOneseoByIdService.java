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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static team.themoment.hellogsmv3.domain.oneseo.entity.type.GraduationType.CANDIDATE;
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

        GeneralSubjectsScoreDetailResDto generalSubjectsScoreDetailResDto = GeneralSubjectsScoreDetailResDto.builder()
                .score1_2(entranceTestFactorsDetail.getScore1_2())
                .score2_1(entranceTestFactorsDetail.getScore2_1())
                .score2_2(entranceTestFactorsDetail.getScore2_2())
                .score3_1(entranceTestFactorsDetail.getScore3_1())
                .score3_2(entranceTestFactorsDetail.getScore3_2())
                .build();

        return switch (graduationType) {
            case CANDIDATE -> {
                MiddleSchoolAchievement middleSchoolAchievement = oneseo.getMiddleSchoolAchievement();
                BigDecimal score_1 = calculateIndividualArtsPhysicalScore(middleSchoolAchievement.getArtsPhysicalAchievement(), 0, 3);
                BigDecimal score_2 = calculateIndividualArtsPhysicalScore(middleSchoolAchievement.getArtsPhysicalAchievement(), 3, 6);
                BigDecimal score_3 = calculateIndividualArtsPhysicalScore(middleSchoolAchievement.getArtsPhysicalAchievement(), 6, 9);

                String freeSemesterKey = getFreeSemesterKey(middleSchoolAchievement.getLiberalSystem(), middleSchoolAchievement.getFreeSemester());

                ArtsPhysicalSubjectsScoreDetailResDto artsPhysicalSubjectsScoreDetailResDto = ArtsPhysicalSubjectsScoreDetailResDto.builder()
                        .score1_2(assignIndividualArtsPhysicalScore(freeSemesterKey, "1-2", score_1, score_2, score_3))
                        .score2_1(assignIndividualArtsPhysicalScore(freeSemesterKey, "2-1", score_1, score_2, score_3))
                        .score2_2(assignIndividualArtsPhysicalScore(freeSemesterKey, "2-2", score_1, score_2, score_3))
                        .score3_1(assignIndividualArtsPhysicalScore(freeSemesterKey, "3-1", score_1, score_2, score_3))
                        .build();

                yield CalculatedScoreResDto.builder()
                        .generalSubjectsScore(entranceTestFactorsDetail.getGeneralSubjectsScore())
                        .artsPhysicalSubjectsScore(entranceTestFactorsDetail.getArtsPhysicalSubjectsScore())
                        .attendanceScore(entranceTestFactorsDetail.getAttendanceScore())
                        .volunteerScore(entranceTestFactorsDetail.getVolunteerScore())
                        .totalScore(entranceTestResult.getDocumentEvaluationScore())
                        .generalSubjectsScoreDetail(generalSubjectsScoreDetailResDto)
                        .artsPhysicalSubjectsScoreDetail(artsPhysicalSubjectsScoreDetailResDto)
                        .build();
            }
            case GRADUATE ->
                CalculatedScoreResDto.builder()
                        .generalSubjectsScore(entranceTestFactorsDetail.getGeneralSubjectsScore())
                        .artsPhysicalSubjectsScore(entranceTestFactorsDetail.getArtsPhysicalSubjectsScore())
                        .attendanceScore(entranceTestFactorsDetail.getAttendanceScore())
                        .volunteerScore(entranceTestFactorsDetail.getVolunteerScore())
                        .totalScore(entranceTestResult.getDocumentEvaluationScore())
                        .generalSubjectsScoreDetail(generalSubjectsScoreDetailResDto)
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

    private String getFreeSemesterKey(String liberalSystem, String freeSemester) {
        if (liberalSystem.equals("자유학년제") || freeSemester.equals("1-1") || freeSemester.equals("1-2")) {
            return "free";
        }
        return freeSemester;
    }

    private BigDecimal assignIndividualArtsPhysicalScore(
            String freeSemesterKey, String currentSemester,
            BigDecimal score_1, BigDecimal score_2, BigDecimal score_3
    ) {
        switch (freeSemesterKey) {
            case "free", "1-1", "1-2" -> {
                if (currentSemester.equals("2-1")) return score_1;
                if (currentSemester.equals("2-2")) return score_2;
                if (currentSemester.equals("3-1")) return score_3;
            }
            case "2-1" -> {
                if (currentSemester.equals("1-2")) return score_1;
                if (currentSemester.equals("2-2")) return score_2;
                if (currentSemester.equals("3-1")) return score_3;
            }
            case "2-2" -> {
                if (currentSemester.equals("1-2")) return score_1;
                if (currentSemester.equals("2-1")) return score_2;
                if (currentSemester.equals("3-1")) return score_3;
            }
            case "3-1" -> {
                if (currentSemester.equals("1-2")) return score_1;
                if (currentSemester.equals("2-1")) return score_2;
                if (currentSemester.equals("2-2")) return score_3;
            }
        }
        return null;
    }

    private BigDecimal calculateIndividualArtsPhysicalScore(List<Integer> achievementList, int start, int end) {
        List<Integer> subList = achievementList.subList(start, end);

        int sum = subList.stream()
                .reduce(0, Integer::sum);

        int achievementCount = (int) subList.stream()
                .filter(achievement -> achievement != 0)
                .count();

        int allAchievementCount = (int) achievementList.stream()
                .filter(achievement -> achievement != 0)
                .count();

        // 개별 학기별 예체능 점수 배점은 60 * (해당 학기의 성적이 있는 예체능 교과 수 / 성적이 있는 총 예체능 교과 수)으로 계산
        BigDecimal allocation = BigDecimal.valueOf(60)
                .multiply(BigDecimal.valueOf(achievementCount)
                        .divide(BigDecimal.valueOf(allAchievementCount), 4, RoundingMode.HALF_UP));

        if (achievementCount == 0) {
            return BigDecimal.ZERO;
        }

        return BigDecimal.valueOf(sum)
                .divide(BigDecimal.valueOf(achievementCount).multiply(BigDecimal.valueOf(5)), 4, RoundingMode.HALF_UP)
                .multiply(allocation)
                .setScale(4, RoundingMode.HALF_UP);
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
                .calculatedScore(calculatedScoreResDto)
                .build();
    }
}
