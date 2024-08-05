package team.themoment.hellogsmv3.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import lombok.SneakyThrows;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import team.themoment.hellogsmv3.global.common.response.CommonApiResponse;

import java.lang.reflect.Field;

@OpenAPIDefinition(
        info = @Info(title = "Hello, GSM 2024",
                description = "광주소프트웨어마이스터고등학교 입학지원 시스템",
                version = "v1"))
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi api(OperationCustomizer operationCustomizer) {
        return GroupedOpenApi.builder()
                .group("Hello, GSM 2024 API")
                .pathsToMatch("/member/**", "/oneseo/**", "/auth/**")
                .addOperationCustomizer(operationCustomizer)
                .build();
    }

    @Bean
    public OperationCustomizer operationCustomizer() {
        return (operation, handlerMethod) -> {
            this.addResponseBodyWrapperSchemaExample(operation, CommonApiResponse.class, "data");
            return operation;
        };
    }

    private void addResponseBodyWrapperSchemaExample(Operation operation, Class<?> type, String wrapFieldName) {
        final Content content = operation.getResponses().get("200").getContent();
        if (content != null) {
            content.keySet()
                    .forEach(mediaTypeKey -> {
                        final MediaType mediaType = content.get(mediaTypeKey);
                        mediaType.schema(wrapSchema(mediaType.getSchema(), type, wrapFieldName));
                    });
        }
    }

    @SneakyThrows
    private <T> Schema<T> wrapSchema(Schema<?> originalSchema, Class<T> type, String wrapFieldName) {
        final Schema<T> wrapperSchema = new Schema<>();
        final T instance = type.getDeclaredConstructor().newInstance();

        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);
            wrapperSchema.addProperty(field.getName(), new Schema<>().example(field.get(instance)));
            field.setAccessible(false);
        }
        wrapperSchema.addProperty(wrapFieldName, originalSchema);
        return wrapperSchema;
    }
}
