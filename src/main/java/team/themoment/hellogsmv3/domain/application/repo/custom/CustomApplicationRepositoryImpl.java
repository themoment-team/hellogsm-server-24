package team.themoment.hellogsmv3.domain.application.repo.custom;

import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import team.themoment.hellogsmv3.domain.application.entity.abs.AbstractApplication;
import team.themoment.hellogsmv3.domain.application.type.EvaluationStatus;

import java.util.List;
import java.util.Optional;

import static team.themoment.hellogsmv3.domain.application.entity.abs.QAbstractApplication.abstractApplication;
import static team.themoment.hellogsmv3.domain.application.entity.abs.QAbstractMiddleSchoolGrade.abstractMiddleSchoolGrade;
import static team.themoment.hellogsmv3.domain.application.entity.abs.QAbstractPersonalInformation.abstractPersonalInformation;
import static team.themoment.hellogsmv3.domain.applicant.entity.QApplicant.applicant;




@Repository
@RequiredArgsConstructor
public class CustomApplicationRepositoryImpl implements CustomApplicationRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<AbstractApplication> findByAuthenticationIdWithAllJoins(Long authenticationId) {

        AbstractApplication application = queryFactory.selectFrom(abstractApplication)
                .join(abstractApplication.middleSchoolGrade, abstractMiddleSchoolGrade).fetchJoin()
                .join(abstractApplication.personalInformation, abstractPersonalInformation).fetchJoin()
                .join(abstractApplication.applicant, applicant).fetchJoin()
                .where(applicant.authenticationId.eq(authenticationId))
                .fetchOne();

        return Optional.ofNullable(application);
    }

    @Override
    public Page<AbstractApplication> findAllByFinalSubmitted(Pageable pageable) {

        List<AbstractApplication> applications = queryFactory.selectFrom(abstractApplication)
                .leftJoin(abstractApplication.applicant, applicant).fetchJoin()
                .leftJoin(abstractApplication.personalInformation, abstractPersonalInformation).fetchJoin()
                .where(abstractApplication.finalSubmitted.isTrue())
                .orderBy(
                        new CaseBuilder()
                                .when(abstractApplication.competencyEvaluationResult.evaluationStatus.eq(EvaluationStatus.valueOf("PASS"))).then(30)
                                .when(abstractApplication.competencyEvaluationResult.evaluationStatus.eq(EvaluationStatus.valueOf("FALL"))).then(20)
                                .otherwise(10).desc(),
                        new CaseBuilder()
                                .when(abstractApplication.subjectEvaluationResult.evaluationStatus.eq(EvaluationStatus.valueOf("PASS"))).then(3)
                                .when(abstractApplication.subjectEvaluationResult.evaluationStatus.eq(EvaluationStatus.valueOf("FALL"))).then(2)
                                .otherwise(1).desc()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(abstractApplication.count())
                .from(abstractApplication)
                .where(abstractApplication.finalSubmitted.eq(true))
                .fetchOne();

        return new PageImpl<>(applications, pageable, total);
    }

    @Override
    public Page<AbstractApplication> findAllByFinalSubmittedAndApplicantNameContaining(String keyword, Pageable pageable) {

        List<AbstractApplication> applications = queryFactory.selectFrom(abstractApplication)
                .leftJoin(abstractApplication.applicant, applicant).fetchJoin()
                .leftJoin(abstractApplication.personalInformation, abstractPersonalInformation).fetchJoin()
                .where(
                        abstractApplication.finalSubmitted.isTrue()
                                .and(abstractApplication.applicant.name.like("%" + keyword + "%"))
                )
                .orderBy(
                        new CaseBuilder()
                                .when(abstractApplication.competencyEvaluationResult.evaluationStatus.eq(EvaluationStatus.valueOf("PASS"))).then(30)
                                .when(abstractApplication.competencyEvaluationResult.evaluationStatus.eq(EvaluationStatus.valueOf("FALL"))).then(20)
                                .otherwise(10).desc(),
                        new CaseBuilder()
                                .when(abstractApplication.subjectEvaluationResult.evaluationStatus.eq(EvaluationStatus.valueOf("PASS"))).then(3)
                                .when(abstractApplication.subjectEvaluationResult.evaluationStatus.eq(EvaluationStatus.valueOf("FALL"))).then(2)
                                .otherwise(1).desc()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(abstractApplication.count())
                .from(abstractApplication)
                .where(abstractApplication.finalSubmitted.eq(true))
                .fetchOne();

        return new PageImpl<>(applications, pageable, total);
    }
}
