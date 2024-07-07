package team.themoment.hellogsmv3.domain.oneseo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "tb_screening_change_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@Builder
public class ScreeningChangeHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entrance _test_result_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "oneseo_id")
    private Oneseo oneseo;

    @Enumerated(EnumType.STRING)
    @Column(name = "before_screening", nullable = false)
    private Screening beforeScreening;

    @Enumerated(EnumType.STRING)
    @Column(name = "after_screening", nullable = false)
    private Screening afterScreening;

    @CreatedDate
    @Column(name = "created_time", updatable = false, nullable = false)
    private LocalDateTime createdTime;
}
