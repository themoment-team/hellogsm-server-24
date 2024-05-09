package team.themoment.hellogsmv3.domain.applicant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsmv3.domain.applicant.dto.request.ApplicantReqDto;
import team.themoment.hellogsmv3.domain.applicant.entity.Applicant;
import team.themoment.hellogsmv3.domain.applicant.entity.AuthenticationCode;
import team.themoment.hellogsmv3.domain.applicant.repo.ApplicantRepository;
import team.themoment.hellogsmv3.domain.applicant.repo.CodeRepository;
import team.themoment.hellogsmv3.domain.applicant.type.Gender;
import team.themoment.hellogsmv3.domain.auth.repo.AuthenticationRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ModifyApplicantService {

    private final ApplicantRepository applicantRepository;
    private final AuthenticationRepository authenticationRepository;
    private final CodeRepository codeRepository;

    public void execute(ApplicantReqDto reqDto, Long authenticationId) {
        if(!authenticationRepository.existsById(authenticationId))
            throw new ExpectedException("존재하지 않는 Authentication 입니다", HttpStatus.BAD_REQUEST);

        Applicant savedApplicant = applicantRepository.findByAuthenticationId(authenticationId)
                .orElseThrow(() -> new ExpectedException("존재하지 않는 Applicant 입니다", HttpStatus.BAD_REQUEST));

        List<AuthenticationCode> codes = codeRepository.findByAuthenticationId(authenticationId);
        AuthenticationCode recentCode = codes.stream()
                .max(Comparator.comparing(AuthenticationCode::getCreatedAt))
                .orElseThrow(() ->
                        new ExpectedException("사용자의 code가 존재하지 않습니다. 사용자의 ID : " + authenticationId, HttpStatus.BAD_REQUEST));

        if (!recentCode.getAuthenticated())
            throw new ExpectedException("유효하지 않은 요청입니다. 인증받지 않은 code입니다.", HttpStatus.BAD_REQUEST);

        if (!recentCode.getCode().equals(reqDto.code()))
            throw new ExpectedException("유효하지 않은 요청입니다. 이전 혹은 잘못된 형식의 code입니다.", HttpStatus.BAD_REQUEST);

        if (!recentCode.getPhoneNumber().equals(reqDto.phoneNumber()))
            throw new ExpectedException("유효하지 않은 요청입니다. code인증에 사용되었던 전화번호와 요청에 사용한 전화번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);

        Applicant newApplicant = new Applicant(
                savedApplicant.getId(),
                reqDto.name(),
                reqDto.phoneNumber(),
                reqDto.birth(),
                Gender.valueOf(reqDto.gender()),
                authenticationId
        );
        applicantRepository.save(newApplicant);

        codes.forEach(code -> codeRepository.deleteById(code.getCode()));
    }
}
