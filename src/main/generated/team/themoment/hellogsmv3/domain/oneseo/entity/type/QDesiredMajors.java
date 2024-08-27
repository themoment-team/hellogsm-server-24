package team.themoment.hellogsmv3.domain.oneseo.entity.type;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDesiredMajors is a Querydsl query type for DesiredMajors
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QDesiredMajors extends BeanPath<DesiredMajors> {

    private static final long serialVersionUID = 742705450L;

    public static final QDesiredMajors desiredMajors = new QDesiredMajors("desiredMajors");

    public final EnumPath<Major> firstDesiredMajor = createEnum("firstDesiredMajor", Major.class);

    public final EnumPath<Major> secondDesiredMajor = createEnum("secondDesiredMajor", Major.class);

    public final EnumPath<Major> thirdDesiredMajor = createEnum("thirdDesiredMajor", Major.class);

    public QDesiredMajors(String variable) {
        super(DesiredMajors.class, forVariable(variable));
    }

    public QDesiredMajors(Path<? extends DesiredMajors> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDesiredMajors(PathMetadata metadata) {
        super(DesiredMajors.class, metadata);
    }

}

