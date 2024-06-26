package team.themoment.hellogsmv3.domain.oneseo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import team.themoment.hellogsmv3.domain.application.type.Screening;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScreeningChangeHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entrance _test_result_id")
    private Long id;

    @Column(name = "before_screening", nullable = false)
    private Screening beforeScreening;

    @Column(name = "after_screening", nullable = false)
    private Screening afterScreening;

    @CreatedDate
    @Column(name = "created_time", updatable = false, nullable = false)
    private LocalDateTime createdTime;
}
