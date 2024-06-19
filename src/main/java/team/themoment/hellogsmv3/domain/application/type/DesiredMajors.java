package team.themoment.hellogsmv3.domain.application.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.themoment.hellogsmv3.domain.application.annotation.ValidDesiredMajors;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Embeddable
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ValidDesiredMajors
public class DesiredMajors {

    @Enumerated(EnumType.STRING)
    @Column(name = "first_desired_major", nullable = false)
    @NotNull
    private Major firstDesiredMajor;

    @Enumerated(EnumType.STRING)
    @Column(name = "second_desired_major", nullable = false)
    @NotNull
    private Major secondDesiredMajor;

    @Enumerated(EnumType.STRING)
    @Column(name = "third_desired_major", nullable = false)
    @NotNull
    private Major thirdDesiredMajor;
}
