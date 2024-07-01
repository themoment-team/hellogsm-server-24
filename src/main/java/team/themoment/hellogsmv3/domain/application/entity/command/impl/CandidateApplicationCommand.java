package team.themoment.hellogsmv3.domain.application.entity.command.impl;

import com.fasterxml.jackson.annotation.JsonTypeName;
import team.themoment.hellogsmv3.domain.application.entity.command.ApplicationCommand;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.GraduationType;

@JsonTypeName("candidate")
public record CandidateApplicationCommand(
        CandidatePersonalInformationCommand personalInformationCommand
) implements ApplicationCommand {

    @Override
    public GraduationType getGraduationStatus() {
        return this.personalInformationCommand.graduation;
    }

    public record CandidatePersonalInformationCommand(
            String applicantImageUri,
            String address,
            String detailAddress,
            GraduationType graduation,
            String telephone,
            String guardianName,
            String relationWithApplicant,
            String schoolName,
            String schoolLocation,
            String teacherName,
            String teacherPhoneNumber
    ) {

    }
}
