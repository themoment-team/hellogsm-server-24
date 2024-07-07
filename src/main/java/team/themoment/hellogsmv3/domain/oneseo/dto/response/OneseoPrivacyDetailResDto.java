package team.themoment.hellogsmv3.domain.oneseo.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import team.themoment.hellogsmv3.domain.member.entity.type.Sex;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.GraduationType;

import java.time.LocalDate;

@Builder
public record OneseoPrivacyDetailResDto(

        String name,
        Sex sex,
        @JsonFormat(pattern="yyyy-MM-dd")
        LocalDate birth,
        String phoneNumber,
        GraduationType graduationType,
        String address,
        String detailAddress,
        String guardianName,
        String guardianPhoneNumber,
        String relationshipWithGuardian,
        String schoolName,
        String schoolAddress,
        String schoolTeacherName,
        String schoolTeacherPhoneNumber,
        String profileImg
) {
}
