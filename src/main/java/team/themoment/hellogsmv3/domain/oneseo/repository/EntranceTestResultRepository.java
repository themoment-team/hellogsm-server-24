package team.themoment.hellogsmv3.domain.oneseo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.themoment.hellogsmv3.domain.oneseo.entity.EntranceTestResult;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;

import java.util.Optional;

public interface EntranceTestResultRepository extends JpaRepository<EntranceTestResult, Long> {

    Optional<EntranceTestResult> findEntranceTestResultByOneseo(Oneseo oneseo);
}
