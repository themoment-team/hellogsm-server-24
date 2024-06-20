package team.themoment.hellogsmv3.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.applicant.dto.response.AdmissionTicketsResDto;
import team.themoment.hellogsmv3.domain.application.entity.*;
import team.themoment.hellogsmv3.domain.application.entity.abs.AbstractApplication;
import team.themoment.hellogsmv3.domain.application.repo.ApplicationRepository;
import team.themoment.hellogsmv3.domain.application.type.GraduationStatus;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class QueryAdmissionTicketsService {

    private final ApplicationRepository applicationRepository;

    public List<AdmissionTicketsResDto> execute() {
        List<AbstractApplication> applications = applicationRepository.findAll();
        List<AdmissionTicketsResDto> admissionTicketsResDtos = new ArrayList<>();

        for (AbstractApplication application : applications) {

            if (application.getPersonalInformation().getGraduation() == GraduationStatus.GED) {
                admissionTicketsResDtos.add(AdmissionTicketsResDto.builder()
                        .applicantName(application.getApplicant().getName())
                        .applicantBirth(application.getApplicant().getBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .applicantImageUri(application.getPersonalInformation().getApplicantImageUri())
                        .screening(application.getSubjectEvaluationResult().getPreScreeningEvaluation())
                        .schoolName(null)
                        .registrationNumber(application.getRegistrationNumber())
                        .build()
                );
            } else if (application.getPersonalInformation().getGraduation() == GraduationStatus.CANDIDATE) {
                CandidatePersonalInformation candidatePersonalInformation = (CandidatePersonalInformation) application.getPersonalInformation().clone();

                admissionTicketsResDtos.add(AdmissionTicketsResDto.builder()
                        .applicantName(application.getApplicant().getName())
                        .applicantBirth(application.getApplicant().getBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .applicantImageUri(application.getPersonalInformation().getApplicantImageUri())
                        .schoolName(candidatePersonalInformation.getSchoolName())
                        .screening(application.getSubjectEvaluationResult().getPreScreeningEvaluation())
                        .registrationNumber(application.getRegistrationNumber())
                        .build()
                );
            } else if (application.getPersonalInformation().getGraduation() == GraduationStatus.GRADUATE) {
                GraduatePersonalInformation graduatePersonalInformation = (GraduatePersonalInformation) application.getPersonalInformation().clone();

                admissionTicketsResDtos.add(AdmissionTicketsResDto.builder()
                        .applicantName(application.getApplicant().getName())
                        .applicantBirth(application.getApplicant().getBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .applicantImageUri(application.getPersonalInformation().getApplicantImageUri())
                        .schoolName(graduatePersonalInformation.getSchoolName())
                        .screening(application.getSubjectEvaluationResult().getPreScreeningEvaluation())
                        .registrationNumber(application.getRegistrationNumber())
                        .build()
                );
            }
        }
        return admissionTicketsResDtos;
    }
}
