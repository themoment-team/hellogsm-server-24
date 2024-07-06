package team.themoment.hellogsmv3.domain.oneseo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.hellogsmv3.domain.member.entity.Member;
import team.themoment.hellogsmv3.domain.member.repo.MemberRepository;
import team.themoment.hellogsmv3.domain.oneseo.dto.request.OneseoReqDto;
import team.themoment.hellogsmv3.domain.oneseo.entity.MiddleSchoolAchievement;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;
import team.themoment.hellogsmv3.domain.oneseo.entity.OneseoPrivacyDetail;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.DesiredMajors;
import team.themoment.hellogsmv3.domain.oneseo.repository.MiddleSchoolAchievementRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoPrivacyDetailRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

import java.math.BigDecimal;

import static team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo.*;

@Service
@RequiredArgsConstructor
public class CreateOneseoService {

    private final MemberRepository memberRepository;
    private final OneseoRepository oneseoRepository;
    private final OneseoPrivacyDetailRepository oneseoPrivacyDetailRepository;
    private final MiddleSchoolAchievementRepository middleSchoolAchievementRepository;

    @Transactional
    public void execute(OneseoReqDto reqDto, Long memberId) {
        Member currentMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new ExpectedException("존재하지 않는 지원자입니다. member ID: " + memberId, HttpStatus.NOT_FOUND));

        isExistOneseo(currentMember);

        Oneseo oneseo = buildOneseo(reqDto, currentMember);
        OneseoPrivacyDetail oneseoPrivacyDetail = buildOneseoPrivacyDetail(reqDto, oneseo);
        MiddleSchoolAchievement middleSchoolAchievement = buildMiddleSchoolAchievement(reqDto, oneseo);

        saveEntities(oneseo, oneseoPrivacyDetail, middleSchoolAchievement);
    }

    private void saveEntities(Oneseo oneseo, OneseoPrivacyDetail oneseoPrivacyDetail, MiddleSchoolAchievement middleSchoolAchievement) {
        oneseoRepository.save(oneseo);
        oneseoPrivacyDetailRepository.save(oneseoPrivacyDetail);
        middleSchoolAchievementRepository.save(middleSchoolAchievement);
    }

    private Oneseo buildOneseo(OneseoReqDto reqDto, Member currentMember) {
        return Oneseo.builder()
                .member(currentMember)
                .desiredMajors(DesiredMajors.builder()
                        .firstDesiredMajor(reqDto.firstDesiredMajor())
                        .secondDesiredMajor(reqDto.secondDesiredMajor())
                        .thirdDesiredMajor(reqDto.thirdDesiredMajor())
                        .build())
                .realOneseoArrivedYn(NO)
                .finalSubmittedYn(NO)
                .appliedScreening(reqDto.screening()).build();
    }

    private OneseoPrivacyDetail buildOneseoPrivacyDetail(OneseoReqDto reqDto, Oneseo oneseo) {
        return OneseoPrivacyDetail.builder()
                .oneseo(oneseo)
                .graduationType(reqDto.graduationType())
                .address(reqDto.address())
                .detailAddress(reqDto.detailAddress())
                .profileImg(reqDto.profileImg())
                .guardianName(reqDto.guardianName())
                .guardianPhoneNumber(reqDto.guardianPhoneNumber())
                .relationshipWithGuardian(reqDto.relationshipWithGuardian())
                .schoolAddress(reqDto.schoolAddress())
                .schoolName(reqDto.schoolName())
                .schoolTeacherName(reqDto.schoolTeacherName())
                .schoolTeacherPhoneNumber(reqDto.schoolTeacherPhoneNumber()).build();
    }

    private MiddleSchoolAchievement buildMiddleSchoolAchievement(OneseoReqDto reqDto, Oneseo oneseo) {

        // TODO 추후에 성적 환산 로직 추가

        return MiddleSchoolAchievement.builder()
                .oneseo(oneseo)
                .transcript(reqDto.transcript())
                .percentileRank(BigDecimal.ONE)
                .totalScore(BigDecimal.ONE)
                .artisticScore(BigDecimal.ONE)
                .attendanceScore(BigDecimal.ONE)
                .volunteerScore(BigDecimal.ONE)
                .curricularSubtotalScore(BigDecimal.ONE)
                .extraCurricularSubtotalScore(BigDecimal.ONE)
                .gedMaxScore(BigDecimal.ONE)
                .gedTotalScore(BigDecimal.ONE)
                .grade1Semester1Score(BigDecimal.ONE)
                .grade1Semester2Score(BigDecimal.ONE)
                .grade2Semester1Score(BigDecimal.ONE)
                .grade2Semester2Score(BigDecimal.ONE)
                .grade3Semester1Score(BigDecimal.ONE).build();
    }

    private void isExistOneseo(Member currentMember) {
        if (oneseoRepository.existsByMember(currentMember)) {
            throw new ExpectedException("이미 원서가 존재합니다.", HttpStatus.BAD_REQUEST);
        }
    }

}
