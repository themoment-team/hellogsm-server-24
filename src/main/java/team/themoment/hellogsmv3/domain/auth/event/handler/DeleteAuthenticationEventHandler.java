package team.themoment.hellogsmv3.domain.auth.event.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import team.themoment.hellogsmv3.domain.applicant.service.CascadeDeleteApplicantService;
import team.themoment.hellogsmv3.domain.auth.event.DeleteAuthenticationEvent;

@Component
@RequiredArgsConstructor
public class DeleteAuthenticationEventHandler {
    private final CascadeDeleteApplicantService cascadeDeleteApplicantService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void deleteAuthenticationEventHandler(DeleteAuthenticationEvent deleteAuthenticationEvent) {
        cascadeDeleteApplicantService.execute(deleteAuthenticationEvent.authentication().getId());
    }
}
