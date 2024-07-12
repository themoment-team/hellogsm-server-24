package team.themoment.hellogsmv3.domain.oneseo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.oneseo.entity.EntranceTestResult;
import team.themoment.hellogsmv3.domain.oneseo.entity.MiddleSchoolAchievement;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;
import team.themoment.hellogsmv3.domain.oneseo.repository.EntranceTestFactorsDetailRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.EntranceTestResultRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.MiddleSchoolAchievementRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CalculateGradeService {

    private final EntranceTestResultRepository entranceTestResultRepository;
    private final EntranceTestFactorsDetailRepository entranceTestFactorsDetailRepository;
    private final MiddleSchoolAchievementRepository middleSchoolAchievementRepository;

    public void execute(Oneseo oneseo) {

        MiddleSchoolAchievement middleSchoolAchievement = middleSchoolAchievementRepository.findByOneseo(oneseo);

        String liberalSystem = middleSchoolAchievement.getLiberalSystem();
        String freeSemester = middleSchoolAchievement.getFreeSemester();

        /**
         * 자유학년제, 학기제에 따른 성적계산에 사용되는 학기 성적
         *
         * 자유학년제 + 자유학기제 (1-1, 1-2)
         * - 2-1, 2-2, 3-1, = 54, 54, 72
         *
         * 자유학기제 (2-1)
         * - 1-2, 2-2, 3-1 = 54, 54, 72
         *
         * 자유학기제 (2-2)
         * - 1-2, 2-1, 3-1 = 54, 54, 72
         *
         * 자유학기제 (3-1)
         * - 1-2, 2-1, 2-2 = 54, 54, 72
         *
         */

        BigDecimal achievement1_2 = calcGeneralScore(
                middleSchoolAchievement.getAchievement1_2(), BigDecimal.valueOf(
                liberalSystem.equals("자유학년제") || freeSemester.equals("1-1") || liberalSystem.equals("1-2") ? 0 : 54)
        );
        BigDecimal achievement2_1 = calcGeneralScore(
                middleSchoolAchievement.getAchievement2_1(), BigDecimal.valueOf(
                        liberalSystem.equals("2-1") ? 0 : 54)
        );
        BigDecimal achievement2_2 = calcGeneralScore(
                middleSchoolAchievement.getAchievement2_2(), BigDecimal.valueOf(
                        liberalSystem.equals("2-2") ? 0 : (liberalSystem.equals("3-1") ? 72 : 54))
        );
        BigDecimal achievement3_1 = calcGeneralScore(
                middleSchoolAchievement.getAchievement3_1(), BigDecimal.valueOf(
                        liberalSystem.equals("3-1") ? 0 : 72)
        );

        // 일반 교과 성적 환산값 (총점: 180점)
        BigDecimal generalSubjectsScore = Stream.of(
                        achievement1_2,
                        achievement2_1,
                        achievement2_2,
                        achievement3_1).reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(3, RoundingMode.HALF_UP);

        // 예체능 성적 환산값 (총점: 60점)
        BigDecimal artsPhysicalSubjectsScore = calcArtSportsScore(middleSchoolAchievement.getArtsPhysicalAchievement());


        // 교과 성적 환산값 (예체능 성적 + 일반 교과 성적, 총점: 240점)
        BigDecimal totalSubjectsScore = artsPhysicalSubjectsScore
                .add(generalSubjectsScore)
                .setScale(3, RoundingMode.HALF_UP);

        // 출결 성적 (총점: 30점)
        BigDecimal attendanceScore = calcAttendanceScore(
                middleSchoolAchievement.getAbsentDays(), middleSchoolAchievement.getAttendanceDays());

    }

    private BigDecimal calcGeneralScore(List<BigDecimal> scoreArray, BigDecimal maxPoint) {
        // 해당 학기의 등급별 점수 배열이 비어있거나 해당 학기의 배점이 없다면 0.000을 반환
        if (scoreArray == null || scoreArray.isEmpty() || maxPoint.equals(BigDecimal.ZERO)) return BigDecimal.valueOf(0).setScale(3, RoundingMode.HALF_UP);
        // 해당 학기에 수강하지 않은 과목이 있다면 제거한 리스트를 반환 (점수가 0인 원소 제거)
        List<BigDecimal> noZeroScoreArray = scoreArray.stream().filter((score) -> score.compareTo(BigDecimal.ZERO) != 0).toList();
        // 위에서 구한 리스트가 비어있다면 0.000을 반환
        if (noZeroScoreArray.isEmpty()) return BigDecimal.valueOf(0).setScale(3, RoundingMode.HALF_UP);

        // 1. 점수로 환산된 등급을 모두 더한다.
        BigDecimal reduceResult = scoreArray.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        // 2. 더한값 / (과목 수 * 5) (소수점 6째자리에서 반올림)
        BigDecimal divideResult = reduceResult.divide(BigDecimal.valueOf(noZeroScoreArray.size() * 5L), 5, RoundingMode.HALF_UP);
        // 3. 각 학기당 배점 * 나눈값 (소수점 4째자리에서 반올림)
        return divideResult.multiply(maxPoint).setScale(3, RoundingMode.HALF_UP);
    }

    private BigDecimal calcArtSportsScore(List<BigDecimal> scoreArray) {
        int aCount = 0;
        int bCount = 0;
        int cCount = 0;

        BigDecimal A = BigDecimal.valueOf(5);
        BigDecimal B = BigDecimal.valueOf(4);
        BigDecimal C = BigDecimal.valueOf(3);

        for (BigDecimal score : scoreArray) {
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

        // TODO 작년 구현에서 과목수가 0일시 36.00점을 반환하는 로직의 존재 이유를 모르겠어서 주석처리
        /*
            if (totalSubjects == 0) {
                return BigDecimal.valueOf(36).setScale(3, RoundingMode.HALF_UP);
            }
        */

        BigDecimal averageOfAchievementScale  = BigDecimal.valueOf(totalScores).divide(BigDecimal.valueOf(maxScore), 3, RoundingMode.HALF_UP);
        return BigDecimal.valueOf(60).multiply(averageOfAchievementScale).setScale(3, RoundingMode.HALF_UP);
    }

    private BigDecimal calcAttendanceScore(List<BigDecimal> absentScore, List<BigDecimal> attendanceScore) {
        // 결석 횟수를 더함
        BigDecimal absent = absentScore.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        // 결석 횟수가 10회 이상 0점을 반환
        if (absent.compareTo(BigDecimal.TEN) >= 0) return BigDecimal.valueOf(0);

        // 1. 모든 지각, 조퇴, 결과 횟수를 더함
        BigDecimal attendance = attendanceScore.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        // 2. 지각, 조퇴, 결과 횟수는 3개당 결석 1회
        BigDecimal absentResult = attendance.divide(BigDecimal.valueOf(3), 0, RoundingMode.DOWN);
        // 3. 총점(30점) - (3 * 총 결석 횟수)
        BigDecimal result = BigDecimal.valueOf(30).subtract(absent.add(absentResult).multiply(BigDecimal.valueOf(3)));

        // 총 점수가 0점 이하라면 0점을 반환
        if (result.compareTo(BigDecimal.ZERO) <= 0) return BigDecimal.ZERO;

        return result;
    }

}
