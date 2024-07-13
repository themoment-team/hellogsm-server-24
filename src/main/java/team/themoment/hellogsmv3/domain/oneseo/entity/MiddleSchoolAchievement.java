package team.themoment.hellogsmv3.domain.oneseo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import team.themoment.hellogsmv3.global.common.converter.IntegerListConverter;
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
public class MiddleSchoolAchievement {

    @Id
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oneseo_id")
    private Oneseo oneseo;

    @Convert(converter = IntegerListConverter.class)
    @Column(name = "achievement_1_2")
    private List<BigDecimal> achievement1_2;

    @Convert(converter = IntegerListConverter.class)
    @Column(name = "achievement_2_1")
    private List<BigDecimal> achievement2_1;

    @Convert(converter = IntegerListConverter.class)
    @Column(name = "achievement_2_2")
    private List<BigDecimal> achievement2_2;

    @Convert(converter = IntegerListConverter.class)
    @Column(name = "achievement_3_1")
    private List<BigDecimal> achievement3_1;

    @Convert(converter = IntegerListConverter.class)
    @Column(name = "achievement_3_2")
    private List<BigDecimal> achievement3_2;

    @Convert(converter = StringListConverter.class)
    @Column(name = "general_subjects")
    private List<String> generalSubjects;

    @Convert(converter = StringListConverter.class)
    @Column(name = "new_subjects")
    private List<String> newSubjects;

    @Convert(converter = IntegerListConverter.class)
    @Column(name = "arts_physical_achievement")
    private List<BigDecimal> artsPhysicalAchievement;

    @Convert(converter = StringListConverter.class)
    @Column(name = "arts_physical_subjects")
    private List<String> artsPhysicalSubjects;

    @Convert(converter = IntegerListConverter.class)
    @Column(name = "absent_days")
    private List<BigDecimal> absentDays;

    @Convert(converter = IntegerListConverter.class)
    @Column(name = "attendance_days")
    private List<BigDecimal> attendanceDays;

    @Convert(converter = IntegerListConverter.class)
    @Column(name = "volunteer_time")
    private List<BigDecimal> volunteerTime;

    @Column(name = "liberal_system")
    private String liberalSystem;

    @Column(name = "free_semester")
    private String freeSemester;

    @Column(name = "ged_total_score")
    private BigDecimal gedTotalScore;

    @Column(name = "ged_max_score")
    private BigDecimal gedMaxScore;
}
