package team.themoment.hellogsmv3.domain.application.type;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DesiredMajors {

    private Major firstDesiredMajor;

    private Major secondDesiredMajor;

    private Major thirdDesiredMajor;
}
