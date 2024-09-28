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
@Table(name = "tb_entrance_test_result", indexes = {
        @Index(name = "idx_first_test_pass_yn", columnList = "first_test_pass_yn"),
        @Index(name = "idx_second_test_pass_yn", columnList = "second_test_pass_yn")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EntranceTestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entrance_test_result_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "oneseo_id")
    private Oneseo oneseo;

    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL, orphanRemoval = true)
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

    public void modifyAptitudeEvaluationScore(BigDecimal aptitudeEvaluationScore) {
        this.aptitudeEvaluationScore = aptitudeEvaluationScore;
    }

    public void modifyInterviewScore(BigDecimal interviewScore) {
        this.interviewScore = interviewScore;
    }

    public void modifyDocumentEvaluationScore(BigDecimal documentEvaluationScore) {
        this.documentEvaluationScore = documentEvaluationScore;
    }

    public EntranceTestResult(Oneseo oneseo, EntranceTestFactorsDetail entranceTestFactorsDetail, BigDecimal documentEvaluationScore) {
        this.oneseo = oneseo;
        this.entranceTestFactorsDetail = entranceTestFactorsDetail;
        this.documentEvaluationScore = documentEvaluationScore;
    }
}
