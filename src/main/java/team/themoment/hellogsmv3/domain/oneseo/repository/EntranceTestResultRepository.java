package team.themoment.hellogsmv3.domain.oneseo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.themoment.hellogsmv3.domain.oneseo.entity.EntranceTestResult;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo;

import java.util.List;
import java.util.Optional;

public interface EntranceTestResultRepository extends JpaRepository<EntranceTestResult, Long> {

    Optional<EntranceTestResult> findEntranceTestResultByOneseo(Oneseo oneseo);
    EntranceTestResult findByOneseo(Oneseo oneseo);
    Boolean existsByFirstTestPassYn(YesNo firstTestPassYn);
    Boolean existsBySecondTestPassYn(YesNo secondTestPassYn);
    List<EntranceTestResult> findAllByFirstTestPassYnOrSecondTestPassYnAndOneseoFinalSubmittedYn(YesNo firstTestPassYn, YesNo secondTestPassYn, YesNo finalSubmittedYn);
}
