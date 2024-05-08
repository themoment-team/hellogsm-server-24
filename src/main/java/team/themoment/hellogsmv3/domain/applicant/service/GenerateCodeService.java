package team.themoment.hellogsmv3.domain.applicant.service;

import team.themoment.hellogsmv3.domain.applicant.dto.request.GenerateCodeReqDto;

public interface GenerateCodeService {
    String execute(Long userId, GenerateCodeReqDto reqDto);
}
