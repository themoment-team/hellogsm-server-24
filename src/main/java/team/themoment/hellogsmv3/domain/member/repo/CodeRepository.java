package team.themoment.hellogsmv3.domain.member.repo;

import org.springframework.data.repository.CrudRepository;
import team.themoment.hellogsmv3.domain.member.entity.AuthenticationCode;

import java.util.Optional;

public interface CodeRepository extends CrudRepository<AuthenticationCode, String> {
    Optional<AuthenticationCode> findByAuthenticationId(Long authenticationId);
    Optional<AuthenticationCode> findByCode(String code);
}
