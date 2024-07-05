package team.themoment.hellogsmv3.domain.oneseo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.DesiredMajors;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo;

@Getter
@Entity
@Table(name = "tb_oneseo")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Oneseo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oneseo_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "oneseo_submit_code")
    private Long oneseoSubmitCode;

    @NotNull
    @Embedded
    protected DesiredMajors desiredMajors;

    @Enumerated(EnumType.STRING)
    @Column(name = "real_oneseo_arrived_yn", nullable = false)
    private YesNo realOneseoArrivedYn;

    @Enumerated(EnumType.STRING)
    @Column(name = "final_submitted_yn", nullable = false)
    private YesNo finalSubmittedYn;

    @Enumerated(EnumType.STRING)
    @Column(name = "applied_screening", nullable = false)
    private Screening appliedScreening;
}
