package team.themoment.hellogsmv3.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsmv3.domain.applicant.entity.Applicant;
import team.themoment.hellogsmv3.domain.applicant.repo.ApplicantRepository;
import team.themoment.hellogsmv3.domain.application.dto.request.ApplicationReqDto;
import team.themoment.hellogsmv3.domain.application.entity.*;
import team.themoment.hellogsmv3.domain.application.entity.abs.AbstractApplication;
import team.themoment.hellogsmv3.domain.application.entity.param.AbstractApplicationStatusParameter;
import team.themoment.hellogsmv3.domain.application.entity.param.AbstractPersonalInformationParameter;
import team.themoment.hellogsmv3.domain.application.entity.param.CandidateMiddleSchoolGradeParameter;
import team.themoment.hellogsmv3.domain.application.repo.ApplicationRepository;
import team.themoment.hellogsmv3.domain.application.repo.MiddleSchoolGradeRepository;
import team.themoment.hellogsmv3.domain.application.repo.PersonalInformationRepository;
import team.themoment.hellogsmv3.domain.application.type.DesiredMajors;
import team.themoment.hellogsmv3.domain.application.type.MiddleSchoolTranscript;
import team.themoment.hellogsmv3.domain.auth.repo.AuthenticationRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ModifyApplicationService {

    private final ApplicantRepository applicantRepository;
    private final AuthenticationRepository authenticationRepository;
    private final ApplicationRepository applicationRepository;
    private final MiddleSchoolGradeRepository middleSchoolGradeRepository;
    private final PersonalInformationRepository personalInformationRepository;

    @Transactional
    public void execute(ApplicationReqDto dto, Long authenticationId, boolean isAdmin) {

        Applicant applicant = applicantRepository.findByAuthenticationId(authenticationId)
                .orElseThrow(() -> new ExpectedException("존재하지 않는 유저입니다.", HttpStatus.NOT_FOUND));

        AbstractApplication application = applicationRepository.findByApplicant(applicant)
                .orElseThrow(() -> new ExpectedException("원서를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        if (!isAdmin && application.getFinalSubmitted())
            throw new ExpectedException("최종제출이 완료된 원서는 수정할 수 없습니다.", HttpStatus.BAD_REQUEST);

        if (!authenticationRepository.existsById(applicant.getAuthenticationId()))
            throw new ExpectedException("인증 정보가 존재하지 않습니다.", HttpStatus.NOT_FOUND);

        deleteOldApplication(application);
        saveUpdatedApplication(dto, application, applicant);

    }

    private void deleteOldApplication(AbstractApplication application) {
        applicationRepository.deleteQueryApplicationById(application.getId());
        middleSchoolGradeRepository.deleteQueryMiddleSchoolGrade(application.getMiddleSchoolGrade().getId());
        personalInformationRepository.deleteQueryPersonalInformation(application.getPersonalInformation().getId());
    }

    private void saveUpdatedApplication(ApplicationReqDto dto, AbstractApplication application, Applicant applicant) {
        switch (dto.graduation()) {
            case CANDIDATE -> {
                CandidatePersonalInformation candidatePersonalInformation = createCandidatePersonalInformation(dto, application);
                CandidateMiddleSchoolGrade candidateMiddleSchoolGrade = createCandidateMiddleSchoolGrade(application);
                CandidateApplication candidateApplication = createCandidateApplication(
                        dto, candidatePersonalInformation, candidateMiddleSchoolGrade, applicant, application);

                applicationRepository.save(candidateApplication);
            }
            case GRADUATE -> {
                GraduatePersonalInformation graduatePersonalInformation = createGraduatePersonalInformation(dto, application);
                GraduateMiddleSchoolGrade graduateMiddleSchoolGrade = createGraduateMiddleSchoolGrade(application);
                GraduateApplication graduateApplication = createGraduateApplication(
                        dto, graduatePersonalInformation, graduateMiddleSchoolGrade, applicant, application);

                applicationRepository.save(graduateApplication);
            }
            case GED -> {
                GedPersonalInformation gedPersonalInformation = createGedPersonalInformation(dto, application);
                GedMiddleSchoolGrade gedMiddleSchoolGrade = createGedMiddleSchoolGrade(application);
                GedApplication gedApplication = createGedApplication(dto, gedPersonalInformation, gedMiddleSchoolGrade, applicant, application);

                applicationRepository.save(gedApplication);
            }
        }
    }

    private CandidatePersonalInformation createCandidatePersonalInformation(ApplicationReqDto dto, AbstractApplication application) {
        return CandidatePersonalInformation.builder()
                .id(application.getPersonalInformation().getId())
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

    private CandidateMiddleSchoolGrade createCandidateMiddleSchoolGrade(AbstractApplication application) {
        return CandidateMiddleSchoolGrade.builder()
                // 환산 로직은 추후 구현 예정
                .id(application.getMiddleSchoolGrade().getId())
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

    private CandidateApplication createCandidateApplication(
            ApplicationReqDto dto,
            CandidatePersonalInformation candidatePersonalInformation,
            CandidateMiddleSchoolGrade candidateMiddleSchoolGrade,
            Applicant applicant,
            AbstractApplication application
    ) {
        return CandidateApplication.builder()
                .id(application.getId())
                .personalInformation(candidatePersonalInformation)
                .middleSchoolGrade(candidateMiddleSchoolGrade)
                .statusParameter(AbstractApplicationStatusParameter.builder()
                        .finalSubmitted(application.getFinalSubmitted())
                        .printsArrived(application.getPrintsArrived())
                        .subjectEvaluationResult(application.getSubjectEvaluationResult())
                        .competencyEvaluationResult(application.getCompetencyEvaluationResult())
                        .registrationNumber(application.getRegistrationNumber())
                        .desiredMajors(DesiredMajors.builder()
                                .firstDesiredMajor(dto.firstDesiredMajor())
                                .secondDesiredMajor(dto.secondDesiredMajor())
                                .thirdDesiredMajor(dto.thirdDesiredMajor())
                                .build())
                        .finalMajor(application.getFinalMajor())
                        .build())
                .applicant(applicant)
                .build();
    }

    private GraduatePersonalInformation createGraduatePersonalInformation(ApplicationReqDto dto, AbstractApplication application) {
        return GraduatePersonalInformation.builder()
                .id(application.getPersonalInformation().getId())
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

    private GraduateMiddleSchoolGrade createGraduateMiddleSchoolGrade(AbstractApplication application) {
        return GraduateMiddleSchoolGrade.builder()
                // 환산 로직은 추후 구현 예정
                .id(application.getMiddleSchoolGrade().getId())
                .percentileRank(BigDecimal.ONE)
                .attendanceScore(BigDecimal.ONE)
                .volunteerScore(BigDecimal.ONE)
                .build();
    }

    private GraduateApplication createGraduateApplication(
            ApplicationReqDto dto,
            GraduatePersonalInformation graduatePersonalInformation,
            GraduateMiddleSchoolGrade graduateMiddleSchoolGrade,
            Applicant applicant,
            AbstractApplication application
    ) {
        return GraduateApplication.builder()
                .id(application.getId())
                .personalInformation(graduatePersonalInformation)
                .middleSchoolGrade(graduateMiddleSchoolGrade)
                .statusParameter(AbstractApplicationStatusParameter.builder()
                        .finalSubmitted(application.getFinalSubmitted())
                        .printsArrived(application.getPrintsArrived())
                        .subjectEvaluationResult(application.getSubjectEvaluationResult())
                        .competencyEvaluationResult(application.getCompetencyEvaluationResult())
                        .registrationNumber(application.getRegistrationNumber())
                        .desiredMajors(DesiredMajors.builder()
                                .firstDesiredMajor(dto.firstDesiredMajor())
                                .secondDesiredMajor(dto.secondDesiredMajor())
                                .thirdDesiredMajor(dto.thirdDesiredMajor())
                                .build())
                        .finalMajor(application.getFinalMajor())
                        .build())
                .applicant(applicant)
                .build();
    }

    private GedPersonalInformation createGedPersonalInformation(ApplicationReqDto dto, AbstractApplication application) {
        return GedPersonalInformation.builder()
                .id(application.getPersonalInformation().getId())
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

    private GedMiddleSchoolGrade createGedMiddleSchoolGrade(AbstractApplication application) {
        return GedMiddleSchoolGrade.builder()
                // 환산 로직은 추후 구현 예정
                .id(application.getMiddleSchoolGrade().getId())
                .percentileRank(BigDecimal.ONE)
                .gedMaxScore(BigDecimal.ONE)
                .gedTotalScore(BigDecimal.ONE)
                .build();
    }

    private GedApplication createGedApplication(
            ApplicationReqDto dto,
            GedPersonalInformation gedPersonalInformation,
            GedMiddleSchoolGrade gedMiddleSchoolGrade,
            Applicant applicant,
            AbstractApplication application
    ) {
        return GedApplication.builder()
                .id(application.getId())
                .personalInformation(gedPersonalInformation)
                .middleSchoolGrade(gedMiddleSchoolGrade)
                .statusParameter(AbstractApplicationStatusParameter.builder()
                        .finalSubmitted(application.getFinalSubmitted())
                        .printsArrived(application.getPrintsArrived())
                        .subjectEvaluationResult(application.getSubjectEvaluationResult())
                        .competencyEvaluationResult(application.getCompetencyEvaluationResult())
                        .registrationNumber(application.getRegistrationNumber())
                        .desiredMajors(DesiredMajors.builder()
                                .firstDesiredMajor(dto.firstDesiredMajor())
                                .secondDesiredMajor(dto.secondDesiredMajor())
                                .thirdDesiredMajor(dto.thirdDesiredMajor())
                                .build())
                        .finalMajor(application.getFinalMajor())
                        .build())
                .applicant(applicant)
                .build();
    }

}
