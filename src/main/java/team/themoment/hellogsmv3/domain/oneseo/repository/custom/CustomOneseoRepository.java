package team.themoment.hellogsmv3.domain.oneseo.repository.custom;

import team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import team.themoment.hellogsmv3.domain.application.type.ScreeningCategory;
import team.themoment.hellogsmv3.domain.oneseo.dto.request.TestResultTag;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo;
import team.themoment.hellogsmv3.domain.oneseo.dto.response.AdmissionTicketsResDto;

import java.util.List;

public interface CustomOneseoRepository {

    Integer findMaxSubmitCodeByScreening(Screening screening);

    Page<Oneseo> findAllByKeywordAndScreeningAndSubmissionStatusAndTestResult(
            String keyword,
            ScreeningCategory screening,
            YesNo isSubmitted,
            TestResultTag testResultTag,
            Pageable pageable
    );

    List<AdmissionTicketsResDto> findAdmissionTickets();
}
