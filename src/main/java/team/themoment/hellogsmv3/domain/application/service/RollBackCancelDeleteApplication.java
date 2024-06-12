package team.themoment.hellogsmv3.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsmv3.domain.application.entity.abs.AbstractApplication;
import team.themoment.hellogsmv3.domain.application.repo.ApplicationRepository;

@Service
@RequiredArgsConstructor
public class RollBackCancelDeleteApplication {

    private final ApplicationRepository applicationRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void execute(AbstractApplication application) {
        applicationRepository.save(application);
    }

}
