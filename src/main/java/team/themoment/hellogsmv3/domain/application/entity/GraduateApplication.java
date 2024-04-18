package team.themoment.hellogsmv3.domain.application.entity;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import team.themoment.hellogsmv3.domain.applicant.entity.Applicant;
import team.themoment.hellogsmv3.domain.application.entity.abs.AbstractApplication;
import team.themoment.hellogsmv3.domain.application.entity.param.AbstractApplicationStatusParameter;

import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class GraduateApplication extends AbstractApplication {

    private GraduateApplication(
            @NonNull UUID id,
            @NonNull GraduatePersonalInformation personalInformation,
            @NonNull GraduateMiddleSchoolGrade middleSchoolGrade,
            @NonNull AbstractApplicationStatusParameter statusParameter,
            @NonNull Applicant applicant
            ) {
        super(id, personalInformation, middleSchoolGrade, statusParameter, applicant);
    }
}
