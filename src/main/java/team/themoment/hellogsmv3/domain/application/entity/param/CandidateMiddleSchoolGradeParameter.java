package team.themoment.hellogsmv3.domain.application.entity.param;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import team.themoment.hellogsmv3.domain.application.type.MiddleSchoolTranscript;

import java.math.BigDecimal;

@Getter
public final class CandidateMiddleSchoolGradeParameter {

    private final MiddleSchoolTranscript transcript;

    private final BigDecimal percentileRank;

    private final BigDecimal grade1Semester1Score;

    private final BigDecimal grade1Semester2Score;

    private final BigDecimal grade2Semester1Score;

    private final BigDecimal grade2Semester2Score;

    private final BigDecimal grade3Semester1Score;

    private final BigDecimal artisticScore;

    private final BigDecimal curricularSubtotalScore;

    private final BigDecimal attendanceScore;

    private final BigDecimal volunteerScore;

    private final BigDecimal extraCurricularSubtotalScore;

    private final BigDecimal totalScore;

    @Builder
    public CandidateMiddleSchoolGradeParameter(
            @NonNull MiddleSchoolTranscript transcript,
            @NonNull BigDecimal percentileRank,
            @NonNull BigDecimal grade1Semester1Score,
            @NonNull BigDecimal grade1Semester2Score,
            @NonNull BigDecimal grade2Semester1Score,
            @NonNull BigDecimal grade2Semester2Score,
            @NonNull BigDecimal grade3Semester1Score,
            @NonNull BigDecimal artisticScore,
            @NonNull BigDecimal curricularSubtotalScore,
            @NonNull BigDecimal attendanceScore,
            @NonNull BigDecimal volunteerScore,
            @NonNull BigDecimal extraCurricularSubtotalScore,
            @NonNull BigDecimal totalScore) {
        this.transcript = transcript;
        this.percentileRank = percentileRank;
        this.grade1Semester1Score = grade1Semester1Score;
        this.grade1Semester2Score = grade1Semester2Score;
        this.grade2Semester1Score = grade2Semester1Score;
        this.grade2Semester2Score = grade2Semester2Score;
        this.grade3Semester1Score = grade3Semester1Score;
        this.artisticScore = artisticScore;
        this.curricularSubtotalScore = curricularSubtotalScore;
        this.attendanceScore = attendanceScore;
        this.volunteerScore = volunteerScore;
        this.extraCurricularSubtotalScore = extraCurricularSubtotalScore;
        this.totalScore = totalScore;
    }
}
