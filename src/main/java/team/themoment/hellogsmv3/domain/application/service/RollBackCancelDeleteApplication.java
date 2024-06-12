package team.themoment.hellogsmv3.domain.application.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsmv3.domain.application.entity.GedApplication;
import team.themoment.hellogsmv3.domain.application.entity.GedMiddleSchoolGrade;
import team.themoment.hellogsmv3.domain.application.entity.GedPersonalInformation;
import team.themoment.hellogsmv3.domain.application.entity.abs.AbstractApplication;
import team.themoment.hellogsmv3.domain.application.entity.abs.AbstractMiddleSchoolGrade;
import team.themoment.hellogsmv3.domain.application.entity.abs.AbstractPersonalInformation;
import team.themoment.hellogsmv3.domain.application.entity.param.AbstractApplicationStatusParameter;
import team.themoment.hellogsmv3.domain.application.repo.ApplicationRepository;
import team.themoment.hellogsmv3.domain.application.repo.MiddleSchoolGradeRepository;
import team.themoment.hellogsmv3.domain.application.repo.PersonalInformationRepository;

@Service
@RequiredArgsConstructor
public class RollBackCancelDeleteApplication {

    private final ApplicationRepository applicationRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void execute(AbstractApplication application) {
        applicationRepository.save(application);
    }

}
