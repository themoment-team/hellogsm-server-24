package team.themoment.hellogsmv3.domain.oneseo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "tb_entrance_test_factors_detail")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EntranceTestFactorsDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entrance_test_factors_detail_id")
    private Long id;

    @Column(name = "general_subjects_score")
    private BigDecimal generalSubjectsScore;

    @Column(name = "arts_physical_subjects_score")
    private BigDecimal artsPhysicalSubjectsScore;

    @Column(name = "total_subjects_score")
    private BigDecimal totalSubjectsScore;

    @Column(name = "attendance_score")
    private BigDecimal attendanceScore;

    @Column(name = "volunteer_score")
    private BigDecimal volunteerScore;

    @Column(name = "total_non_subjects_score")
    private BigDecimal totalNonSubjectsScore;

    @Column(name = "score_1_2")
    private BigDecimal score1_2;

    @Column(name = "score_2_1")
    private BigDecimal score2_1;

    @Column(name = "score_2_2")
    private BigDecimal score2_2;

    @Column(name = "score_3_1")
    private BigDecimal score3_1;

    @Column(name = "score_3_2")
    private BigDecimal score3_2;

}
