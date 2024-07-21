package team.themoment.hellogsmv3.domain.oneseo.repository.custom;

import team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening;

public interface CustomOneseoRepository {

    Integer findMaxSubmitCodeByScreening(Screening screening);
}
