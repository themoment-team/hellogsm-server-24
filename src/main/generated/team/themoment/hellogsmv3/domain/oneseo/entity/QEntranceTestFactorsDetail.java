package team.themoment.hellogsmv3.domain.oneseo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEntranceTestFactorsDetail is a Querydsl query type for EntranceTestFactorsDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEntranceTestFactorsDetail extends EntityPathBase<EntranceTestFactorsDetail> {

    private static final long serialVersionUID = 1584151459L;

    public static final QEntranceTestFactorsDetail entranceTestFactorsDetail = new QEntranceTestFactorsDetail("entranceTestFactorsDetail");

    public final NumberPath<java.math.BigDecimal> artsPhysicalSubjectsScore = createNumber("artsPhysicalSubjectsScore", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> attendanceScore = createNumber("attendanceScore", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> generalSubjectsScore = createNumber("generalSubjectsScore", java.math.BigDecimal.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<java.math.BigDecimal> score1_2 = createNumber("score1_2", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> score2_1 = createNumber("score2_1", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> score2_2 = createNumber("score2_2", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> score3_1 = createNumber("score3_1", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> score3_2 = createNumber("score3_2", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> totalNonSubjectsScore = createNumber("totalNonSubjectsScore", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> totalSubjectsScore = createNumber("totalSubjectsScore", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> volunteerScore = createNumber("volunteerScore", java.math.BigDecimal.class);

    public QEntranceTestFactorsDetail(String variable) {
        super(EntranceTestFactorsDetail.class, forVariable(variable));
    }

    public QEntranceTestFactorsDetail(Path<? extends EntranceTestFactorsDetail> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEntranceTestFactorsDetail(PathMetadata metadata) {
        super(EntranceTestFactorsDetail.class, metadata);
    }

}

