package team.themoment.hellogsmv3.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsmv3.domain.application.repo.ApplicationRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteOldApplicationService {

    private final ApplicationRepository applicationRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void execute(UUID uuid) {
        System.out.println("=========== T 1 ===========");
        applicationRepository.deleteById(uuid);
    }

}
