package team.themoment.hellogsmv3.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.applicant.entity.Applicant;
import team.themoment.hellogsmv3.domain.application.dto.response.ApplicationDto;
import team.themoment.hellogsmv3.domain.application.dto.response.ApplicationListInfoDto;
import team.themoment.hellogsmv3.domain.application.dto.response.ApplicationListResDto;
import team.themoment.hellogsmv3.domain.application.entity.CandidatePersonalInformation;
import team.themoment.hellogsmv3.domain.application.entity.abs.AbstractApplication;
import team.themoment.hellogsmv3.domain.application.entity.abs.AbstractPersonalInformation;
import team.themoment.hellogsmv3.domain.application.repo.ApplicationRepository;
import team.themoment.hellogsmv3.domain.application.type.GraduationStatus;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryAllApplicationService {

    private final ApplicationRepository applicationRepository;

    public ApplicationListResDto execute(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AbstractApplication> applicationPage = applicationRepository.findAll(pageable);
        ApplicationListInfoDto paginationInfo = new ApplicationListInfoDto(applicationPage.getTotalPages(), applicationPage.getTotalElements());
        List<AbstractApplication> applicationList = applicationPage.toList();
        List<ApplicationDto> applicationDtoList = new ArrayList<ApplicationDto>(applicationList.size());

        for ( AbstractApplication application : applicationList ) {
            Applicant applicant = application.getApplicant();

            AbstractPersonalInformation personalInformation = application.getPersonalInformation();
            CandidatePersonalInformation generalPersonalInformation = null;
            boolean isGed = application.getPersonalInformation().getGraduation() == GraduationStatus.GED;
            if (!isGed) {
                generalPersonalInformation = (CandidatePersonalInformation) Hibernate.unproxy(personalInformation);
            }

            applicationDtoList.add(ApplicationDto.builder()
                            .applicantId(application.getApplicant().getId())
                            .applicantName(applicant.getName())
                            .graduation(personalInformation.getGraduation())
                            .applicantPhoneNumber(personalInformation.getPhoneNumber())
                            .guardianPhoneNumber(personalInformation.getGuardianPhoneNumber())
                            .teacherName(isGed ? null : generalPersonalInformation.getTeacherName())
                            .teacherPhoneNumber(isGed ? null : generalPersonalInformation.getTeacherPhoneNumber())
                            .isFinalSubmitted(application.getFinalSubmitted())
                            .isPrintsArrived(application.getPrintsArrived())
                            .firstEvaluation(application.getSubjectEvaluationResult().getEvaluationStatus())
                            .secondEvaluation(application.getCompetencyEvaluationResult().getEvaluationStatus())
                            .screeningFirstEvaluationAt(application.getSubjectEvaluationResult() != null ? application.getSubjectEvaluationResult().getPostScreeningEvaluation() : null)
                            .screeningSecondEvaluationAt(application.getCompetencyEvaluationResult() != null ? application.getCompetencyEvaluationResult().getPostScreeningEvaluation() : null)
                            .registrationNumber(application.getRegistrationNumber())
                            .secondScore(application.getCompetencyEvaluationResult().getScore())
                            .interviewScore(application.getInterviewScore())
                            .build());
        }

        return new ApplicationListResDto(
                paginationInfo,
                applicationDtoList
        );
    }
}
