package team.themoment.hellogsmv3.domain.member.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.member.entity.type.AuthReferrerType;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByAuthReferrerTypeAndEmail(AuthReferrerType authRefType, String email);
}
