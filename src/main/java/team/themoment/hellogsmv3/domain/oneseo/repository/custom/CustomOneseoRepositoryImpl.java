package team.themoment.hellogsmv3.domain.oneseo.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening;

import static team.themoment.hellogsmv3.domain.oneseo.entity.QOneseo.oneseo;

@Repository
@RequiredArgsConstructor
public class CustomOneseoRepositoryImpl implements CustomOneseoRepository{

    private final JPAQueryFactory queryFactory;


    @Override
    public Integer findMaxSubmitCodeByScreening(Screening screening) {
        return queryFactory
                .select(oneseo.oneseoSubmitCode.substring(2).castToNum(Integer.class))
                .from(oneseo)
                .where(oneseo.appliedScreening.eq(screening))
                .orderBy(oneseo.oneseoSubmitCode.substring(2).castToNum(Integer.class).desc())
                .fetchFirst();
    }
}
