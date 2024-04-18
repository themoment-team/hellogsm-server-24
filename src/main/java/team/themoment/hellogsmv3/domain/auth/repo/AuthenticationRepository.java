package team.themoment.hellogsmv3.domain.auth.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import team.themoment.hellogsmv3.domain.auth.entity.Authentication;

public interface AuthenticationRepository extends JpaRepository<Authentication, Long> {
}
