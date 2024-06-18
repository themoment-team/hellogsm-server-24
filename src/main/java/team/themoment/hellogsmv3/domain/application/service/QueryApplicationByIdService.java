package team.themoment.hellogsmv3.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.applicant.entity.Applicant;
import team.themoment.hellogsmv3.domain.application.dto.response.*;
import team.themoment.hellogsmv3.domain.application.entity.*;
import team.themoment.hellogsmv3.domain.application.entity.abs.AbstractApplication;
import team.themoment.hellogsmv3.domain.application.repo.ApplicationRepository;
import team.themoment.hellogsmv3.domain.application.type.GraduationStatus;
import team.themoment.hellogsmv3.domain.application.type.Screening;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

@Service
@RequiredArgsConstructor
public class QueryApplicationByIdService {

    private final ApplicationRepository applicationRepository;

    public FoundApplicationResDto execute(Long applicantId) {
        AbstractApplication application = applicationRepository.findByAuthenticationIdWithAllJoins(applicantId)
                .orElseThrow(() -> new ExpectedException(String.format("ID(%s)에 해당하는 지원자의 원서를 찾을 수 없습니다.", applicantId), HttpStatus.NOT_FOUND));

        AdmissionInfoResDto admissionInfoResDto;
        AdmissionGradeResDto admissionGradeResDto;

        Screening screening = getScreening(application);

        if (application.getPersonalInformation().getGraduation().equals(GraduationStatus.GED)) {
            admissionInfoResDto = buildGedAdmissionInfoResDto(application, screening);
            admissionGradeResDto = buildGedAdmissionGradeResDto(application);
        } else {
            admissionInfoResDto = buildGeneralAdmissionInfoResDto(application, screening);
            admissionGradeResDto = buildGeneralAdmissionGradeResDto(application);
        }

        AdmissionStatusResDto admissionStatusResDto = buildAdmissionStatusResDto(application);

        return new FoundApplicationResDto(
                application.getId(),
                admissionInfoResDto,
                application.getMiddleSchoolGrade().getTranscript(),
                admissionGradeResDto,
                admissionStatusResDto
        );
    }

    private Screening getScreening(AbstractApplication application) {
        Screening screening;

        if (!application.getSubjectEvaluationResult().isPass()) {
            screening = application.getSubjectEvaluationResult().getPreScreeningEvaluation();
        } else if (!application.getCompetencyEvaluationResult().isPass()) {
            screening = application.getCompetencyEvaluationResult().getPreScreeningEvaluation();
        } else {
            screening = application.getCompetencyEvaluationResult().getPostScreeningEvaluation();
        }

        return screening;
    }

    private GedAdmissionInfoResDto buildGedAdmissionInfoResDto(AbstractApplication application, Screening screening) {
        Applicant applicant = application.getApplicant();
        GedPersonalInformation personalInformation = (GedPersonalInformation) application.getPersonalInformation();

        return GedAdmissionInfoResDto.builder()
                .applicantName(applicant.getName())
                .applicantGender(applicant.getGender())
                .applicantBirth(applicant.getBirth())
                .address(personalInformation.getAddress())
                .detailAddress(personalInformation.getDetailAddress())
                .graduation(personalInformation.getGraduation())
                .telephone(personalInformation.getPhoneNumber())
                .applicantPhoneNumber(applicant.getPhoneNumber())
                .guardianName(personalInformation.getGuardianName())
                .relationWithApplicant(personalInformation.getRelationWithApplicant())
                .guardianPhoneNumber(personalInformation.getGuardianPhoneNumber())
                .applicantImageUri(personalInformation.getApplicantImageUri())
                .desiredMajor(application.getDesiredMajors())
                .screening(screening)
                .build();
    }

    private GedAdmissionGradeResDto buildGedAdmissionGradeResDto(AbstractApplication application) {
        GedMiddleSchoolGrade middleSchoolGrade = (GedMiddleSchoolGrade) application.getMiddleSchoolGrade();

        return GedAdmissionGradeResDto.builder()
                .totalScore(middleSchoolGrade.getTotalScore())
                .percentileRank(middleSchoolGrade.getPercentileRank())
                .gedTotalScore(middleSchoolGrade.getGedTotalScore())
                .gedMaxScore(middleSchoolGrade.getGedMaxScore())
                .build();
    }

    private GeneralAdmissionInfoResDto buildGeneralAdmissionInfoResDto(AbstractApplication application, Screening screening) {
        Applicant applicant = application.getApplicant();
        CandidatePersonalInformation personalInformation = (CandidatePersonalInformation) application.getPersonalInformation();

        return GeneralAdmissionInfoResDto.builder()
                .applicantName(applicant.getName())
                .applicantGender(applicant.getGender())
                .applicantBirth(applicant.getBirth())
                .address(personalInformation.getAddress())
                .detailAddress(personalInformation.getDetailAddress())
                .graduation(personalInformation.getGraduation())
                .telephone(personalInformation.getPhoneNumber())
                .applicantPhoneNumber(applicant.getPhoneNumber())
                .guardianName(personalInformation.getGuardianName())
                .relationWithApplicant(personalInformation.getRelationWithApplicant())
                .guardianPhoneNumber(personalInformation.getGuardianPhoneNumber())
                .teacherName(personalInformation.getTeacherName())
                .teacherPhoneNumber(personalInformation.getTeacherPhoneNumber())
                .schoolName(personalInformation.getSchoolName())
                .schoolLocation(personalInformation.getSchoolLocation())
                .applicantImageUri(personalInformation.getApplicantImageUri())
                .desiredMajor(application.getDesiredMajors())
                .screening(screening)
                .build();
    }

    private GeneralAdmissionGradeResDto buildGeneralAdmissionGradeResDto(AbstractApplication application) {
        CandidateMiddleSchoolGrade middleSchoolGrade = (CandidateMiddleSchoolGrade) application.getMiddleSchoolGrade();

        return GeneralAdmissionGradeResDto.builder()
                .totalScore(middleSchoolGrade.getTotalScore())
                .percentileRank(middleSchoolGrade.getPercentileRank())
                .grade1Semester1Score(middleSchoolGrade.getGrade1Semester1Score())
                .grade1Semester2Score(middleSchoolGrade.getGrade1Semester2Score())
                .grade2Semester1Score(middleSchoolGrade.getGrade2Semester1Score())
                .grade2Semester2Score(middleSchoolGrade.getGrade2Semester2Score())
                .grade3Semester1Score(middleSchoolGrade.getGrade3Semester1Score())
                .artisticScore(middleSchoolGrade.getArtisticScore())
                .curricularSubtotalScore(middleSchoolGrade.getCurricularSubtotalScore())
                .attendanceScore(middleSchoolGrade.getAttendanceScore())
                .volunteerScore(middleSchoolGrade.getVolunteerScore())
                .extracurricularSubtotalScore(middleSchoolGrade.getExtraCurricularSubtotalScore())
                .build();
    }

    private AdmissionStatusResDto buildAdmissionStatusResDto(AbstractApplication application) {
        return AdmissionStatusResDto.builder()
                .isFinalSubmitted(application.getFinalSubmitted())
                .isPrintsArrived(application.getPrintsArrived())
                .firstEvaluation(application.getSubjectEvaluationResult().getEvaluationStatus())
                .secondEvaluation(application.getCompetencyEvaluationResult().getEvaluationStatus())
                .screeningFirstEvaluationAt(application.getSubjectEvaluationResult() != null ? application.getSubjectEvaluationResult().getPostScreeningEvaluation() : null)
                .screeningSecondEvaluationAt(application.getCompetencyEvaluationResult() != null ? application.getCompetencyEvaluationResult().getPostScreeningEvaluation() : null)
                .registrationNumber(application.getRegistrationNumber())
                .secondScore(application.getCompetencyExamScore())
                .finalMajor(application.getFinalMajor())
                .build();
    }
}
