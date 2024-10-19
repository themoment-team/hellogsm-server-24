package team.themoment.hellogsmv3.domain.common.operation.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import team.themoment.hellogsmv3.domain.common.operation.entity.OperationTestResult;

public interface OperationTestResultRepository extends JpaRepository<OperationTestResult, Long> {
}
