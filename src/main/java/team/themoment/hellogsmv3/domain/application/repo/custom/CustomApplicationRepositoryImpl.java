package team.themoment.hellogsmv3.domain.application.repo.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.themoment.hellogsmv3.domain.application.entity.abs.AbstractApplication;

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
    public Optional<AbstractApplication> findByApplicantIdWithAllJoins(Long applicantId) {

        AbstractApplication application = queryFactory.selectFrom(abstractApplication)
                .join(abstractApplication.middleSchoolGrade, abstractMiddleSchoolGrade).fetchJoin()
                .join(abstractApplication.personalInformation, abstractPersonalInformation).fetchJoin()
                .join(abstractApplication.applicant, applicant).fetchJoin()
                .where(applicant.id.eq(applicantId))
                .fetchOne();

        return Optional.ofNullable(application);
    }
}
