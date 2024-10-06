package team.themoment.hellogsmv3.domain.oneseo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.oneseo.dto.request.OneseoReqDto;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.ScreeningCategory;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

import java.util.List;

import static team.themoment.hellogsmv3.domain.oneseo.entity.type.GraduationType.CANDIDATE;

@Service
@RequiredArgsConstructor
public class OneseoService {

    public static final String ONESEO_CACHE_VALUE = "oneseo";
    private final OneseoRepository oneseoRepository;

    public void assignSubmitCode(Oneseo oneseo) {
        Integer maxSubmitCodeNumber = oneseoRepository.findMaxSubmitCodeByScreening(oneseo.getWantedScreening());
        int newSubmitCodeNumber = (maxSubmitCodeNumber != null ? maxSubmitCodeNumber : 0) + 1;

        String submitCode;
        ScreeningCategory screeningCategory = oneseo.getWantedScreening().getScreeningCategory();
        switch (screeningCategory) {
            case GENERAL -> submitCode = "A-" + newSubmitCodeNumber;
            case SPECIAL -> submitCode = "B-" + newSubmitCodeNumber;
            case EXTRA -> submitCode = "C-" + newSubmitCodeNumber;
            default -> throw new IllegalArgumentException("Unexpected value: " + screeningCategory);
        }

        oneseo.setOneseoSubmitCode(submitCode);
    }

    public static Integer calcAbsentDaysCount(List<Integer> absentDays, List<Integer> attendanceDays) {
        if (absentDays == null || attendanceDays == null) {
            return null;
        }

        int totalAbsentDays = absentDays.stream().mapToInt(Integer::intValue).sum();
        int totalAttendanceDays = attendanceDays.stream().mapToInt(Integer::intValue).sum();

        return totalAbsentDays + (totalAttendanceDays / 3);
    }

    public static void isValidMiddleSchoolInfo(OneseoReqDto reqDto) {
        if (
                reqDto.graduationType().equals(CANDIDATE) && (
                        isBlankString(reqDto.schoolTeacherName()) ||
                        isBlankString(reqDto.schoolTeacherPhoneNumber()) ||
                        isBlankString(reqDto.schoolName()) ||
                        isBlankString(reqDto.schoolAddress())
                )
        ) {
            throw new ExpectedException("중학교 졸업예정인 지원자는 현재 재학 중인 중학교 정보를 필수로 입력해야 합니다.", HttpStatus.BAD_REQUEST);
        }
    }

    private static boolean isBlankString(String target) {
        return target == null || target.isBlank();
    }

    public Oneseo findByMemberOrThrow(Member member) {
        return oneseoRepository.findByMember(member)
                .orElseThrow(() -> new ExpectedException("해당 지원자의 원서를 찾을 수 없습니다. member ID: " + member.getId(), HttpStatus.NOT_FOUND));
    }
}
