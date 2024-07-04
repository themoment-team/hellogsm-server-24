package team.themoment.hellogsmv3.domain.applicant.repo;

import org.springframework.data.repository.CrudRepository;
import team.themoment.hellogsmv3.domain.applicant.entity.ApplicantAuthenticationCode;

import java.util.Optional;

public interface ApplicantCodeRepository extends CrudRepository<ApplicantAuthenticationCode, String> {
    Optional<ApplicantAuthenticationCode> findByAuthenticationId(Long authenticationId);
    Optional<ApplicantAuthenticationCode> findByCode(String code);
}
