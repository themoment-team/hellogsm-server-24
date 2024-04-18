package team.themoment.hellogsmv3.domain.auth.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import team.themoment.hellogsmv3.domain.auth.entity.Authentication;

import java.util.Optional;

public interface AuthenticationRepository extends JpaRepository<Authentication, Long> {
    Optional<Authentication> findByProviderNameAndProviderId(String providerName, String providerId);
}
