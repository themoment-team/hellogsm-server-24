package team.themoment.hellogsmv3.domain.oneseo.repository.custom;

import team.themoment.hellogsmv3.domain.oneseo.dto.response.AdmissionTicketsResDto;

import java.util.List;

public interface CustomOneseoRepository {

    List<AdmissionTicketsResDto> findAdmissionTickets();
}
