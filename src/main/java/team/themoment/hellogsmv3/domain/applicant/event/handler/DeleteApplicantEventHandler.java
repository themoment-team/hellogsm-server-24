package team.themoment.hellogsmv3.domain.applicant.event.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import team.themoment.hellogsmv3.domain.applicant.event.DeleteApplicantEvent;
import team.themoment.hellogsmv3.domain.application.service.CascadeDeleteApplicationService;

@Component
@RequiredArgsConstructor
public class DeleteApplicantEventHandler {

    private final CascadeDeleteApplicationService cascadeDeleteApplicationService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void deleteAuthenticationEventHandler(DeleteApplicantEvent deleteApplicantEvent) {
        cascadeDeleteApplicationService.execute(deleteApplicantEvent);
    }
}
