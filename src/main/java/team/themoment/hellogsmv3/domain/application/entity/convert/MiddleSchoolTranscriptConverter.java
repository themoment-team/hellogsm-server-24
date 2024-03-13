package team.themoment.hellogsmv3.domain.application.entity.convert;

import com.google.gson.Gson;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import team.themoment.hellogsmv3.domain.application.type.MiddleSchoolTranscript;
import team.themoment.hellogsmv3.domain.application.type.SemesterType;

import java.util.List;

@Converter
public class MiddleSchoolTranscriptConverter implements AttributeConverter<MiddleSchoolTranscript, String> {

    private static final Gson gson = new Gson();

    @Override
    public String convertToDatabaseColumn(MiddleSchoolTranscript attribute) {
        return gson.toJson(attribute);
    }

    @Override
    public MiddleSchoolTranscript convertToEntityAttribute(String dbData) {
        return gson.fromJson(dbData, MiddleSchoolTranscript.class);
    }

    private SemesterType getSemesterType(List<String> generalCurriculumSemesters) {
        for (SemesterType type : SemesterType.values()) {
            if (type.getSemesters().equals(generalCurriculumSemesters)) {
                return type;
            }
        }
        throw new RuntimeException();
    }
}
