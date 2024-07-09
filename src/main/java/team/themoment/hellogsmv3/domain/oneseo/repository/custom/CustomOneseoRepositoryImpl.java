package team.themoment.hellogsmv3.domain.oneseo.repository.custom;

import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo;

import java.util.List;

import static team.themoment.hellogsmv3.domain.oneseo.entity.QOneseo.oneseo;
import static team.themoment.hellogsmv3.domain.member.entity.QMember.member;
import static team.themoment.hellogsmv3.domain.oneseo.entity.QEntranceTestResult.entranceTestResult;
import static team.themoment.hellogsmv3.domain.oneseo.entity.QOneseoPrivacyDetail.oneseoPrivacyDetail;




@Repository
@RequiredArgsConstructor
public class CustomOneseoRepositoryImpl implements CustomOneseoRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Oneseo> findAllByFinalSubmitted(Pageable pageable) {

        List<Oneseo> oneseos = queryFactory.selectFrom(oneseo)
                .leftJoin(oneseo.member, member).fetchJoin()
                .leftJoin(entranceTestResult).on(entranceTestResult.oneseo.id.eq(oneseo.id))
                .where(oneseo.finalSubmittedYn.eq(YesNo.YES))
                .orderBy(
                        new CaseBuilder()
                                .when(entranceTestResult.secondTestPassYn.eq(YesNo.YES)).then(5)
                                .when(entranceTestResult.secondTestPassYn.eq(YesNo.NO)).then(4)
                                .when(entranceTestResult.firstTestPassYn.eq(YesNo.YES)).then(3)
                                .when(entranceTestResult.firstTestPassYn.eq(YesNo.NO)).then(2)
                                .when(entranceTestResult.firstTestPassYn.isNull()).then(1)
                                .otherwise(0).desc()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(oneseo.count())
                .from(oneseo)
                .where(oneseo.finalSubmittedYn.eq(YesNo.YES))
                .fetchOne();

        return new PageImpl<>(oneseos, pageable, total);
    }

    @Override
    public Page<Oneseo> findAllByFinalSubmittedAndMemberNameContaining(String keyword, Pageable pageable) {

        List<Oneseo> oneseos = queryFactory.selectFrom(oneseo)
                .leftJoin(oneseo.member, member).fetchJoin()
                .leftJoin(entranceTestResult).on(entranceTestResult.oneseo.id.eq(oneseo.id))
                .where(
                        oneseo.finalSubmittedYn.eq(YesNo.YES)
                        .and(member.name.like("%" + keyword + "%"))
                )
                .orderBy(
                        new CaseBuilder()
                                .when(entranceTestResult.secondTestPassYn.eq(YesNo.YES)).then(5)
                                .when(entranceTestResult.secondTestPassYn.eq(YesNo.NO)).then(4)
                                .when(entranceTestResult.firstTestPassYn.eq(YesNo.YES)).then(3)
                                .when(entranceTestResult.firstTestPassYn.eq(YesNo.NO)).then(2)
                                .when(entranceTestResult.firstTestPassYn.isNull()).then(1)
                                .otherwise(0).desc()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(oneseo.count())
                .from(oneseo)
                .where(oneseo.finalSubmittedYn.eq(YesNo.YES))
                .fetchOne();

        return new PageImpl<>(oneseos, pageable, total);
    }

    @Override
    public Page<Oneseo> findAllByFinalSubmittedAndSchoolNameContaining(String keyword, Pageable pageable) {

        List<Oneseo> oneseos = queryFactory.selectFrom(oneseo)
                .leftJoin(oneseo.member, member).fetchJoin()
                .leftJoin(oneseoPrivacyDetail).on(oneseoPrivacyDetail.oneseo.eq(oneseo))
                .leftJoin(entranceTestResult).on(entranceTestResult.oneseo.id.eq(oneseo.id))
                .where(
                        oneseo.finalSubmittedYn.eq(YesNo.YES)
                                .and(oneseoPrivacyDetail.schoolName.like("%" + keyword + "%"))
                )
                .orderBy(
                        new CaseBuilder()
                                .when(entranceTestResult.secondTestPassYn.eq(YesNo.YES)).then(5)
                                .when(entranceTestResult.secondTestPassYn.eq(YesNo.NO)).then(4)
                                .when(entranceTestResult.firstTestPassYn.eq(YesNo.YES)).then(3)
                                .when(entranceTestResult.firstTestPassYn.eq(YesNo.NO)).then(2)
                                .when(entranceTestResult.firstTestPassYn.isNull()).then(1)
                                .otherwise(0).desc()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(oneseo.count())
                .from(oneseo)
                .where(oneseo.finalSubmittedYn.eq(YesNo.YES))
                .fetchOne();

        return new PageImpl<>(oneseos, pageable, total);
    }

    @Override
    public Page<Oneseo> findAllByFinalSubmittedAndPhoneNumberContaining(String keyword, Pageable pageable) {
        return null;
    }
}
