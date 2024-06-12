package team.themoment.hellogsmv3.domain.application.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsmv3.domain.applicant.entity.Applicant;
import team.themoment.hellogsmv3.domain.applicant.repo.ApplicantRepository;
import team.themoment.hellogsmv3.domain.application.dto.request.ApplicationReqDto;
import team.themoment.hellogsmv3.domain.application.entity.abs.AbstractApplication;
import team.themoment.hellogsmv3.domain.application.repo.ApplicationRepository;
import team.themoment.hellogsmv3.domain.application.repo.MiddleSchoolGradeRepository;
import team.themoment.hellogsmv3.domain.application.repo.PersonalInformationRepository;
import team.themoment.hellogsmv3.domain.auth.repo.AuthenticationRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

@Service
@RequiredArgsConstructor
public class ModifyApplicationService {

    private final ApplicantRepository applicantRepository;
    private final AuthenticationRepository authenticationRepository;
    private final ApplicationRepository applicationRepository;
    private final DeleteOldApplicationService deleteOldApplicationService;
    private final SaveUpdatedApplicationService saveUpdatedApplicationService;
    private final RollBackCancelDeleteApplication rollBackCancelDeleteApplication;
    private final EntityManager em;
    private final MiddleSchoolGradeRepository middleSchoolGradeRepository;
    private final PersonalInformationRepository personalInformationRepository;

    @Transactional
    public void execute(ApplicationReqDto dto, Long userId, boolean isAdmin) {

        Applicant applicant = applicantRepository.findById(userId)
                .orElseThrow(() -> new ExpectedException("존재하지 않는 유저입니다.", HttpStatus.NOT_FOUND));

        AbstractApplication application = applicationRepository.findByApplicant(applicant)
                .orElseThrow(() -> new ExpectedException("원서를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        if (!isAdmin && application.getFinalSubmitted())
            throw new ExpectedException("최종제출이 완료된 원서는 수정할 수 없습니다.", HttpStatus.BAD_REQUEST);

        if (!authenticationRepository.existsById(applicant.getAuthenticationId()))
            throw new ExpectedException("인증 정보가 존재하지 않습니다.", HttpStatus.NOT_FOUND);

        deleteOldApplicationService.execute(application.getId());

        try {
            saveUpdatedApplicationService.execute(dto, application, applicant);
        } catch (Exception e) {
            // ROLLBACK
            rollBackCancelDeleteApplication.execute(application);
        }
    }

}
