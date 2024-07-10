package team.themoment.hellogsmv3.domain.oneseo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Entity
@Table(name = "tb_middle_school_achievement")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@DynamicUpdate
public class MiddleSchoolAchievement {

    @Id
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oneseo_id")
    private Oneseo oneseo;

    @Convert
    @Column(name = "achievement_1_1")
    private List<Integer> achievement_1_1;

    @Convert
    @Column(name = "achievement_1_2")
    private List<Integer> achievement_1_2;

    @Convert
    @Column(name = "achievement_2_1")
    private List<Integer> achievement_2_1;

    @Convert
    @Column(name = "achievement_2_2")
    private List<Integer> achievement_2_2;

    @Convert
    @Column(name = "achievement_3_1")
    private List<Integer> achievement_3_1;

    @Convert
    @Column(name = "general_subjects")
    private List<String> general_subjects;

    @Convert
    @Column(name = "new_subjects")
    private List<String> new_subjects;

    @Convert
    @Column(name = "arts_sports_achievement")
    private List<Integer> arts_sports_achievement;

    @Convert
    @Column(name = "arts_sports_subjects")
    private List<String> arts_sports_subjects;

    @Convert
    @Column(name = "absent_days")
    private List<Integer> absent_days;

    @Convert
    @Column(name = "attendance_days")
    private List<Integer> attendance_days;

    @Convert
    @Column(name = "volunteer_time")
    private List<Integer> volunteer_time;

    @Column(name = "liberal_system")
    private String liberal_system;

    @Column(name = "free_semester")
    private String free_semester;

    @Column(name = "ged_total_score")
    private BigDecimal ged_total_score;

    @Column(name = "ged_max_score")
    private BigDecimal ged_max_score;
}
