package team.themoment.hellogsmv3.domain.oneseo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.Major;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo;

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

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entrance_test_factors_detail_id")
    private EntranceTestFactorsDetail entranceTestFactorsDetail;

    @Enumerated(EnumType.STRING)
    @Column(name = "first_test_pass_yn")
    private YesNo firstTestPassYn;

    @Enumerated(EnumType.STRING)
    @Column(name = "second_test_pass_yn")
    private YesNo secondTestPassYn;

    @Enumerated(EnumType.STRING)
    @Column(name = "decided_major")
    private Major decidedMajor;
}
