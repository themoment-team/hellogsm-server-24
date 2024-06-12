package team.themoment.hellogsmv3.domain.application.dto.response;

import java.util.UUID;

public record FoundApplicationResDto(
        UUID id,
        AdmissionInfoResDto admissionInfo,
        String middleSchoolGrade,
        AdmissionGradeResDto admissionGrade,
        AdmissionStatusResDto admissionStatus
) {
}
