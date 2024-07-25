package team.themoment.hellogsmv3.domain.oneseo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.oneseo.dto.response.AdmissionTicketsResDto;
import team.themoment.hellogsmv3.domain.oneseo.repository.custom.CustomOneseoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryAdmissionTicketsService {

    private final CustomOneseoRepository oneseoRepository;

    public List<AdmissionTicketsResDto> execute() {
        return oneseoRepository.findAdmissionTickets();
    }
}
