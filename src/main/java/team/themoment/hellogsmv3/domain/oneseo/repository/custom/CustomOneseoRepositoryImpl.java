package team.themoment.hellogsmv3.domain.oneseo.repository.custom;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import team.themoment.hellogsmv3.domain.application.type.ScreeningCategory;
import team.themoment.hellogsmv3.domain.oneseo.dto.request.TestResultTag;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo;

import java.util.List;

import static com.querydsl.core.types.ExpressionUtils.anyOf;
import static team.themoment.hellogsmv3.domain.oneseo.entity.QOneseo.oneseo;
import static team.themoment.hellogsmv3.domain.member.entity.QMember.member;
import static team.themoment.hellogsmv3.domain.oneseo.entity.QEntranceTestResult.entranceTestResult;
import static team.themoment.hellogsmv3.domain.oneseo.entity.QOneseoPrivacyDetail.oneseoPrivacyDetail;

@Repository
@RequiredArgsConstructor
public class CustomOneseoRepositoryImpl implements CustomOneseoRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Oneseo> findAllByKeywordAndScreeningAndSubmissionStatusAndTestResult(
            String keyword,
            ScreeningCategory screeningTag,
            YesNo isSubmitted,
            TestResultTag testResultTag,
            Pageable pageable
    ) {

        BooleanBuilder builder = createBooleanBuilder(
                keyword,
                screeningTag,
                isSubmitted,
                testResultTag
        );

        List<Oneseo> oneseos = queryFactory.selectFrom(oneseo)
                .leftJoin(oneseo.member, member).fetchJoin()
                .leftJoin(oneseoPrivacyDetail).on(oneseoPrivacyDetail.oneseo.eq(oneseo))
                .leftJoin(entranceTestResult).on(entranceTestResult.id.eq(oneseo.id))
                .where(builder)
                .orderBy(oneseo.oneseoSubmitCode.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(
                oneseos,
                pageable,
                oneseos.size()
        );
    }

    private BooleanBuilder createBooleanBuilder(
            String keyword,
            ScreeningCategory screeningTag,
            YesNo isSubmitted,
            TestResultTag testResultTag
    ) {

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(oneseo.finalSubmittedYn.eq(YesNo.YES))
                .and(
                        anyOf(
                                oneseo.member.name.like("%" + keyword + "%"),
                                oneseoPrivacyDetail.schoolName.like("%" + keyword + "%"),
                                oneseo.member.phoneNumber.like("%" + keyword + "%")
                        )
                );

        applyScreeningTag(builder, screeningTag);
        applyIsSubmittedTag(builder, isSubmitted);
        applyTestResultTag(builder, testResultTag);

        return builder;
    }

    private void applyScreeningTag(
            BooleanBuilder builder,
            ScreeningCategory screeningTag
    ) {
        if (screeningTag == null) return;

        switch (screeningTag) {
            case GENERAL ->
                    builder.and(
                            oneseo.appliedScreening.eq(Screening.GENERAL)
                    );
            case SPECIAL ->
                    builder.and(
                            oneseo.appliedScreening.eq(Screening.SPECIAL)
                    );
            case EXTRA ->
                    builder.andAnyOf(
                            oneseo.appliedScreening.eq(Screening.EXTRA_ADMISSION),
                            oneseo.appliedScreening.eq(Screening.EXTRA_VETERANS)
                    );
        }
    }

    private void applyIsSubmittedTag(
            BooleanBuilder builder,
            YesNo isSubmitted
    ) {
        if (isSubmitted == null) return;


        switch (isSubmitted) {
            case YES ->
                    builder.and(
                            oneseo.realOneseoArrivedYn.eq(YesNo.YES)
                    );
            case NO ->
                    builder.and(
                            oneseo.realOneseoArrivedYn.eq(YesNo.NO)
                    );
        }
    }

    private void applyTestResultTag(
            BooleanBuilder builder,
            TestResultTag testResultTag
    ) {

        switch (testResultTag) {
            case FIRST_PASS ->
                    builder.and(
                            entranceTestResult.firstTestPassYn.eq(YesNo.YES)
                    );
            case FINAL_PASS ->
                    builder.and(
                            entranceTestResult.secondTestPassYn.eq(YesNo.YES)
                    );
            case FALL ->
                    builder.andAnyOf(
                            entranceTestResult.firstTestPassYn.eq(YesNo.NO),
                            entranceTestResult.firstTestPassYn.eq(YesNo.NO)
                    );
        }
    }

}
