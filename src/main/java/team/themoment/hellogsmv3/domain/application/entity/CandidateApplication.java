package team.themoment.hellogsmv3.domain.application.entity;

import jakarta.persistence.Entity;
import lombok.*;
import team.themoment.hellogsmv3.domain.applicant.entity.Applicant;
import team.themoment.hellogsmv3.domain.application.entity.abs.AbstractApplication;
import team.themoment.hellogsmv3.domain.application.entity.param.AbstractApplicationStatusParameter;

import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class CandidateApplication extends AbstractApplication {

    @Builder
    public CandidateApplication(
            UUID id,
            @NonNull CandidatePersonalInformation personalInformation,
            @NonNull CandidateMiddleSchoolGrade middleSchoolGrade,
            @NonNull AbstractApplicationStatusParameter statusParameter,
            @NonNull Applicant applicant
    ) {
        super(id, personalInformation, middleSchoolGrade, statusParameter, applicant);
    }
}
