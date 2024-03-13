package team.themoment.hellogsmv3.domain.application.type;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Embeddable
@Getter
@NoArgsConstructor
public class DesiredMajors {

    private Major firstDesiredMajor;

    private Major secondDesiredMajor;

    private Major thirdDesiredMajor;

    public DesiredMajors(List<Major> desiredMajors) {
        assert desiredMajors.size() == 3;
        this.firstDesiredMajor = desiredMajors.get(0);
        this.secondDesiredMajor = desiredMajors.get(1);
        this.thirdDesiredMajor = desiredMajors.get(2);
    }
}
