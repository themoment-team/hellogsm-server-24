package team.themoment.hellogsmv3.domain.oneseo.dto.response;


import java.util.List;

public record TranscriptResDto(

        List<String> score1_1,
        List<String> score1_2,
        List<String> score2_1,
        List<String> score2_2,
        List<String> score3_1,
        List<String> artSportsScore,
        List<String> absentScore,
        List<String> attendanceScore,
        List<String> volunteerScore,
        List<String> subjects,
        List<String> nonSubjects,
        String system,
        String freeSemester
) {
}
