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

        List<AdmissionTicketsResDto> gedAdmissionTicketsResDtos = applications.stream()
                .filter(application -> application.getPersonalInformation().getGraduation() == GraduationStatus.GED)
                .map(application ->
                        AdmissionTicketsResDto.builder()
                                .applicantName(application.getApplicant().getName())
                                .applicantBirth(application.getApplicant().getBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                                .applicantImageUri(application.getPersonalInformation().getApplicantImageUri())
                                .schoolName(null)
                                .screening(application.getSubjectEvaluationResult().getPreScreeningEvaluation())
                                .registrationNumber(application.getRegistrationNumber())
                                .build()).toList();

        List<AdmissionTicketsResDto> candidateAdmissionTicketsResDtos = applications.stream()
                .filter(application -> application.getPersonalInformation().getGraduation() == GraduationStatus.CANDIDATE)
                .map(application -> {
                    CandidatePersonalInformation candidatePersonalInformation = (CandidatePersonalInformation) application.getPersonalInformation();

                    return AdmissionTicketsResDto.builder()
                            .applicantName(application.getApplicant().getName())
                            .applicantBirth(application.getApplicant().getBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                            .applicantImageUri(application.getPersonalInformation().getApplicantImageUri())
                            .schoolName(candidatePersonalInformation.getSchoolName())
                            .screening(application.getSubjectEvaluationResult().getPreScreeningEvaluation())
                            .registrationNumber(application.getRegistrationNumber())
                            .build();
                }).toList();

        List<AdmissionTicketsResDto> graduateAdmissionTicketsResDtos = applications.stream()
                .filter(application -> application.getPersonalInformation().getGraduation() == GraduationStatus.GRADUATE)
                .map(application -> {
                    GraduatePersonalInformation graduatePersonalInformation = (GraduatePersonalInformation) application.getPersonalInformation();

                    return AdmissionTicketsResDto.builder()
                            .applicantName(application.getApplicant().getName())
                            .applicantBirth(application.getApplicant().getBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                            .applicantImageUri(application.getPersonalInformation().getApplicantImageUri())
                            .schoolName(graduatePersonalInformation.getSchoolName())
                            .screening(application.getSubjectEvaluationResult().getPreScreeningEvaluation())
                            .registrationNumber(application.getRegistrationNumber())
                            .build();
                }).toList();

        List<AdmissionTicketsResDto> allAdmissionTicketsResDtos = new ArrayList<>();
        allAdmissionTicketsResDtos.addAll(gedAdmissionTicketsResDtos);
        allAdmissionTicketsResDtos.addAll(candidateAdmissionTicketsResDtos);
        allAdmissionTicketsResDtos.addAll(graduateAdmissionTicketsResDtos);

        return allAdmissionTicketsResDtos;
    }
}
