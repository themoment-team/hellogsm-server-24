package team.themoment.hellogsmv3.domain.application.entity;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import team.themoment.hellogsmv3.domain.application.entity.abs.AbstractPersonalInformation;
import team.themoment.hellogsmv3.domain.application.entity.param.AbstractPersonalInformationParameter;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class GraduatePersonalInformation extends AbstractPersonalInformation {

    private String schoolName;

    private String schoolLocation;

    private String teacherName;

    private String teacherPhoneNumber;

    private GraduatePersonalInformation(
            @NonNull UUID id,
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
