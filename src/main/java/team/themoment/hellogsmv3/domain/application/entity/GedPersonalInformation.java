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
public final class GedPersonalInformation extends AbstractPersonalInformation {

    private GedPersonalInformation(
            @NonNull UUID id,
            @NonNull AbstractPersonalInformationParameter superParam
    ) {
        super(id, superParam);
    }
}
