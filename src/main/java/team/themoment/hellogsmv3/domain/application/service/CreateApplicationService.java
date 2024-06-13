package team.themoment.hellogsmv3.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsmv3.domain.applicant.entity.Applicant;
import team.themoment.hellogsmv3.domain.applicant.repo.ApplicantRepository;
import team.themoment.hellogsmv3.domain.application.dto.request.ApplicationReqDto;
import team.themoment.hellogsmv3.domain.application.entity.*;
import team.themoment.hellogsmv3.domain.application.entity.param.AbstractApplicationStatusParameter;
import team.themoment.hellogsmv3.domain.application.entity.param.AbstractPersonalInformationParameter;
import team.themoment.hellogsmv3.domain.application.entity.param.CandidateMiddleSchoolGradeParameter;
import team.themoment.hellogsmv3.domain.application.repo.ApplicationRepository;
import team.themoment.hellogsmv3.domain.application.type.DesiredMajors;
import team.themoment.hellogsmv3.domain.application.type.EvaluationResult;
import team.themoment.hellogsmv3.domain.application.type.MiddleSchoolTranscript;
import team.themoment.hellogsmv3.domain.auth.repo.AuthenticationRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CreateApplicationService {

    private final ApplicantRepository applicantRepository;
    private final AuthenticationRepository authenticationRepository;
    private final ApplicationRepository applicationRepository;

    @Transactional
    public void execute(ApplicationReqDto dto, Long authenticationId) {

        if (!authenticationRepository.existsById(authenticationId))
            throw new ExpectedException("인증 정보가 존재하지 않습니다.", HttpStatus.NOT_FOUND);

        Applicant currentApplicant = applicantRepository.findByAuthenticationId(authenticationId)
                .orElseThrow(() -> new ExpectedException("존재하지 않는 유저입니다.", HttpStatus.NOT_FOUND));

        if (applicationRepository.existsByApplicant(currentApplicant))
            throw new ExpectedException("이미 원서가 존재합니다.", HttpStatus.BAD_REQUEST);

        switch (dto.graduation()) {
            case CANDIDATE -> saveCandidateApplication(dto, currentApplicant);
            case GRADUATE -> saveGraduateApplication(dto, currentApplicant);
            case GED -> saveGedApplication(dto, currentApplicant);
        }
    }

    private void saveCandidateApplication(ApplicationReqDto dto, Applicant currentApplicant) {
        CandidatePersonalInformation candidatePersonalInformation = buildCandidatePersonalInformation(dto);
        CandidateMiddleSchoolGrade candidateMiddleSchoolGrade = buildCandidateMiddleSchoolGrade();
        CandidateApplication candidateApplication = buildCandidateApplication(
                dto, candidatePersonalInformation, candidateMiddleSchoolGrade, currentApplicant);

        applicationRepository.save(candidateApplication);
    }

    private void saveGedApplication(ApplicationReqDto dto, Applicant currentApplicant) {
        GedPersonalInformation gedPersonalInformation = buildGedPersonalInformation(dto);
        GedMiddleSchoolGrade gedMiddleSchoolGrade = buildGedMiddleSchoolGrade();
        GedApplication gedApplication = buildGedApplication(
                dto, gedPersonalInformation, gedMiddleSchoolGrade, currentApplicant);

        applicationRepository.save(gedApplication);
    }

    private void saveGraduateApplication(ApplicationReqDto dto, Applicant currentApplicant) {
        GraduatePersonalInformation graduatePersonalInformation = buildGraduatePersonalInformation(dto);
        GraduateMiddleSchoolGrade graduateMiddleSchoolGrade = buildGraduateMiddleSchoolGrade();
        GraduateApplication graduateApplication = buildGraduateApplication(
                dto, graduatePersonalInformation, graduateMiddleSchoolGrade, currentApplicant);

        applicationRepository.save(graduateApplication);
    }

    private CandidatePersonalInformation buildCandidatePersonalInformation(ApplicationReqDto dto) {
        return CandidatePersonalInformation.builder()
                .superParameter(AbstractPersonalInformationParameter.builder()
                        .address(dto.address())
                        .applicantImageUri(dto.applicantImageUri())
                        .detailAddress(dto.detailAddress())
                        .graduation(dto.graduation())
                        .phoneNumber(dto.telephone())
                        .guardianName(dto.guardianName())
                        .relationWithApplicant(dto.relationWithApplicant())
                        .build())
                .schoolName(dto.schoolName())
                .schoolLocation(dto.schoolLocation())
                .teacherName(dto.teacherName())
                .teacherPhoneNumber(dto.teacherPhoneNumber())
                .build();
    }

    private CandidateMiddleSchoolGrade buildCandidateMiddleSchoolGrade() {
        return CandidateMiddleSchoolGrade.builder()
                // 환산 로직은 추후 구현 예정
                .parameter(CandidateMiddleSchoolGradeParameter.builder()
                        .transcript(new MiddleSchoolTranscript())
                        .grade1Semester1Score(BigDecimal.ONE)
                        .grade1Semester2Score(BigDecimal.ONE)
                        .grade2Semester1Score(BigDecimal.ONE)
                        .grade2Semester2Score(BigDecimal.ONE)
                        .grade3Semester1Score(BigDecimal.ONE)
                        .artisticScore(BigDecimal.ONE)
                        .volunteerScore(BigDecimal.ONE)
                        .curricularSubtotalScore(BigDecimal.ONE)
                        .attendanceScore(BigDecimal.ONE)
                        .totalScore(BigDecimal.ONE)
                        .percentileRank(BigDecimal.ONE)
                        .extraCurricularSubtotalScore(BigDecimal.ONE)
                        .build())
                .build();
    }

    private CandidateApplication buildCandidateApplication(
            ApplicationReqDto dto,
            CandidatePersonalInformation candidatePersonalInformation,
            CandidateMiddleSchoolGrade candidateMiddleSchoolGrade,
            Applicant applicant
    ) {
        return CandidateApplication.builder()
                .personalInformation(candidatePersonalInformation)
                .middleSchoolGrade(candidateMiddleSchoolGrade)
                .statusParameter(AbstractApplicationStatusParameter.builder()
                        .finalSubmitted(false)
                        .printsArrived(false)
                        .subjectEvaluationResult(EvaluationResult.builder()
                                .preScreeningEvaluation(null)
                                .postScreeningEvaluation(null)
                                .evaluationStatus(null).build())
                        .competencyEvaluationResult(EvaluationResult.builder()
                                .preScreeningEvaluation(null)
                                .postScreeningEvaluation(null)
                                .evaluationStatus(null).build())
                        .registrationNumber(null)
                        .desiredMajors(DesiredMajors.builder()
                                .firstDesiredMajor(dto.firstDesiredMajor())
                                .secondDesiredMajor(dto.secondDesiredMajor())
                                .thirdDesiredMajor(dto.thirdDesiredMajor())
                                .build())
                        .finalMajor(null)
                        .build())
                .applicant(applicant)
                .build();
    }

    private GraduatePersonalInformation buildGraduatePersonalInformation(ApplicationReqDto dto) {
        return GraduatePersonalInformation.builder()
                .superParameter(AbstractPersonalInformationParameter.builder()
                        .address(dto.address())
                        .applicantImageUri(dto.applicantImageUri())
                        .detailAddress(dto.detailAddress())
                        .graduation(dto.graduation())
                        .phoneNumber(dto.telephone())
                        .guardianName(dto.guardianName())
                        .relationWithApplicant(dto.relationWithApplicant())
                        .build())
                .schoolName(dto.schoolName())
                .schoolLocation(dto.schoolLocation())
                .teacherName(dto.teacherName())
                .teacherPhoneNumber(dto.teacherPhoneNumber())
                .build();
    }

    private GraduateMiddleSchoolGrade buildGraduateMiddleSchoolGrade() {
        return GraduateMiddleSchoolGrade.builder()
                // 환산 로직은 추후 구현 예정
                .percentileRank(BigDecimal.ONE)
                .attendanceScore(BigDecimal.ONE)
                .volunteerScore(BigDecimal.ONE)
                .build();
    }

    private GraduateApplication buildGraduateApplication(
            ApplicationReqDto dto,
            GraduatePersonalInformation graduatePersonalInformation,
            GraduateMiddleSchoolGrade graduateMiddleSchoolGrade,
            Applicant currentApplicant) {
        return GraduateApplication.builder()
                .personalInformation(graduatePersonalInformation)
                .middleSchoolGrade(graduateMiddleSchoolGrade)
                .statusParameter(AbstractApplicationStatusParameter.builder()
                        .finalSubmitted(false)
                        .printsArrived(false)
                        .subjectEvaluationResult(EvaluationResult.builder()
                                .preScreeningEvaluation(null)
                                .postScreeningEvaluation(null)
                                .evaluationStatus(null).build())
                        .competencyEvaluationResult(EvaluationResult.builder()
                                .preScreeningEvaluation(null)
                                .postScreeningEvaluation(null)
                                .evaluationStatus(null).build())
                        .registrationNumber(null)
                        .desiredMajors(DesiredMajors.builder()
                                .firstDesiredMajor(dto.firstDesiredMajor())
                                .secondDesiredMajor(dto.secondDesiredMajor())
                                .thirdDesiredMajor(dto.thirdDesiredMajor())
                                .build())
                        .finalMajor(null)
                        .build())
                .applicant(currentApplicant)
                .build();
    }

    private GedPersonalInformation buildGedPersonalInformation(ApplicationReqDto dto) {
        return GedPersonalInformation.builder()
                .superParam(AbstractPersonalInformationParameter.builder()
                        .address(dto.address())
                        .applicantImageUri(dto.applicantImageUri())
                        .detailAddress(dto.detailAddress())
                        .graduation(dto.graduation())
                        .phoneNumber(dto.telephone())
                        .guardianName(dto.guardianName())
                        .relationWithApplicant(dto.relationWithApplicant())
                        .build())
                .build();
    }

    private GedMiddleSchoolGrade buildGedMiddleSchoolGrade() {
        return GedMiddleSchoolGrade.builder()
                .percentileRank(BigDecimal.ONE)
                .gedMaxScore(BigDecimal.ONE)
                .gedTotalScore(BigDecimal.ONE)
                .build();
    }

    private GedApplication buildGedApplication(
            ApplicationReqDto dto,
            GedPersonalInformation gedPersonalInformation,
            GedMiddleSchoolGrade gedMiddleSchoolGrade,
            Applicant currentApplicant) {
        return GedApplication.builder()
                .personalInformation(gedPersonalInformation)
                .middleSchoolGrade(gedMiddleSchoolGrade)
                .statusParameter(AbstractApplicationStatusParameter.builder()
                        .finalSubmitted(false)
                        .printsArrived(false)
                        .subjectEvaluationResult(EvaluationResult.builder()
                                .preScreeningEvaluation(null)
                                .postScreeningEvaluation(null)
                                .evaluationStatus(null).build())
                        .competencyEvaluationResult(EvaluationResult.builder()
                                .preScreeningEvaluation(null)
                                .postScreeningEvaluation(null)
                                .evaluationStatus(null).build())
                        .registrationNumber(null)
                        .desiredMajors(DesiredMajors.builder()
                                .firstDesiredMajor(dto.firstDesiredMajor())
                                .secondDesiredMajor(dto.secondDesiredMajor())
                                .thirdDesiredMajor(dto.thirdDesiredMajor())
                                .build())
                        .finalMajor(null)
                        .build())
                .applicant(currentApplicant)
                .build();
    }

}
