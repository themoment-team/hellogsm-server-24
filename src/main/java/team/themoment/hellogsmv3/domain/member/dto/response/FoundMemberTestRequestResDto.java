package team.themoment.hellogsmv3.domain.member.dto.response;

import lombok.Builder;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo;

@Builder
public record FoundMemberTestRequestResDto(
        YesNo firstTestPassYn,
        YesNo secondTestPassYn
) {
}
