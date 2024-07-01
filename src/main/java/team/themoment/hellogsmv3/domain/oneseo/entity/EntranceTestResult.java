package team.themoment.hellogsmv3.domain.oneseo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.Major;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo;

import java.math.BigDecimal;

@Getter
@Entity
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

    @Column(name = "first_test_result_score")
    private BigDecimal firstTestResultScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "first_test_pass_yn")
    private YesNo firstTestPassYn;

    @Column(name = "second_test_result_score")
    private BigDecimal secondTestResultScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "second_test_pass_yn")
    private YesNo secondTestPassYn;

    @Column(name = "interview_score")
    private BigDecimal interviewScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "decided_major")
    private Major decidedMajor;
}
