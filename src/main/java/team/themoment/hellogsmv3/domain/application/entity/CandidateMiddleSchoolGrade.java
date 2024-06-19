package team.themoment.hellogsmv3.domain.application.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import lombok.*;
import team.themoment.hellogsmv3.domain.application.entity.abs.AbstractMiddleSchoolGrade;
import team.themoment.hellogsmv3.domain.application.entity.convert.MiddleSchoolTranscriptConverter;
import team.themoment.hellogsmv3.domain.application.entity.param.CandidateMiddleSchoolGradeParameter;
import team.themoment.hellogsmv3.domain.application.type.MiddleSchoolTranscript;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class CandidateMiddleSchoolGrade extends AbstractMiddleSchoolGrade {

    private BigDecimal grade1Semester1Score;

    private BigDecimal grade1Semester2Score;

    private BigDecimal grade2Semester1Score;

    private BigDecimal grade2Semester2Score;

    private BigDecimal grade3Semester1Score;

    private BigDecimal artisticScore;

    private BigDecimal curricularSubtotalScore;

    private BigDecimal attendanceScore;

    private BigDecimal volunteerScore;

    private BigDecimal extraCurricularSubtotalScore;

    @Builder
    public CandidateMiddleSchoolGrade(
            UUID id,
            @NonNull CandidateMiddleSchoolGradeParameter parameter
    ) {
        super(id, parameter.getPercentileRank(), parameter.getTotalScore(), parameter.getTranscript());
        this.grade1Semester1Score = parameter.getGrade1Semester1Score();
        this.grade1Semester2Score = parameter.getGrade1Semester2Score();
        this.grade2Semester1Score = parameter.getGrade2Semester1Score();
        this.grade2Semester2Score = parameter.getGrade2Semester2Score();
        this.grade3Semester1Score = parameter.getGrade3Semester1Score();
        this.artisticScore = parameter.getArtisticScore();
        this.curricularSubtotalScore = parameter.getCurricularSubtotalScore();
        this.attendanceScore = parameter.getAttendanceScore();
        this.volunteerScore = parameter.getVolunteerScore();
        this.extraCurricularSubtotalScore = parameter.getExtraCurricularSubtotalScore();
    }
}
