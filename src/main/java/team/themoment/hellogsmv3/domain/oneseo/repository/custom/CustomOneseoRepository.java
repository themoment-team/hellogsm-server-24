package team.themoment.hellogsmv3.domain.oneseo.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import team.themoment.hellogsmv3.domain.application.type.ScreeningCategory;
import team.themoment.hellogsmv3.domain.oneseo.dto.request.ApplicantStatusTag;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo;

public interface CustomOneseoRepository {

    Page<Oneseo> findAllByKeywordAndScreeningAndSubmissionStatusAndTestResult(
            String keyword,
            ScreeningCategory screening,
            YesNo isSubmitted,
            ApplicantStatusTag applicantStatusTag,
            Pageable pageable
    );
}
