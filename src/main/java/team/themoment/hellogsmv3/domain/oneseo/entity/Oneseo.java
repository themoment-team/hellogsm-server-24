package team.themoment.hellogsmv3.domain.oneseo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.Major;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening;

@Getter
@Entity
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

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "middle_school_achievement_id")
    private MiddleSchoolAchievement middleSchoolAchievement;

    @Column(name = "oneseo_submit_code")
    private Long oneseoSubmitCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "first_desired_major", nullable = false)
    private Major firstDesiredMajor;

    @Enumerated(EnumType.STRING)
    @Column(name = "second_desired_major", nullable = false)
    private Major secondDesiredMajor;

    @Enumerated(EnumType.STRING)
    @Column(name = "third_desired_major", nullable = false)
    private Major thirdDesiredMajor;

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
