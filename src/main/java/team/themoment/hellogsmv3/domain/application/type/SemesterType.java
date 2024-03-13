package team.themoment.hellogsmv3.domain.application.type;

import java.util.Collections;
import java.util.List;

public enum SemesterType {

    FREE_GRADE(List.of(SemesterType.GRADE_2_1, SemesterType.GRADE_2_2, SemesterType.GRADE_3_1)),
    GRADE_1_1_FREE_SEMESTER(List.of(SemesterType.GRADE_1_2, SemesterType.GRADE_2_1, SemesterType.GRADE_2_2, SemesterType.GRADE_3_1)),
    GRADE_1_2_FREE_SEMESTER(List.of(SemesterType.GRADE_1_1, SemesterType.GRADE_2_1, SemesterType.GRADE_2_2, SemesterType.GRADE_3_1)),
    GRADE_2_1_FREE_SEMESTER(List.of(SemesterType.GRADE_1_1, SemesterType.GRADE_1_2, SemesterType.GRADE_2_2, SemesterType.GRADE_3_1)),
    GRADE_2_2_FREE_SEMESTER(List.of(SemesterType.GRADE_1_1, SemesterType.GRADE_1_2, SemesterType.GRADE_2_1, SemesterType.GRADE_3_1)),
    GRADE_3_1_FREE_SEMESTER(List.of(SemesterType.GRADE_1_1, SemesterType.GRADE_1_2, SemesterType.GRADE_2_1, SemesterType.GRADE_2_2));

    public static final String GRADE_1_1 = "1-1";
    public static final String GRADE_1_2 = "1-2";
    public static final String GRADE_2_1 = "2-1";
    public static final String GRADE_2_2 = "2-2";
    public static final String GRADE_3_1 = "3-1";

    private final List<String> semesters;

    SemesterType(List<String> semesters) {
        this.semesters = semesters;
    }

    public List<String> getSemesters() {
        return Collections.unmodifiableList(semesters);
    }
}
