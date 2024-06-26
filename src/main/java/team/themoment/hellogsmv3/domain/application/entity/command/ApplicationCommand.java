package team.themoment.hellogsmv3.domain.application.entity.command;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import team.themoment.hellogsmv3.domain.application.entity.command.impl.CandidateApplicationCommand;
import team.themoment.hellogsmv3.domain.application.entity.command.impl.GedApplicationCommand;
import team.themoment.hellogsmv3.domain.application.entity.command.impl.GraduateApplicationCommand;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.GraduationStatus;

@JsonSubTypes({
        @JsonSubTypes.Type(value = CandidateApplicationCommand.class, name = "candidate"),
        @JsonSubTypes.Type(value = GedApplicationCommand.class, name = "ged"),
        @JsonSubTypes.Type(value = GraduateApplicationCommand.class, name = "graduate")
})
public interface ApplicationCommand {
    GraduationStatus getGraduationStatus();
}
