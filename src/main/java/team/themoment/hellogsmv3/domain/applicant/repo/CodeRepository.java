package team.themoment.hellogsmv3.domain.applicant.repo;

import org.springframework.data.repository.CrudRepository;
import team.themoment.hellogsmv3.domain.applicant.entity.AuthenticationCode;

public interface CodeRepository extends CrudRepository<AuthenticationCode, String> {
}
