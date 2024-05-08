package team.themoment.hellogsmv3.domain.applicant.service;

import team.themoment.hellogsmv3.domain.applicant.dto.request.AuthenticateCodeReqDto;

public interface AuthenticateCodeService {
    void execute(Long userId, AuthenticateCodeReqDto reqDto);
}
