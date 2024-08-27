package team.themoment.hellogsmv3.domain.oneseo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMiddleSchoolAchievement is a Querydsl query type for MiddleSchoolAchievement
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMiddleSchoolAchievement extends EntityPathBase<MiddleSchoolAchievement> {

    private static final long serialVersionUID = -1866957764L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMiddleSchoolAchievement middleSchoolAchievement = new QMiddleSchoolAchievement("middleSchoolAchievement");

    public final ListPath<Integer, NumberPath<Integer>> absentDays = this.<Integer, NumberPath<Integer>>createList("absentDays", Integer.class, NumberPath.class, PathInits.DIRECT2);

    public final ListPath<Integer, NumberPath<Integer>> achievement1_2 = this.<Integer, NumberPath<Integer>>createList("achievement1_2", Integer.class, NumberPath.class, PathInits.DIRECT2);

    public final ListPath<Integer, NumberPath<Integer>> achievement2_1 = this.<Integer, NumberPath<Integer>>createList("achievement2_1", Integer.class, NumberPath.class, PathInits.DIRECT2);

    public final ListPath<Integer, NumberPath<Integer>> achievement2_2 = this.<Integer, NumberPath<Integer>>createList("achievement2_2", Integer.class, NumberPath.class, PathInits.DIRECT2);

    public final ListPath<Integer, NumberPath<Integer>> achievement3_1 = this.<Integer, NumberPath<Integer>>createList("achievement3_1", Integer.class, NumberPath.class, PathInits.DIRECT2);

    public final ListPath<Integer, NumberPath<Integer>> achievement3_2 = this.<Integer, NumberPath<Integer>>createList("achievement3_2", Integer.class, NumberPath.class, PathInits.DIRECT2);

    public final ListPath<Integer, NumberPath<Integer>> artsPhysicalAchievement = this.<Integer, NumberPath<Integer>>createList("artsPhysicalAchievement", Integer.class, NumberPath.class, PathInits.DIRECT2);

    public final ListPath<String, StringPath> artsPhysicalSubjects = this.<String, StringPath>createList("artsPhysicalSubjects", String.class, StringPath.class, PathInits.DIRECT2);

    public final ListPath<Integer, NumberPath<Integer>> attendanceDays = this.<Integer, NumberPath<Integer>>createList("attendanceDays", Integer.class, NumberPath.class, PathInits.DIRECT2);

    public final StringPath freeSemester = createString("freeSemester");

    public final NumberPath<java.math.BigDecimal> gedTotalScore = createNumber("gedTotalScore", java.math.BigDecimal.class);

    public final ListPath<String, StringPath> generalSubjects = this.<String, StringPath>createList("generalSubjects", String.class, StringPath.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath liberalSystem = createString("liberalSystem");

    public final ListPath<String, StringPath> newSubjects = this.<String, StringPath>createList("newSubjects", String.class, StringPath.class, PathInits.DIRECT2);

    public final QOneseo oneseo;

    public final ListPath<Integer, NumberPath<Integer>> volunteerTime = this.<Integer, NumberPath<Integer>>createList("volunteerTime", Integer.class, NumberPath.class, PathInits.DIRECT2);

    public QMiddleSchoolAchievement(String variable) {
        this(MiddleSchoolAchievement.class, forVariable(variable), INITS);
    }

    public QMiddleSchoolAchievement(Path<? extends MiddleSchoolAchievement> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMiddleSchoolAchievement(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMiddleSchoolAchievement(PathMetadata metadata, PathInits inits) {
        this(MiddleSchoolAchievement.class, metadata, inits);
    }

    public QMiddleSchoolAchievement(Class<? extends MiddleSchoolAchievement> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.oneseo = inits.isInitialized("oneseo") ? new QOneseo(forProperty("oneseo"), inits.get("oneseo")) : null;
    }

}

