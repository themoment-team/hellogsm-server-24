package team.themoment.hellogsmv3.domain.application.service;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.applicant.entity.Applicant;
import team.themoment.hellogsmv3.domain.application.dto.response.ApplicationListInfoDto;
import team.themoment.hellogsmv3.domain.application.dto.response.SearchApplicationResDto;
import team.themoment.hellogsmv3.domain.application.dto.response.SearchApplicationsResDto;
import team.themoment.hellogsmv3.domain.application.entity.CandidatePersonalInformation;
import team.themoment.hellogsmv3.domain.application.entity.abs.AbstractApplication;
import team.themoment.hellogsmv3.domain.application.entity.abs.AbstractPersonalInformation;
import team.themoment.hellogsmv3.domain.application.repo.ApplicationRepository;
import team.themoment.hellogsmv3.domain.application.type.GraduationStatus;
import team.themoment.hellogsmv3.domain.application.type.Screening;
import team.themoment.hellogsmv3.domain.application.type.SearchTag;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class SearchApplicationService {

    private final ApplicationRepository applicationRepository;

    public SearchApplicationsResDto execute(Integer page, Integer size, @Nullable SearchTag tag, @Nullable String keyword) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AbstractApplication> applicationPage = applicationPage(tag, keyword, pageable);
        ApplicationListInfoDto paginationInfo = new ApplicationListInfoDto(applicationPage.getTotalPages(), applicationPage.getTotalElements());
        List<AbstractApplication> applicationList = applicationPage.getContent();
        List<SearchApplicationResDto> searchApplicationResDtoList = new ArrayList<SearchApplicationResDto>(applicationList.size());

        for ( AbstractApplication application : applicationList ) {
            Applicant applicant = application.getApplicant();

            AbstractPersonalInformation personalInformation = application.getPersonalInformation();
            CandidatePersonalInformation generalPersonalInformation = null;
            boolean isGed = application.getPersonalInformation().getGraduation() == GraduationStatus.GED;
            if (!isGed) {
                generalPersonalInformation = (CandidatePersonalInformation) Hibernate.unproxy(personalInformation);
            }

            Screening screening;
            if (!application.getSubjectEvaluationResult().isPass()) {
                screening = application.getSubjectEvaluationResult().getPreScreeningEvaluation();
            } else if (!application.getCompetencyEvaluationResult().isPass()) {
                screening = application.getCompetencyEvaluationResult().getPreScreeningEvaluation();
            } else {
                screening = application.getCompetencyEvaluationResult().getPostScreeningEvaluation();
            }

            searchApplicationResDtoList.add(SearchApplicationResDto.builder()
                            .applicantId(application.getApplicant().getId())
                            .isFinalSubmitted(application.getFinalSubmitted())
                            .isPrintsArrived(application.getPrintsArrived())
                            .applicantName(applicant.getName())
                            .screening(screening)
                            .schoolName(isGed ? null : generalPersonalInformation.getSchoolName())
                            .applicantPhoneNumber(personalInformation.getPhoneNumber())
                            .guardianPhoneNumber(personalInformation.getGuardianPhoneNumber())
                            .teacherPhoneNumber(isGed ? null : generalPersonalInformation.getTeacherPhoneNumber())
                            .firstEvaluation(application.getSubjectEvaluationResult().getEvaluationStatus())
                            .secondEvaluation(application.getCompetencyEvaluationResult().getEvaluationStatus())
                            .registrationNumber(application.getRegistrationNumber())
                            .secondScore(application.getCompetencyExamScore())
                            .build());
        }



         return new SearchApplicationsResDto(
                paginationInfo,
                searchApplicationResDtoList
        );
    }

    private Page<AbstractApplication> applicationPage(SearchTag tag, String keyword, Pageable pageable) {
        if (tag == null) {
            return applicationRepository.findAllByFinalSubmitted(pageable);
        }
        return switch (tag) {
            case APPLICANT -> applicationRepository.findAllByFinalSubmittedAndApplicantNameContaining(keyword, pageable);
            case SCHOOL -> applicationRepository.findAllByFinalSubmittedAndSchoolNameContaining(keyword, pageable);
            case PHONE_NUMBER -> applicationRepository.findAllByFinalSubmittedAndPhoneNumberContaining(keyword, pageable);
        };
    }
}
