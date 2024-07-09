package team.themoment.hellogsmv3.domain.oneseo.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;

public class CustomOneseoRepositoryImpl implements CustomOneseoRepository{

    @Override
    public Page<Oneseo> findAllByFinalSubmitted(Pageable pageable) {
        return null;
    }

    @Override
    public Page<Oneseo> findAllByFinalSubmittedAndMemberNameContaining(String keyword, Pageable pageable) {
        return null;
    }

    @Override
    public Page<Oneseo> findAllByFinalSubmittedAndSchoolNameContaining(String keyword, Pageable pageable) {
        return null;
    }

    @Override
    public Page<Oneseo> findAllByFinalSubmittedAndPhoneNumberContaining(String keyword, Pageable pageable) {
        return null;
    }
}
