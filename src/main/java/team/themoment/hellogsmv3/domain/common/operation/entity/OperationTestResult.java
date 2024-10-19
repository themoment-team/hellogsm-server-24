package team.themoment.hellogsmv3.domain.common.operation.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo;

@Getter
@Builder
@Table(name = "tb_operation_test_result")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class OperationTestResult {
    @Id @Column(name = "operarion_test_result_id")
    private Long id;

    @Column(name = "first_test_result_announcement_yn")
    @Enumerated(EnumType.STRING)
    private YesNo firstTestResultAnnouncementYn;

    @Column(name = "second_test_result_announcement_yn")
    @Enumerated(EnumType.STRING)
    private YesNo secondTestResultAnnouncementYn;
}
