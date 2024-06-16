package team.themoment.hellogsmv3.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsmv3.domain.applicant.entity.Applicant;
import team.themoment.hellogsmv3.domain.applicant.repo.ApplicantRepository;
import team.themoment.hellogsmv3.domain.application.dto.request.ApplicationStatusReqDto;
import team.themoment.hellogsmv3.domain.application.entity.*;
import team.themoment.hellogsmv3.domain.application.entity.abs.AbstractApplication;
import team.themoment.hellogsmv3.domain.application.entity.param.AbstractApplicationStatusParameter;
import team.themoment.hellogsmv3.domain.application.repo.ApplicationRepository;
import team.themoment.hellogsmv3.domain.application.type.EvaluationResult;
import team.themoment.hellogsmv3.domain.application.type.EvaluationStatus;
import team.themoment.hellogsmv3.domain.application.type.Major;
import team.themoment.hellogsmv3.domain.application.type.Screening;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

@Service
@RequiredArgsConstructor
public class UpdateApplicationStatusService {

    private final ApplicationRepository applicationRepository;
    private final ApplicantRepository applicantRepository;

    @Transactional
    public void execute(Long applicantId, ApplicationStatusReqDto applicationStatusReqDto) {
        Applicant applicant = applicantRepository.findById(applicantId)
                .orElseThrow(() -> new ExpectedException(String.format("ID(%s)에 해당하는 지원자를 찾을 수 없습니다.", applicantId), HttpStatus.NOT_FOUND));

        AbstractApplication application = applicationRepository.findAbstractApplicationByApplicant(applicant)
                .orElseThrow(() -> new ExpectedException(String.format("ID(%s)에 해당하는 지원자의 원서를 찾을 수 없습니다.", applicantId), HttpStatus.NOT_FOUND));

        switch (application.getPersonalInformation().getGraduation()) {
            case GED -> updateGedApplicationStatus(application, applicationStatusReqDto, applicant);
            case GRADUATE -> updateGraduateApplicationStatus(application, applicationStatusReqDto, applicant);
            case CANDIDATE -> updateCandidateApplicationStatus(application, applicationStatusReqDto, applicant);
        }
    }

    private void updateGedApplicationStatus(AbstractApplication application, ApplicationStatusReqDto applicationStatusReqDto, Applicant applicant) {
        GedApplication updateApplication = GedApplication.builder()
                .id(application.getId())
                .personalInformation((GedPersonalInformation) application.getPersonalInformation())
                .middleSchoolGrade((GedMiddleSchoolGrade) application.getMiddleSchoolGrade())
                .statusParameter(buildAbstractApplicationStatusParameter(application, applicationStatusReqDto))
                .applicant(applicant)
                .build();

        applicationRepository.save(updateApplication);
    }

    private void updateGraduateApplicationStatus(AbstractApplication application, ApplicationStatusReqDto applicationStatusReqDto, Applicant applicant) {
        GraduateApplication updateApplication = GraduateApplication.builder()
                .id(application.getId())
                .personalInformation((GraduatePersonalInformation) application.getPersonalInformation())
                .middleSchoolGrade((GraduateMiddleSchoolGrade) application.getMiddleSchoolGrade())
                .statusParameter(buildAbstractApplicationStatusParameter(application, applicationStatusReqDto))
                .applicant(applicant)
                .build();

        applicationRepository.save(updateApplication);
    }

    private void updateCandidateApplicationStatus(AbstractApplication application, ApplicationStatusReqDto applicationStatusReqDto, Applicant applicant) {
        CandidateApplication updateApplication = CandidateApplication.builder()
                .id(application.getId())
                .personalInformation((CandidatePersonalInformation) application.getPersonalInformation())
                .middleSchoolGrade((CandidateMiddleSchoolGrade) application.getMiddleSchoolGrade())
                .statusParameter(buildAbstractApplicationStatusParameter(application, applicationStatusReqDto))
                .applicant(applicant)
                .build();

        applicationRepository.save(updateApplication);
    }

    private AbstractApplicationStatusParameter buildAbstractApplicationStatusParameter(AbstractApplication application, ApplicationStatusReqDto applicationStatusReqDto) {
        return AbstractApplicationStatusParameter.builder()
                .finalSubmitted(applicationStatusReqDto.isFinalSubmitted())
                .printsArrived(applicationStatusReqDto.isPrintsArrived())
                .subjectEvaluationResult(EvaluationResult.builder()
                        .evaluationStatus(EvaluationStatus.valueOf(applicationStatusReqDto.firstEvaluation()))
                        .preScreeningEvaluation(application.getSubjectEvaluationResult().getPreScreeningEvaluation())
                        .postScreeningEvaluation(Screening.valueOf(applicationStatusReqDto.screeningFirstEvaluationAt()))
                        .build()
                )
                .competencyEvaluationResult(EvaluationResult.builder()
                        .evaluationStatus(EvaluationStatus.valueOf(applicationStatusReqDto.secondEvaluation()))
                        .preScreeningEvaluation(application.getCompetencyEvaluationResult().getPreScreeningEvaluation())
                        .postScreeningEvaluation(Screening.valueOf(applicationStatusReqDto.screeningSecondEvaluationAt()))
                        .build()

                )
                .registrationNumber(applicationStatusReqDto.registrationNumber())
                .competencyExamScore(applicationStatusReqDto.secondScore())
                .desiredMajors(application.getDesiredMajors())
                .finalMajor(Major.valueOf(applicationStatusReqDto.finalMajor()))
                .build();
    }
}
