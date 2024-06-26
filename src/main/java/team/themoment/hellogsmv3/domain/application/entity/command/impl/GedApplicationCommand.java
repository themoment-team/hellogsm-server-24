package team.themoment.hellogsmv3.domain.application.entity.command.impl;

import com.fasterxml.jackson.annotation.JsonTypeName;
import team.themoment.hellogsmv3.domain.application.entity.command.ApplicationCommand;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.GraduationStatus;

@JsonTypeName("ged")
public record GedApplicationCommand(
        GedPersonalInformationCommand personalInformationCommand
) implements ApplicationCommand {

    @Override
    public GraduationStatus getGraduationStatus() {
        return this.personalInformationCommand.graduation;
    }

    public record GedPersonalInformationCommand(
            String applicantImageUri,
            String address,
            String detailAddress,
            GraduationStatus graduation,
            String telephone,
            String guardianName,
            String relationWithApplicant
    ) {

    }
}
