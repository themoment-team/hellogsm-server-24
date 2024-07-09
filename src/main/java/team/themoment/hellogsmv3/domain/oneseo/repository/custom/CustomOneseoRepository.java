package team.themoment.hellogsmv3.domain.oneseo.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;

public interface CustomOneseoRepository {

    Page<Oneseo> findAllByFinalSubmitted(Pageable pageable);

    Page<Oneseo> findAllByFinalSubmittedAndMemberNameContaining(String keyword, Pageable pageable);

    Page<Oneseo> findAllByFinalSubmittedAndSchoolNameContaining(String keyword, Pageable pageable);

    Page<Oneseo> findAllByFinalSubmittedAndPhoneNumberContaining(String keyword, Pageable pageable);
}
