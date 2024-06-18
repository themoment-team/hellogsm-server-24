package team.themoment.hellogsmv3.domain.application.repo.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import team.themoment.hellogsmv3.domain.application.entity.abs.AbstractApplication;

import java.util.Optional;

public interface CustomApplicationRepository {

    Optional<AbstractApplication> findByAuthenticationIdWithAllJoins(Long authenticationId);

    Page<AbstractApplication> findAllByFinalSubmitted(Pageable pageable);

    Page<AbstractApplication> findAllByFinalSubmittedAndApplicantNameContaining(String keyword, Pageable pageable);

    Page<AbstractApplication> findAllByFinalSubmittedAndSchoolNameContaining(String keyword, Pageable pageable);

    Page<AbstractApplication> findAllByFinalSubmittedAndPhoneNumberContaining(String keyword, Pageable pageable);

}
