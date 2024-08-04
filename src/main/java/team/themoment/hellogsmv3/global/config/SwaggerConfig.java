package team.themoment.hellogsmv3.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "Hello, GSM 2024",
                description = "광주소프트웨어마이스터고등학교 입학지원 시스템",
                version = "v1"))
@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("Hello, GSM 2024 API")
                .pathsToMatch("/member/**", "/oneseo/**")
                .build();
    }
}
