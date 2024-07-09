package team.themoment.hellogsmv3.domain.oneseo.dto.response;

import java.util.List;

public record SearchOneseosResDto(

        SearchOneseoPageInfoDto info,
        List<SearchOneseoResDto> applications
) {
}
