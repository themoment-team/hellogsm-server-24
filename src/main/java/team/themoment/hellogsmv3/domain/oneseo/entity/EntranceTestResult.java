package team.themoment.hellogsmv3.domain.oneseo.entity;

import jakarta.persistence.*;
import lombok.*;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.Major;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo;

import java.math.BigDecimal;

@Getter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "tb_entrance_test_result")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EntranceTestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entrance _test_result_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "oneseo_id")
    private Oneseo oneseo;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entrance_test_factors_detail_id")
    private EntranceTestFactorsDetail entranceTestFactorsDetail;

    @Column(name = "document_evaluation_score")
    private BigDecimal documentEvaluationScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "first_test_pass_yn")
    private YesNo firstTestPassYn;

    @Column(name = "aptitude_evaluation_score")
    private BigDecimal aptitudeEvaluationScore;

    @Column(name = "interview_score")
    private BigDecimal interviewScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "second_test_pass_yn")
    private YesNo secondTestPassYn;

    @Enumerated(EnumType.STRING)
    @Column(name = "decided_major")
    private Major decidedMajor;

    public void modifyAptitudeEvaluationScore(BigDecimal aptitudeEvaluationScore) {
        this.aptitudeEvaluationScore = aptitudeEvaluationScore;
    }

    public EntranceTestResult(Oneseo oneseo, EntranceTestFactorsDetail entranceTestFactorsDetail, BigDecimal documentEvaluationScore) {
        this.oneseo = oneseo;
        this.entranceTestFactorsDetail = entranceTestFactorsDetail;
        this.documentEvaluationScore = documentEvaluationScore;
    }
}
