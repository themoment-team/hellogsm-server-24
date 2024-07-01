package team.themoment.hellogsmv3.domain.oneseo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.DesiredMajors;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening;

@Getter
@Entity
@Table(name = "tb_oneseo")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Oneseo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oneseo_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "oneseo_privacy_detail_id")
    private OneseoPrivacyDetail oneseoPrivacyDetail;

    @Column(name = "oneseo_submit_code")
    private Long oneseoSubmitCode;

    @NotNull
    @Embedded
    protected DesiredMajors desiredMajors;

    @Column(name = "is_real_oneseo_arrived", nullable = false)
    private Boolean isRealOneseoArrived;

    @Column(name = "is_final_submitted", nullable = false)
    private Boolean isFinalSubmitted;

    @Enumerated(EnumType.STRING)
    @Column(name = "wanted_screening", nullable = false)
    private Screening wantedScreening;

    @Enumerated(EnumType.STRING)
    @Column(name = "applied_screening", nullable = false)
    private Screening appliedScreening;
}
