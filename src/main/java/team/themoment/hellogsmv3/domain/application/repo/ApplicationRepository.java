package team.themoment.hellogsmv3.domain.application.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import team.themoment.hellogsmv3.domain.applicant.entity.Applicant;
import team.themoment.hellogsmv3.domain.application.entity.abs.AbstractApplication;
import team.themoment.hellogsmv3.domain.application.repo.custom.CustomApplicationRepository;

import java.util.Optional;
import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<AbstractApplication, UUID>, CustomApplicationRepository {

    Optional<AbstractApplication> findAbstractApplicationByApplicant(Applicant applicant);
}
