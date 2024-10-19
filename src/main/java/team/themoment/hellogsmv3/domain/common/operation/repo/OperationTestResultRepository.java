package team.themoment.hellogsmv3.domain.common.operation.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team.themoment.hellogsmv3.domain.common.operation.entity.OperationTestResult;

public interface OperationTestResultRepository extends JpaRepository<OperationTestResult, Long> {
    @Query("SELECT o FROM OperationTestResult o LIMIT 1")
    OperationTestResult getTestResult();
}
