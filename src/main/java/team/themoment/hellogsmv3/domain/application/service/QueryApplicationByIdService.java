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

    public FoundApplicationResDto execute(Long authenticationId) {
        AbstractApplication application = applicationRepository.findByApplicantAuthenticationIdWithAllJoins(authenticationId)
                .orElseThrow(() -> new ExpectedException(String.format("ID(%s)에 해당하는 사용자의 정보를 찾을 수 없습니다.", authenticationId), HttpStatus.NOT_FOUND));

        AdmissionInfoResDto admissionInfoResDto;
        AdmissionGradeResDto admissionGradeResDto;
        Applicant applicant = application.getApplicant();

        Screening screening;
        if (!application.getSubjectEvaluationResult().isPass()) {
            screening = application.getSubjectEvaluationResult().getPreScreeningEvaluation();
        } else if (!application.getCompetencyEvaluationResult().isPass()) {
            screening = application.getCompetencyEvaluationResult().getPreScreeningEvaluation();
        } else {
            screening = application.getCompetencyEvaluationResult().getPostScreeningEvaluation();
        }

        if (application.getPersonalInformation().getGraduation().equals(GraduationStatus.GED)) {
            GedPersonalInformation personalInformation = (GedPersonalInformation) application.getPersonalInformation();
            GedMiddleSchoolGrade middleSchoolGrade = (GedMiddleSchoolGrade) application.getMiddleSchoolGrade();

            admissionInfoResDto = GedAdmissionInfoResDto.builder()
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
            admissionGradeResDto = GedAdmissionGradeResDto.builder()
                    .totalScore(middleSchoolGrade.getTotalScore())
                    .percentileRank(middleSchoolGrade.getPercentileRank())
                    .gedTotalScore(middleSchoolGrade.getGedTotalScore())
                    .gedMaxScore(middleSchoolGrade.getGedMaxScore())
                    .build();
        } else {
            CandidatePersonalInformation personalInformation = (CandidatePersonalInformation) application.getPersonalInformation();
            CandidateMiddleSchoolGrade middleSchoolGrade = (CandidateMiddleSchoolGrade) application.getMiddleSchoolGrade();
            admissionInfoResDto = GeneralAdmissionInfoResDto.builder()
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
            admissionGradeResDto = GeneralAdmissionGradeResDto.builder()
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

        AdmissionStatusResDto admissionStatusResDto = AdmissionStatusResDto.builder()
                .isFinalSubmitted(application.getFinalSubmitted())
                .isPrintsArrived(application.getPrintsArrived())
                .firstEvaluation(application.getSubjectEvaluationResult() != null ? application.getSubjectEvaluationResult().getEvaluationStatus() : null)
                .secondEvaluation(application.getCompetencyEvaluationResult() != null ? application.getCompetencyEvaluationResult().getEvaluationStatus() : null)
                .screeningFirstEvaluationAt(application.getSubjectEvaluationResult() != null ? application.getSubjectEvaluationResult().getPostScreeningEvaluation() : null)
                .screeningSecondEvaluationAt(application.getCompetencyEvaluationResult() != null ? application.getCompetencyEvaluationResult().getPostScreeningEvaluation() : null)
                .registrationNumber(application.getRegistrationNumber())
                .secondScore(application.getCompetencyExamScore())
                .finalMajor(application.getFinalMajor())
                .build();

        return new FoundApplicationResDto(
                application.getId(),
                admissionInfoResDto,
                application.getMiddleSchoolGrade().getTranscript(),
                admissionGradeResDto,
                admissionStatusResDto
        );
    }
}
