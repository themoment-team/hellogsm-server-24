package team.themoment.hellogsmv3.domain.member.repo;

import org.springframework.data.repository.CrudRepository;
import team.themoment.hellogsmv3.domain.member.entity.AuthenticationCode;
import team.themoment.hellogsmv3.domain.member.entity.type.AuthCodeType;

import java.util.Optional;

public interface CodeRepository extends CrudRepository<AuthenticationCode, String> {
    Optional<AuthenticationCode> findByMemberId(Long memberId);
    Optional<AuthenticationCode> findByPhoneNumberAndAuthCodeType(String phoneNumber, AuthCodeType authCodeType);
    Optional<AuthenticationCode> findByCode(String code);
}
