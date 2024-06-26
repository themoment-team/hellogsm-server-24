package team.themoment.hellogsmv3.domain.oneseo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.themoment.hellogsmv3.domain.application.type.EvaluationStatus;
import team.themoment.hellogsmv3.domain.application.type.Major;

import java.math.BigDecimal;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EntranceTestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entrance _test_result_id")
    private Long id;

    @Column(name = "first_test_result_score")
    private BigDecimal firstTestResultScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "first_test_pass_yn")
    private EvaluationStatus firstTestPassYn;

    @Column(name = "second_test_result_score")
    private BigDecimal secondTestResultScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "second_test_pass_yn")
    private EvaluationStatus secondTestPassYn;

    @Column(name = "interview_score")
    private BigDecimal interviewScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "decided_major")
    private Major decidedMajor;
}
