package team.themoment.hellogsmv3.domain.application.dto.response;

import java.util.List;

public record ApplicationListResDto(
        ApplicationListInfoDto paginationInfo,
        List<ApplicationDto> applications
) {
}
