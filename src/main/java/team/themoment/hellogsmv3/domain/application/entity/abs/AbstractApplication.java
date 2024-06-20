package team.themoment.hellogsmv3.domain.application.entity.abs;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import team.themoment.hellogsmv3.domain.applicant.entity.Applicant;
import team.themoment.hellogsmv3.domain.application.entity.param.AbstractApplicationStatusParameter;
import team.themoment.hellogsmv3.domain.application.type.DesiredMajors;
import team.themoment.hellogsmv3.domain.application.type.EvaluationResult;
import team.themoment.hellogsmv3.domain.application.type.Major;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class AbstractApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @NotNull
    @Column(unique = true)
    protected UUID id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @Cascade(value = CascadeType.ALL)
    protected AbstractMiddleSchoolGrade middleSchoolGrade;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @Cascade(value = CascadeType.ALL)
    protected AbstractPersonalInformation personalInformation;

    @NotNull
    protected Boolean finalSubmitted;

    @NotNull
    protected Boolean printsArrived;

    @Nullable
    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "evaluationStatus", column = @Column(name = "subject_evaluation_result_status")),
            @AttributeOverride( name = "preScreeningEvaluation", column = @Column(name = "subject_evaluation_result_pre_screening")),
            @AttributeOverride( name = "postScreeningEvaluation", column = @Column(name = "subject_evaluation_result_post_screening")),
            @AttributeOverride( name = "score", column = @Column(name = "subject_evaluation_result_score")) // 1차 서류 전형 점수
    })
    protected EvaluationResult subjectEvaluationResult;

    @Nullable
    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "evaluationStatus", column = @Column(name = "competency_evaluation_result_status")),
            @AttributeOverride( name = "preScreeningEvaluation", column = @Column(name = "competency_evaluation_result_pre_screening")),
            @AttributeOverride( name = "postScreeningEvaluation", column = @Column(name = "competency_evaluation_result_post_screening")),
            @AttributeOverride( name = "score", column = @Column(name = "competency_evaluation_result_score")) // 2차 인적성 검사 점수
    })
    protected EvaluationResult competencyEvaluationResult;

    @Nullable
    protected BigDecimal interviewScore; // 심층면접 점수

    @Nullable
    protected Long registrationNumber;

    @NotNull
    @Embedded
    protected DesiredMajors desiredMajors;

    @Nullable
    protected Major finalMajor;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "applicant_id")
    protected Applicant applicant;

    protected AbstractApplication(
            UUID id,
            @NonNull AbstractPersonalInformation personalInformation,
            @NonNull AbstractMiddleSchoolGrade middleSchoolGrade,
            @NonNull AbstractApplicationStatusParameter parameter,
            @NonNull Applicant applicant) {
        this.id = id;
        this.personalInformation = personalInformation;
        this.middleSchoolGrade = middleSchoolGrade;
        this.finalSubmitted = parameter.finalSubmitted();
        this.printsArrived = parameter.printsArrived();
        this.subjectEvaluationResult = parameter.subjectEvaluationResult();
        this.competencyEvaluationResult = parameter.competencyEvaluationResult();
        this.registrationNumber = parameter.registrationNumber();
        this.desiredMajors = parameter.desiredMajors();
        this.finalMajor = parameter.finalMajor();
        this.applicant = applicant;
    }

    public void updateFinalSubmission() {
        this.finalSubmitted = true;
    }
}
