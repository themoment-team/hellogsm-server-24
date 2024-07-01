package team.themoment.hellogsmv3.domain.oneseo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "tb_middle_school_achievement")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MiddleSchoolAchievement {

    @Id
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oneseo_id")
    private Oneseo oneseo;

    @Column(name = "transcript", columnDefinition = "TEXT", nullable = false)
    private String transcript;

    @Column(name = "percentile_rank", nullable = false)
    private BigDecimal percentileRank;

    @Column(name = "total_score", nullable = false)
    private BigDecimal totalScore;

    @Column(name = "artistic_score")
    private BigDecimal artisticScore;

    @Column(name = "attendance_score")
    private BigDecimal attendanceScore;

    @Column(name = "volunteer_score")
    private BigDecimal volunteerScore;

    @Column(name = "curricular_subtotal_score")
    private BigDecimal curricularSubtotalScore;

    @Column(name = "extra_curricular_subtotal_score")
    private BigDecimal extraCurricularSubtotalScore;

    @Column(name = "ged_max_score")
    private BigDecimal gedMaxScore;

    @Column(name = "ged_total_score")
    private BigDecimal gedTotalScore;

    @Column(name = "grade1_semester1_score")
    private BigDecimal grade1Semester1Score;

    @Column(name = "grade1_semester2_score")
    private BigDecimal grade1Semester2Score;

    @Column(name = "grade2_semester1_score")
    private BigDecimal grade2Semester1Score;

    @Column(name = "grade2_semester2_score")
    private BigDecimal grade2Semester2Score;

    @Column(name = "grade3_semester1_score")
    private BigDecimal grade3Semester1Score;
}
