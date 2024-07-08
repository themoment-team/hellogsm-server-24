package team.themoment.hellogsmv3.domain.oneseo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;

import java.util.Optional;

public interface OneseoRepository extends JpaRepository<Oneseo, Long> {

    Optional<Oneseo> findByMember(Member member);
}
