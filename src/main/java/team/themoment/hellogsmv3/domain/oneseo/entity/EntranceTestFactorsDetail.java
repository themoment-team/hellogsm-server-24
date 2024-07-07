package team.themoment.hellogsmv3.domain.oneseo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "tb_entrance_test_factors_detail")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EntranceTestFactorsDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entrance_test_factors_detail_id")
    private Long id;

    @Column(name = "curricular_subtotal_score")
    private BigDecimal curricularSubtotalScore;

    @Column(name = "extra_curricular_subtotal_score")
    private BigDecimal extraCurricularSubtotalScore;

    @Column(name = "artistic_score")
    private BigDecimal artisticScore;

    @Column(name = "attendance_score")
    private BigDecimal attendanceScore;

    @Column(name = "volunteer_score")
    private BigDecimal volunteerScore;

    @Column(name = "document_evaluation_score")
    private BigDecimal documentEvaluationScore;

    @Column(name = "aptitude_evaluation_score")
    private BigDecimal aptitudeEvaluationScore;

    @Column(name = "interview_score")
    private BigDecimal interviewScore;
}
