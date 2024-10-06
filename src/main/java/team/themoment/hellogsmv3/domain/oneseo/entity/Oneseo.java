package team.themoment.hellogsmv3.domain.oneseo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.DesiredMajors;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.Major;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo;

import java.util.ArrayList;
import java.util.List;

import static team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo.*;

@Getter
@Entity
@Table(name = "tb_oneseo", indexes = {
        @Index(name = "idx_applied_screening_and_real_oneseo_arrived_yn", columnList = "applied_screening, real_oneseo_arrived_yn"),
        @Index(name = "idx_real_oneseo_arrived_yn", columnList = "real_oneseo_arrived_yn")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@DynamicUpdate
public class Oneseo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oneseo_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(mappedBy = "oneseo", cascade = CascadeType.ALL, orphanRemoval = true)
    private EntranceTestResult entranceTestResult;

    @OneToOne(mappedBy = "oneseo", cascade = CascadeType.ALL, orphanRemoval = true)
    private OneseoPrivacyDetail oneseoPrivacyDetail;

    @OneToOne(mappedBy = "oneseo", cascade = CascadeType.ALL, orphanRemoval = true)
    private MiddleSchoolAchievement middleSchoolAchievement;

    @OneToMany(mappedBy = "oneseo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WantedScreeningChangeHistory> wantedScreeningChangeHistory = new ArrayList<>();

    @Column(name = "oneseo_submit_code")
    private String oneseoSubmitCode;

    @Column(name = "pass_yn")
    @Enumerated(EnumType.STRING)
    private YesNo passYn;

    @NotNull
    @Embedded
    protected DesiredMajors desiredMajors;

    @Enumerated(EnumType.STRING)
    @Column(name = "real_oneseo_arrived_yn", nullable = false)
    private YesNo realOneseoArrivedYn;

    @Enumerated(EnumType.STRING)
    @Column(name = "wanted_screening", nullable = false)
    private Screening wantedScreening;

    @Enumerated(EnumType.STRING)
    @Column(name = "applied_screening")
    private Screening appliedScreening;

    @Enumerated(EnumType.STRING)
    @Column(name = "entrance_intention_yn")
    private YesNo entranceIntentionYn;

    @Enumerated(EnumType.STRING)
    @Column(name = "decided_major")
    private Major decidedMajor;

    public Oneseo setOneseoSubmitCode(String submitCode) {
        this.oneseoSubmitCode = submitCode;

        return this;
    }

    public void modifyEntranceIntentionYn(YesNo yn) {
        this.entranceIntentionYn = yn;
    }

    public void switchRealOneseoArrivedYn() {
        this.realOneseoArrivedYn = this.realOneseoArrivedYn == YES ? NO : YES;
    }
}
