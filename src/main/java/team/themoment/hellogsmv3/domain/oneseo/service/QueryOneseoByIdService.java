package team.themoment.hellogsmv3.domain.oneseo.service;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.member.repo.MemberRepository;
import team.themoment.hellogsmv3.domain.oneseo.dto.response.*;
import team.themoment.hellogsmv3.domain.oneseo.entity.*;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.DesiredMajors;
import team.themoment.hellogsmv3.domain.oneseo.repository.EntranceTestResultRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.MiddleSchoolAchievementRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoPrivacyDetailRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

@Service
@RequiredArgsConstructor
public class QueryOneseoByIdService {

    private final OneseoRepository oneseoRepository;
    private final MemberRepository memberRepository;
    private final OneseoPrivacyDetailRepository oneseoPrivacyDetailRepository;
    private final MiddleSchoolAchievementRepository middleSchoolAchievementRepository;
    private final EntranceTestResultRepository entranceTestResultRepository;

    public FoundOneseoResDto execute(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ExpectedException("존재하지 않는 지원자입니다. member ID: " + memberId, HttpStatus.NOT_FOUND));
        Oneseo oneseo = oneseoRepository.findByMember(member)
                .orElseThrow(() -> new ExpectedException("원서를 찾을 수 없습니다. member ID: " + memberId, HttpStatus.NOT_FOUND));
        EntranceTestResult entranceTestResult = entranceTestResultRepository.findByOneseo(oneseo).orElse(null);
        OneseoPrivacyDetail oneseoPrivacyDetail = oneseoPrivacyDetailRepository.findByOneseo(oneseo);
        MiddleSchoolAchievement middleSchoolAchievement = middleSchoolAchievementRepository.findByOneseo(oneseo);

        OneseoPrivacyDetailResDto oneseoPrivacyDetailResDto = buildOneseoPrivacyDetailResDto(member, oneseoPrivacyDetail);
        MiddleSchoolAchievementResDto middleSchoolAchievementResDto = buildMiddleSchoolAchievementResDto(middleSchoolAchievement);
        EntranceTestResultResDto entranceTestResultResDto = buildEntranceTestResultResDto(entranceTestResult);

        return buildFoundOneseoResDto(
                oneseo,
                oneseoPrivacyDetailResDto,
                middleSchoolAchievementResDto,
                entranceTestResultResDto
        );
    }

    private OneseoPrivacyDetailResDto buildOneseoPrivacyDetailResDto(
            Member member,
            OneseoPrivacyDetail oneseoPrivacyDetail
    ) {
        return OneseoPrivacyDetailResDto.builder()
                .name(member.getName())
                .sex(member.getSex())
                .birth(member.getBirth())
                .phoneNumber(member.getPhoneNumber())
                .graduationType(oneseoPrivacyDetail.getGraduationType())
                .address(oneseoPrivacyDetail.getAddress())
                .detailAddress(oneseoPrivacyDetail.getDetailAddress())
                .guardianName(oneseoPrivacyDetail.getGuardianName())
                .guardianPhoneNumber(oneseoPrivacyDetail.getGuardianPhoneNumber())
                .relationshipWithGuardian(oneseoPrivacyDetail.getRelationshipWithGuardian())
                .schoolName(oneseoPrivacyDetail.getSchoolName())
                .schoolAddress(oneseoPrivacyDetail.getSchoolAddress())
                .schoolTeacherName(oneseoPrivacyDetail.getSchoolTeacherName())
                .schoolTeacherPhoneNumber(oneseoPrivacyDetail.getSchoolTeacherPhoneNumber())
                .profileImg(oneseoPrivacyDetail.getProfileImg())
                .build();
    }

    private MiddleSchoolAchievementResDto buildMiddleSchoolAchievementResDto(
            MiddleSchoolAchievement middleSchoolAchievement
    ) {
        return MiddleSchoolAchievementResDto.builder()
                .transcript(buildTranscriptResDto(middleSchoolAchievement.getTranscript()))
                .totalScore(middleSchoolAchievement.getTotalScore())
                .percentileRank(middleSchoolAchievement.getPercentileRank())
                .grade1Semester1Score(middleSchoolAchievement.getGrade1Semester1Score())
                .grade1Semester2Score(middleSchoolAchievement.getGrade1Semester2Score())
                .grade2Semester1Score(middleSchoolAchievement.getGrade2Semester1Score())
                .grade2Semester2Score(middleSchoolAchievement.getGrade2Semester2Score())
                .grade3Semester1Score(middleSchoolAchievement.getGrade3Semester1Score())
                .curricularSubtotalScore(middleSchoolAchievement.getCurricularSubtotalScore())
                .extraCurricularSubtotalScore(middleSchoolAchievement.getExtraCurricularSubtotalScore())
                .artisticScore(middleSchoolAchievement.getArtisticScore())
                .attendanceScore(middleSchoolAchievement.getAttendanceScore())
                .volunteerScore(middleSchoolAchievement.getVolunteerScore())
                .build();
    }

    private TranscriptResDto buildTranscriptResDto(String transcript) {
        Gson gson = new Gson();
        TranscriptResDto transcriptResDto = gson.fromJson(transcript, TranscriptResDto.class);

        // TODO attendanceDay 계산 & 값 주입

        return transcriptResDto;
    }

    private EntranceTestResultResDto buildEntranceTestResultResDto(
            EntranceTestResult entranceTestResult
    ) {
        if (entranceTestResult == null) return null;

        EntranceTestFactorsDetail entranceTestFactorsDetail = entranceTestResult.getEntranceTestFactorsDetail();

        return EntranceTestResultResDto.builder()
                .documentEvaluationScore(entranceTestFactorsDetail.getDocumentEvaluationScore())
                .firstTestPassYn(entranceTestResult.getFirstTestPassYn())
                .aptitudeEvaluationScore(entranceTestFactorsDetail.getAptitudeEvaluationScore())
                .interviewScore(entranceTestFactorsDetail.getInterviewScore())
                .secondTestPassYn(entranceTestResult.getSecondTestPassYn())
                .decidedMajor(entranceTestResult.getDecidedMajor())
                .build();
    }

    private FoundOneseoResDto buildFoundOneseoResDto(
            Oneseo oneseo,
            OneseoPrivacyDetailResDto oneseoPrivacyDetailResDto,
            MiddleSchoolAchievementResDto middleSchoolAchievementResDto,
            EntranceTestResultResDto entranceTestResultResDto
    ) {
        DesiredMajors desiredMajors = oneseo.getDesiredMajors();

        return FoundOneseoResDto.builder()
                .oneseoId(oneseo.getId())
                .submitCode(oneseo.getOneseoSubmitCode())
                .wantedScreening(oneseo.getWantedScreening())
                .desiredMajors(DesiredMajorsResDto.builder()
                        .firstDesiredMajor(desiredMajors.getFirstDesiredMajor())
                        .secondDesiredMajor(desiredMajors.getSecondDesiredMajor())
                        .thirdDesiredMajor(desiredMajors.getThirdDesiredMajor())
                        .build())
                .finalSubmittedYn(oneseo.getFinalSubmittedYn())
                .realOneseoArrivedYn(oneseo.getRealOneseoArrivedYn())
                .privacyDetail(oneseoPrivacyDetailResDto)
                .middleSchoolAchievement(middleSchoolAchievementResDto)
                .entranceTestResult(entranceTestResultResDto)
                .build();
    }
}
