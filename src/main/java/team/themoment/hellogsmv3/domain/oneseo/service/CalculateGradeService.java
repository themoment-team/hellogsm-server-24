package team.themoment.hellogsmv3.domain.oneseo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.oneseo.dto.request.MiddleSchoolAchievementReqDto;
import team.themoment.hellogsmv3.domain.oneseo.entity.*;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.GraduationType;
import team.themoment.hellogsmv3.domain.oneseo.repository.EntranceTestFactorsDetailRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.EntranceTestResultRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Stream;

import static team.themoment.hellogsmv3.domain.oneseo.entity.type.GraduationType.*;

@Service
@RequiredArgsConstructor
public class CalculateGradeService {

    private final EntranceTestResultRepository entranceTestResultRepository;
    private final EntranceTestFactorsDetailRepository entranceTestFactorsDetailRepository;

    public void execute(MiddleSchoolAchievementReqDto dto, Oneseo oneseo, GraduationType graduationType) {

        if (!graduationType.equals(CANDIDATE) && !graduationType.equals(GRADUATE))
            throw new IllegalArgumentException("올바르지 않은 graduationType입니다.");

        String liberalSystem = dto.liberalSystem();
        String freeSemester = dto.freeSemester() != null ? dto.freeSemester() : "";

        BigDecimal achievement1_2 = BigDecimal.ZERO;
        BigDecimal achievement2_1 = BigDecimal.ZERO;
        BigDecimal achievement2_2 = BigDecimal.ZERO;
        BigDecimal achievement3_1 = BigDecimal.ZERO;
        BigDecimal achievement3_2 = BigDecimal.ZERO;

        switch (graduationType) {
            case CANDIDATE -> {
                achievement1_2 = calcGeneralScore(
                        dto.achievement1_2(), BigDecimal.valueOf(
                                liberalSystem.equals("자유학년제") || freeSemester.equals("1-2") ? 0 : 54)
                );
                achievement2_1 = calcGeneralScore(
                        dto.achievement2_1(), BigDecimal.valueOf(
                                freeSemester.equals("2-1") ? 0 : 54)
                );
                achievement2_2 = calcGeneralScore(
                        dto.achievement2_2(), BigDecimal.valueOf(
                                freeSemester.equals("2-2") ? 0 : (freeSemester.equals("3-1") ? 72 : 54))
                );
                achievement3_1 = calcGeneralScore(
                        dto.achievement3_1(), BigDecimal.valueOf(
                                freeSemester.equals("3-1") ? 0 : 72)
                );
            }
            case GRADUATE -> {
                achievement1_2 = calcGeneralScore(
                        dto.achievement1_2(), BigDecimal.valueOf(
                                liberalSystem.equals("자유학년제") || freeSemester.equals("1-2") ? 0 : 36)
                );
                achievement2_1 = calcGeneralScore(
                        dto.achievement2_1(), BigDecimal.valueOf(
                                freeSemester.equals("2-1") ? 0 : 36)
                );
                achievement2_2 = calcGeneralScore(
                        dto.achievement2_2(), BigDecimal.valueOf(
                                freeSemester.equals("2-2") ? 0 :
                                        (freeSemester.equals("3-1") || freeSemester.equals("3-2")) ? 54 : 36)
                );
                achievement3_1 = calcGeneralScore(
                        dto.achievement3_1(), BigDecimal.valueOf(
                                freeSemester.equals("3-1") ? 0 : 54)
                );
                achievement3_2 = calcGeneralScore(
                        dto.achievement3_2(), BigDecimal.valueOf(
                                freeSemester.equals("3-2") ? 0 : 54)
                );
            }
        }

        // 일반 교과 성적 환산값 (총점: 180점)
        BigDecimal generalSubjectsScore = Stream.of(
                        achievement1_2,
                        achievement2_1,
                        achievement2_2,
                        achievement3_1,
                        achievement3_2).reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(3, RoundingMode.HALF_UP);

        // 예체능 성적 환산값 (총점: 60점)
        BigDecimal artsPhysicalSubjectsScore = calcArtSportsScore(dto.artsPhysicalAchievement());


        // 교과 성적 환산값 (예체능 성적 + 일반 교과 성적, 총점: 240점)
        BigDecimal totalSubjectsScore = artsPhysicalSubjectsScore
                .add(generalSubjectsScore)
                .setScale(3, RoundingMode.HALF_UP);

        // 출결 성적 (총점: 30점)
        BigDecimal attendanceScore = calcAttendanceScore(
                dto.absentDays(), dto.attendanceDays()
        ).setScale(3, RoundingMode.HALF_UP);

        // 봉사 성적 (총점: 30점)
        BigDecimal volunteerScore = calcVolunteerScore(dto.volunteerTime())
                .setScale(3, RoundingMode.HALF_UP);

        // 비 교과 성적 환산값 (총점: 60점)
        BigDecimal totalNonSubjectsScore = attendanceScore.add(volunteerScore)
                .setScale(3, RoundingMode.HALF_UP);

        // 내신 성적 총 점수 (총점: 300점)
        BigDecimal totalScore = totalSubjectsScore.add(totalNonSubjectsScore)
                .setScale(3, RoundingMode.HALF_UP);

        EntranceTestFactorsDetail entranceTestFactorsDetail = EntranceTestFactorsDetail.builder()
                .generalSubjectsScore(generalSubjectsScore)
                .artsPhysicalSubjectsScore(artsPhysicalSubjectsScore)
                .attendanceScore(attendanceScore)
                .totalSubjectsScore(totalSubjectsScore)
                .attendanceScore(attendanceScore)
                .volunteerScore(volunteerScore)
                .totalNonSubjectsScore(totalNonSubjectsScore)
                .score1_2(achievement1_2)
                .score2_1(achievement2_1)
                .score2_2(achievement2_2)
                .score3_1(achievement3_1)
                .score3_2(achievement3_2)
                .build();

        EntranceTestResult entranceTestResult = new EntranceTestResult(oneseo, entranceTestFactorsDetail, totalScore);

        entranceTestFactorsDetailRepository.save(entranceTestFactorsDetail);
        entranceTestResultRepository.save(entranceTestResult);
    }

    private BigDecimal calcGeneralScore(List<BigDecimal> achievements, BigDecimal maxPoint) {
        // 해당 학기의 등급별 점수 배열이 비어있거나 해당 학기의 배점이 없다면 0.000을 반환
        if (achievements == null || achievements.isEmpty() || maxPoint.equals(BigDecimal.ZERO)) return BigDecimal.valueOf(0).setScale(3, RoundingMode.HALF_UP);
        // 해당 학기에 수강하지 않은 과목이 있다면 제거한 리스트를 반환 (점수가 0인 원소 제거)
        List<BigDecimal> noZeroAchievements = achievements.stream().filter((score) -> score.compareTo(BigDecimal.ZERO) != 0).toList();
        // 위에서 구한 리스트가 비어있다면 0.000을 반환
        if (noZeroAchievements.isEmpty()) return BigDecimal.valueOf(0).setScale(3, RoundingMode.HALF_UP);

        // 1. 점수로 환산된 등급을 모두 더한다.
        BigDecimal reduceResult = achievements.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        // 2. 더한값 / (과목 수 * 5) (소수점 6째자리에서 반올림)
        BigDecimal divideResult = reduceResult.divide(BigDecimal.valueOf(noZeroAchievements.size() * 5L), 5, RoundingMode.HALF_UP);
        // 3. 각 학기당 배점 * 나눈값 (소수점 4째자리에서 반올림)
        return divideResult.multiply(maxPoint).setScale(3, RoundingMode.HALF_UP);
    }

    private BigDecimal calcArtSportsScore(List<BigDecimal> achievements) {
        int aCount = 0;
        int bCount = 0;
        int cCount = 0;

        BigDecimal A = BigDecimal.valueOf(5);
        BigDecimal B = BigDecimal.valueOf(4);
        BigDecimal C = BigDecimal.valueOf(3);

        for (BigDecimal score : achievements) {
            if (score.equals(A)) {
                aCount++;
            } else if (score.equals(B)) {
                bCount++;
            } else if (score.equals(C)) {
                cCount++;
            }
        }

        // 1. 각 등급별 갯수에 등급별 배점을 곱한 값을 더한다.
        int totalScores = (aCount * 5) + (bCount * 4) + (cCount * 3);
        // 2. 각 등급별 갯수를 모두 더해 과목 수를 구한다.
        int totalSubjects = aCount + bCount + cCount;
        // 3. 각 등급별 갯수를 더한 값에 5를 곰해 총점을 구한다.
        int maxScore = 5 * totalSubjects;

        // 과목 수가 0일시 0점 반환
        if (totalSubjects == 0) {
            return BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
        }

        BigDecimal averageOfAchievementScale = BigDecimal.valueOf(totalScores).divide(BigDecimal.valueOf(maxScore), 3, RoundingMode.HALF_UP);
        return BigDecimal.valueOf(60).multiply(averageOfAchievementScale).setScale(3, RoundingMode.HALF_UP);
    }

    private BigDecimal calcAttendanceScore(List<BigDecimal> absentDays, List<BigDecimal> attendanceDays) {
        // 결석 횟수를 더함
        BigDecimal absentDay = absentDays.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        // 결석 횟수가 10회 이상 0점을 반환
        if (absentDay.compareTo(BigDecimal.TEN) >= 0) return BigDecimal.valueOf(0);

        // 1. 모든 지각, 조퇴, 결과 횟수를 더함
        BigDecimal attendanceDay = attendanceDays.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        // 2. 지각, 조퇴, 결과 횟수는 3개당 결석 1회
        BigDecimal absentResult = attendanceDay.divide(BigDecimal.valueOf(3), 0, RoundingMode.DOWN);
        // 3. 총점(30점) - (3 * 총 결석 횟수)
        BigDecimal result = BigDecimal.valueOf(30).subtract(absentDay.add(absentResult).multiply(BigDecimal.valueOf(3)));

        // 총 점수가 0점 이하라면 0점을 반환
        if (result.compareTo(BigDecimal.ZERO) <= 0) return BigDecimal.ZERO;

        return result;
    }

    private BigDecimal calcVolunteerScore(List<BigDecimal> volunteerHour) {
        return volunteerHour.stream().reduce(BigDecimal.valueOf(0), (current, hour) -> {
            // 연간 7시간 이상
            if (hour.compareTo(BigDecimal.valueOf(7)) >= 0) {
                return current.add(BigDecimal.valueOf(10));
            }
            // 연간 6시간 이상
            else if (hour.compareTo(BigDecimal.valueOf(6)) >= 0) {
                return current.add(BigDecimal.valueOf(8));
            }
            // 연간 5시간 이상
            else if (hour.compareTo(BigDecimal.valueOf(5)) >= 0) {
                return current.add(BigDecimal.valueOf(6));
            }
            // 연간 4시간 이상
            else if (hour.compareTo(BigDecimal.valueOf(4)) >= 0) {
                return current.add(BigDecimal.valueOf(4));
            }
            // 연간 3시간 이하
            else {
                return current.add(BigDecimal.valueOf(2));
            }
        });
    }

}
