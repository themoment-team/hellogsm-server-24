package team.themoment.hellogsmv3.domain.application.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.AssertFalse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Embeddable
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DesiredMajors {

    @Enumerated(EnumType.STRING)
    @Column(name = "first_desired_major", nullable = false)
    private Major firstDesiredMajor;

    @Enumerated(EnumType.STRING)
    @Column(name = "second_desired_major", nullable = false)
    private Major secondDesiredMajor;

    @Enumerated(EnumType.STRING)
    @Column(name = "third_desired_major", nullable = false)
    private Major thirdDesiredMajor;
}
