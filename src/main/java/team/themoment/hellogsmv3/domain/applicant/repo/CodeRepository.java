package team.themoment.hellogsmv3.domain.applicant.repo;

import org.springframework.data.repository.CrudRepository;
import team.themoment.hellogsmv3.domain.applicant.entity.AuthenticationCode;

import java.util.List;
import java.util.Optional;

public interface CodeRepository extends CrudRepository<AuthenticationCode, String> {
    Optional<AuthenticationCode> findByAuthenticationId(Long authenticationId);
    Optional<AuthenticationCode> findByCode(String code);
}
