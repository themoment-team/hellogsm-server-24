package team.themoment.hellogsmv3.domain.application.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import team.themoment.hellogsmv3.domain.application.entity.abs.AbstractPersonalInformation;

import java.util.UUID;

public interface PersonalInformationRepository extends JpaRepository<AbstractPersonalInformation, UUID> {
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM AbstractPersonalInformation WHERE id = :uuid")
    void deleteQueryPersonalInformation(UUID uuid);
}
