package team.themoment.hellogsmv3.domain.common.testResult.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.common.testResult.dto.request.FoundTestResultReqDto;
import team.themoment.hellogsmv3.domain.common.testResult.dto.response.FoundTestResultResDto;
import team.themoment.hellogsmv3.domain.common.testResult.type.TestType;
import team.themoment.hellogsmv3.domain.member.service.CommonCodeService;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

import static team.themoment.hellogsmv3.domain.common.testResult.type.TestType.*;
import static team.themoment.hellogsmv3.domain.member.entity.type.AuthCodeType.TEST_RESULT;

@Service
@RequiredArgsConstructor
public class QueryTestResultService {

    private final OneseoRepository oneseoRepository;
    private final CommonCodeService commonCodeService;

    protected FoundTestResultResDto execute(FoundTestResultReqDto reqDto, TestType testType) {
        commonCodeService.validateAndDelete(reqDto.code(), reqDto.phoneNumber(), TEST_RESULT);

        validateSubmitCodeIsNotNull(reqDto.submitCode());
        Oneseo findOneseo = findOneseo(reqDto);

        return getResDto(findOneseo, testType);
    }

    private FoundTestResultResDto getResDto(Oneseo findOneseo, TestType testType) {
        return testType.equals(FIRST)
                ? FoundTestResultResDto.builder()
                    .name(findOneseo.getMember().getName())
                    .firstTestPassYn(findOneseo.getEntranceTestResult().getFirstTestPassYn())
                    .build()
                : FoundTestResultResDto.builder()
                    .name(findOneseo.getMember().getName())
                    .secondTestPassYn(findOneseo.getEntranceTestResult().getSecondTestPassYn())
                    .decidedMajor(findOneseo.getDecidedMajor())
                    .build();
    }

    private void validateSubmitCodeIsNotNull(String submitCode) {
        if (submitCode == null) {
            throw new ExpectedException("접수번호를 입력해주세요.", HttpStatus.BAD_REQUEST);
        }
    }

    private Oneseo findOneseo(FoundTestResultReqDto reqDto) {
        return oneseoRepository.findByGuardianOrTeacherPhoneNumberAndSubmitCode(reqDto.phoneNumber(), reqDto.submitCode())
                .orElseThrow(() -> new ExpectedException("해당 전화번호, 접수번호로 작성된 원서를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST));
    }
}
