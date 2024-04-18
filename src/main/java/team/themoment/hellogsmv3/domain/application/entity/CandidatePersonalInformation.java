package team.themoment.hellogsmv3.domain.application.entity;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import team.themoment.hellogsmv3.domain.application.entity.abs.AbstractPersonalInformation;
import team.themoment.hellogsmv3.domain.application.entity.param.AbstractPersonalInformationParameter;

import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class CandidatePersonalInformation extends AbstractPersonalInformation {

    private String schoolName;

    private String schoolLocation;

    private String teacherName;

    private String teacherPhoneNumber;

    public CandidatePersonalInformation(
            UUID id,
            @NonNull AbstractPersonalInformationParameter superParameter,
            @NonNull String schoolName,
            @NonNull String schoolLocation,
            @NonNull String teacherName,
            @NonNull String teacherPhoneNumber
    ) {
        super(id, superParameter);
        this.schoolName = schoolName;
        this.schoolLocation = schoolLocation;
        this.teacherName = teacherName;
        this.teacherPhoneNumber = teacherPhoneNumber;
    }
}
