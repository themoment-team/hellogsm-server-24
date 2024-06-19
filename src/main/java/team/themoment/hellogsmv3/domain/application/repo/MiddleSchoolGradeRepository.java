package team.themoment.hellogsmv3.domain.application.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import team.themoment.hellogsmv3.domain.application.entity.abs.AbstractMiddleSchoolGrade;

import java.util.UUID;

public interface MiddleSchoolGradeRepository extends JpaRepository<AbstractMiddleSchoolGrade, UUID> {
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM AbstractMiddleSchoolGrade WHERE id = :uuid")
    void deleteQueryMiddleSchoolGrade(UUID uuid);
}
