package team.themoment.hellogsmv3.domain.applicant.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import team.themoment.hellogsmv3.domain.applicant.entity.Applicant;

import java.util.Optional;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {

    Boolean existsByAuthenticationId(Long authenticationId);

    Optional<Applicant> findByAuthenticationId(Long authenticationId);
}
