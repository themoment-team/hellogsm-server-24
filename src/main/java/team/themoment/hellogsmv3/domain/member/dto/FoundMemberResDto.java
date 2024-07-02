package team.themoment.hellogsmv3.domain.member.dto;

import team.themoment.hellogsmv3.domain.member.entity.type.Gender;

import java.time.LocalDate;

public record FoundMemberResDto(

        Long memberId,
        String name,
        String phoneNumber,
        LocalDate birth,
        Gender sex
) {
}
