package team.themoment.hellogsmv3.global.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationPropertiesScan(basePackages = {
                "team.themoment.hellogsmv3.global.security.auth",
                "team.themoment.hellogsmv3.global.thirdParty.aws.s3.properties"})
public class PropertiesScanConfig {
}
