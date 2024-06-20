package team.themoment.hellogsmv3.domain.application.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import team.themoment.hellogsmv3.domain.applicant.entity.Applicant;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import team.themoment.hellogsmv3.domain.application.entity.abs.AbstractApplication;
import team.themoment.hellogsmv3.domain.application.repo.custom.CustomApplicationRepository;

import java.util.Optional;
import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<AbstractApplication, UUID>, CustomApplicationRepository {

    Optional<AbstractApplication> findAbstractApplicationByApplicant(Applicant applicant);

    boolean existsByApplicant(Applicant applicant);

    Optional<AbstractApplication> findByApplicant(Applicant applicant);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM AbstractApplication WHERE id = :uuid")
    void deleteQueryApplicationById(UUID uuid);
}
