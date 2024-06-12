package team.themoment.hellogsmv3.domain.application.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import team.themoment.hellogsmv3.domain.application.entity.abs.AbstractMiddleSchoolGrade;

import java.util.UUID;

public interface MiddleSchoolGradeRepository extends JpaRepository<AbstractMiddleSchoolGrade, UUID> {
}
