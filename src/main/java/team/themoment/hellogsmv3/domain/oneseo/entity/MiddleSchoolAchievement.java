package team.themoment.hellogsmv3.domain.oneseo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import team.themoment.hellogsmv3.global.common.converter.StringListConverter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Entity
@Table(name = "tb_middle_school_achievement")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@DynamicUpdate
public class MiddleSchoolAchievement {  // TODO converter 구현

    @Id
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oneseo_id")
    private Oneseo oneseo;

    @Convert
    @Column(name = "achievement_1_1")
    private List<Integer> achievement1_1;

    @Convert
    @Column(name = "achievement_1_2")
    private List<Integer> achievement1_2;

    @Convert
    @Column(name = "achievement_2_1")
    private List<Integer> achievement2_1;

    @Convert
    @Column(name = "achievement_2_2")
    private List<Integer> achievement2_2;

    @Convert
    @Column(name = "achievement_3_1")
    private List<Integer> achievement3_1;

    @Convert(converter = StringListConverter.class)
    @Column(name = "general_subjects")
    private List<String> generalSubjects;

    @Convert(converter = StringListConverter.class)
    @Column(name = "new_subjects")
    private List<String> newSubjects;

    @Convert
    @Column(name = "arts_physical_achievement")
    private List<Integer> artsPhysicalAchievement;

    @Convert(converter = StringListConverter.class)
    @Column(name = "arts_physical_subjects")
    private List<String> artsPhysicalSubjects;

    @Convert
    @Column(name = "absent_days")
    private List<Integer> absentDays;

    @Convert
    @Column(name = "attendance_days")
    private List<Integer> attendanceDays;

    @Convert
    @Column(name = "volunteer_time")
    private List<Integer> volunteerTime;

    @Column(name = "liberal_system")
    private String liberalSystem;

    @Column(name = "free_semester")
    private String freeSemester;

    @Column(name = "ged_total_score")
    private BigDecimal gedTotalScore;

    @Column(name = "ged_max_score")
    private BigDecimal gedMaxScore;
}
