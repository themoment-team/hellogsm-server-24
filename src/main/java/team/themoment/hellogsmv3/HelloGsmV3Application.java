package team.themoment.hellogsmv3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import team.themoment.hellogsmv3.global.security.auth.AuthEnvironment;
import team.themoment.hellogsmv3.global.thirdParty.aws.s3.properties.S3Environment;

@SpringBootApplication
@EnableConfigurationProperties({AuthEnvironment.class, S3Environment.class})
public class HelloGsmV3Application {

    public static void main(String[] args) {
        SpringApplication.run(HelloGsmV3Application.class, args);
    }

}
