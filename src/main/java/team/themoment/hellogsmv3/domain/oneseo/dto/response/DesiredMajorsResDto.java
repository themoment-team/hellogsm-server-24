package team.themoment.hellogsmv3.domain.oneseo.dto.response;

import team.themoment.hellogsmv3.domain.oneseo.entity.type.Major;

public record DesiredMajorsResDto(

        Major firstDesiredMajor,
        Major secondDesiredMajor,
        Major thirdDesiredMajor
) {
}
