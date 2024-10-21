package team.themoment.hellogsmv3.domain.oneseo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;
import team.themoment.hellogsmv3.domain.oneseo.repository.custom.CustomOneseoRepository;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening;

import java.util.List;
import java.util.Optional;

public interface OneseoRepository extends JpaRepository<Oneseo, Long>, CustomOneseoRepository {
    boolean existsByMember(Member member);
    Optional<Oneseo> findByMember(Member member);
}
