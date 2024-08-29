package team.themoment.hellogsmv3.domain.oneseo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.member.service.MemberService;
import team.themoment.hellogsmv3.domain.oneseo.dto.request.MiddleSchoolAchievementReqDto;
import team.themoment.hellogsmv3.domain.oneseo.dto.request.OneseoTempReqDto;
import team.themoment.hellogsmv3.domain.oneseo.dto.response.DesiredMajorsResDto;
import team.themoment.hellogsmv3.domain.oneseo.dto.response.FoundOneseoResDto;
import team.themoment.hellogsmv3.domain.oneseo.dto.response.MiddleSchoolAchievementResDto;
import team.themoment.hellogsmv3.domain.oneseo.dto.response.OneseoPrivacyDetailResDto;

import static team.themoment.hellogsmv3.domain.oneseo.service.OneseoService.*;

@Service
@RequiredArgsConstructor
public class OneseoTempStorageService {

    private final MemberService memberService;

    @CachePut(value = ONESEO_CACHE_VALUE, key = "#memberId")
    public FoundOneseoResDto execute(OneseoTempReqDto reqDto, Integer step, Long memberId) {
        Member member = memberService.findByIdOrThrow(memberId);

        OneseoPrivacyDetailResDto oneseoPrivacyDetailResDto = buildOneseoPrivacyDetailResDto(member, reqDto);
        MiddleSchoolAchievementResDto middleSchoolAchievementResDto = buildMiddleSchoolAchievementResDto(reqDto);

        return buildFoundOneseoResDto(
                reqDto,
                oneseoPrivacyDetailResDto,
                middleSchoolAchievementResDto,
                step
        );
    }

    private OneseoPrivacyDetailResDto buildOneseoPrivacyDetailResDto(
            Member member,
            OneseoTempReqDto reqDto
    ) {

        return OneseoPrivacyDetailResDto.builder()
                .name(member.getName())
                .sex(member.getSex())
                .birth(member.getBirth())
                .phoneNumber(member.getPhoneNumber())
                .graduationType(reqDto.graduationType())
                .address(reqDto.address())
                .detailAddress(reqDto.detailAddress())
                .guardianName(reqDto.guardianName())
                .guardianPhoneNumber(reqDto.guardianPhoneNumber())
                .relationshipWithGuardian(reqDto.relationshipWithGuardian())
                .schoolName(reqDto.schoolName())
                .schoolAddress(reqDto.schoolAddress())
                .schoolTeacherName(reqDto.schoolTeacherName())
                .schoolTeacherPhoneNumber(reqDto.schoolTeacherPhoneNumber())
                .profileImg(reqDto.profileImg())
                .build();
    }

    private MiddleSchoolAchievementResDto buildMiddleSchoolAchievementResDto(
            OneseoTempReqDto reqDto
    ) {
        MiddleSchoolAchievementReqDto middleSchoolAchievement = reqDto.middleSchoolAchievement();

        return MiddleSchoolAchievementResDto.builder()
                .achievement1_2(middleSchoolAchievement.achievement1_2())
                .achievement2_1(middleSchoolAchievement.achievement2_1())
                .achievement2_2(middleSchoolAchievement.achievement2_2())
                .achievement3_1(middleSchoolAchievement.achievement3_1())
                .achievement3_2(middleSchoolAchievement.achievement3_2())
                .generalSubjects(middleSchoolAchievement.generalSubjects())
                .newSubjects(middleSchoolAchievement.newSubjects())
                .artsPhysicalAchievement(middleSchoolAchievement.artsPhysicalAchievement())
                .artsPhysicalSubjects(middleSchoolAchievement.artsPhysicalSubjects())
                .absentDays(middleSchoolAchievement.absentDays())
                .attendanceDays(middleSchoolAchievement.attendanceDays())
                .volunteerTime(middleSchoolAchievement.volunteerTime())
                .liberalSystem(middleSchoolAchievement.liberalSystem())
                .freeSemester(middleSchoolAchievement.freeSemester())
                .gedTotalScore(middleSchoolAchievement.gedTotalScore())
                .build();
    }

    private FoundOneseoResDto buildFoundOneseoResDto(
            OneseoTempReqDto reqDto,
            OneseoPrivacyDetailResDto oneseoPrivacyDetailResDto,
            MiddleSchoolAchievementResDto middleSchoolAchievementResDto,
            Integer step
    ) {

        return FoundOneseoResDto.builder()
                .oneseoId(null)
                .submitCode(null)
                .wantedScreening(reqDto.screening())
                .desiredMajors(DesiredMajorsResDto.builder()
                        .firstDesiredMajor(reqDto.firstDesiredMajor())
                        .secondDesiredMajor(reqDto.secondDesiredMajor())
                        .thirdDesiredMajor(reqDto.thirdDesiredMajor())
                        .build())
                .privacyDetail(oneseoPrivacyDetailResDto)
                .middleSchoolAchievement(middleSchoolAchievementResDto)
                .step(step)
                .build();
    }
}
