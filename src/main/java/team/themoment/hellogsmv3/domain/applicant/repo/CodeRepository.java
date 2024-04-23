package team.themoment.hellogsmv3.domain.applicant.repo;

import org.springframework.data.repository.CrudRepository;
import team.themoment.hellogsmv3.domain.applicant.entity.AuthenticationCode;

import java.util.List;

public interface CodeRepository extends CrudRepository<AuthenticationCode, String> {

    List<AuthenticationCode> findByAuthenticationId(Long authenticationId);
}
