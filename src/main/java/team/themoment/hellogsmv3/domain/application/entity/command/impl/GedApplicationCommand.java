package team.themoment.hellogsmv3.domain.application.entity.command.impl;

import com.fasterxml.jackson.annotation.JsonTypeName;
import team.themoment.hellogsmv3.domain.application.entity.command.ApplicationCommand;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.GraduationType;

@JsonTypeName("ged")
public record GedApplicationCommand(
        GedPersonalInformationCommand personalInformationCommand
) implements ApplicationCommand {

    @Override
    public GraduationType getGraduationStatus() {
        return this.personalInformationCommand.graduation;
    }

    public record GedPersonalInformationCommand(
            String applicantImageUri,
            String address,
            String detailAddress,
            GraduationType graduation,
            String telephone,
            String guardianName,
            String relationWithApplicant
    ) {

    }
}
