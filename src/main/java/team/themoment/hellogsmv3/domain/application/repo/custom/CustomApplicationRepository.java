package team.themoment.hellogsmv3.domain.application.repo.custom;

import team.themoment.hellogsmv3.domain.application.entity.abs.AbstractApplication;

import java.util.Optional;

public interface CustomApplicationRepository {

    Optional<AbstractApplication> findByAuthenticationIdWithAllJoins(Long authenticationId);
}
