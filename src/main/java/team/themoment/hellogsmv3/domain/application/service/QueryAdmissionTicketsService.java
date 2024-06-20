package team.themoment.hellogsmv3.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.applicant.dto.response.AdmissionTicketsResDto;
import team.themoment.hellogsmv3.domain.application.entity.*;
import team.themoment.hellogsmv3.domain.application.entity.abs.AbstractApplication;
import team.themoment.hellogsmv3.domain.application.repo.ApplicationRepository;

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
            switch (application.getPersonalInformation().getGraduation()) {
                case GED -> admissionTicketsResDtos.add(buildGedAdmissionTicketsResDto(application));
                case CANDIDATE -> admissionTicketsResDtos.add(buildCandidateAdmissionTicketsResDto(application));
                case GRADUATE -> admissionTicketsResDtos.add(buildGraduateAdmissionTicketsResDto(application));
            }
        }

        return admissionTicketsResDtos;
    }

    private AdmissionTicketsResDto buildGedAdmissionTicketsResDto (AbstractApplication application) {
        return AdmissionTicketsResDto.builder()
                .applicantName(application.getApplicant().getName())
                .applicantBirth(application.getApplicant().getBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .applicantImageUri(application.getPersonalInformation().getApplicantImageUri())
                .screening(application.getSubjectEvaluationResult().getPreScreeningEvaluation())
                .schoolName(null)
                .registrationNumber(application.getRegistrationNumber())
                .build();
    }

    private AdmissionTicketsResDto buildCandidateAdmissionTicketsResDto (AbstractApplication application) {
        CandidatePersonalInformation candidatePersonalInformation = (CandidatePersonalInformation) application.getPersonalInformation().clone();

        return AdmissionTicketsResDto.builder()
                .applicantName(application.getApplicant().getName())
                .applicantBirth(application.getApplicant().getBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .applicantImageUri(application.getPersonalInformation().getApplicantImageUri())
                .screening(application.getSubjectEvaluationResult().getPreScreeningEvaluation())
                .schoolName(candidatePersonalInformation.getSchoolName())
                .registrationNumber(application.getRegistrationNumber())
                .build();
    }

    private AdmissionTicketsResDto buildGraduateAdmissionTicketsResDto (AbstractApplication application) {
        GraduatePersonalInformation graduatePersonalInformation = (GraduatePersonalInformation) application.getPersonalInformation().clone();

        return AdmissionTicketsResDto.builder()
                .applicantName(application.getApplicant().getName())
                .applicantBirth(application.getApplicant().getBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .applicantImageUri(application.getPersonalInformation().getApplicantImageUri())
                .screening(application.getSubjectEvaluationResult().getPreScreeningEvaluation())
                .schoolName(graduatePersonalInformation.getSchoolName())
                .registrationNumber(application.getRegistrationNumber())
                .build();
    }
}
