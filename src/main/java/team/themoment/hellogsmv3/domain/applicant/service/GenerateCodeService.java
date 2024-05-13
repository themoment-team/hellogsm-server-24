package team.themoment.hellogsmv3.domain.applicant.service;

import team.themoment.hellogsmv3.domain.applicant.dto.request.GenerateCodeReqDto;

public abstract class GenerateCodeService {
    public final static int DIGIT_NUMBER = 6;
    public final static int LIMIT_COUNT_CODE_REQUEST = 5;
    public final static int MAX = (int) Math.pow(10, DIGIT_NUMBER) - 1;

    public abstract String execute(Long userId, GenerateCodeReqDto reqDto);
}
