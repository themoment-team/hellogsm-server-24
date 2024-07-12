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

        // 교과 성적 환산값 (총점: 180점)
        BigDecimal generalSubjectsScore = Stream.of(
                        achievement1_2,
                        achievement2_1,
                        achievement2_2,
                        achievement3_1).reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(3, RoundingMode.HALF_UP);

    }

    private BigDecimal calcGeneralScore(List<BigDecimal> scoreArray, BigDecimal maxPoint) {
        // 해당 학기의 등급별 점수 배열이 비어있거나 해당 학기의 배점이 없다면 0.000을 반환
        if (scoreArray == null || scoreArray.isEmpty() || maxPoint.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.valueOf(0).setScale(3, RoundingMode.HALF_UP);
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

}
