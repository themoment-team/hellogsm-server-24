package team.themoment.hellogsmv3.domain.member.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.member.entity.type.AuthReferrerType;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByAuthReferrerTypeAndEmail(AuthReferrerType authRefType, String email);
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<Member> findByPhoneNumber(String phoneNumber);

    @Query("DELETE FROM Member m WHERE m = :member")
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    void deleteDuplicate(Member member);
}
