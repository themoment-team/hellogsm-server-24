package team.themoment.hellogsmv3.domain.oneseo.repository.custom.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.themoment.hellogsmv3.domain.oneseo.dto.response.AdmissionTicketsResDto;
import team.themoment.hellogsmv3.domain.oneseo.repository.custom.CustomOneseoRepository;

import static team.themoment.hellogsmv3.domain.member.entity.QMember.member;
import static team.themoment.hellogsmv3.domain.oneseo.entity.QOneseo.oneseo;
import static team.themoment.hellogsmv3.domain.oneseo.entity.QOneseoPrivacyDetail.oneseoPrivacyDetail;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomOneseoRepositoryImpl implements CustomOneseoRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<AdmissionTicketsResDto> findAdmissionTickets() {
        return queryFactory.select(
                        Projections.constructor(
                                AdmissionTicketsResDto.class,
                                oneseo.member.name,
                                oneseo.member.birth,
                                oneseoPrivacyDetail.profileImg,
                                oneseoPrivacyDetail.schoolName,
                                oneseo.appliedScreening,
                                oneseo.oneseoSubmitCode
                        )
                )
                .from(oneseo, oneseoPrivacyDetail)
                .join(oneseo.member, member)
                .fetch();
    }
}
