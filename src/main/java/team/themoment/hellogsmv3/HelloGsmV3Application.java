package team.themoment.hellogsmv3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import team.themoment.hellogsmv3.global.security.auth.AuthEnvironment;

@SpringBootApplication
@EnableConfigurationProperties({AuthEnvironment.class})
public class HelloGsmV3Application {

    public static void main(String[] args) {
        SpringApplication.run(HelloGsmV3Application.class, args);
    }

}
