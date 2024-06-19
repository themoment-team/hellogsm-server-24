package team.themoment.hellogsmv3.domain.applicant.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(timeToLive = 3600L)
public class AuthenticationCode {
    @Id
    @Indexed
    private Long authenticationId;
    @Indexed
    private String code;
    private Boolean authenticated;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private int count;

    public AuthenticationCode updatedCode(String code, LocalDateTime createdAt, Boolean isTest) {
        this.code = code;
        this.createdAt = createdAt;
        this.count = !isTest ? (count + 1) : 0; // 테스트 상황이라면 count가 증가하지 않음
        return this;
    }

    public AuthenticationCode(Long authenticationId, String code, String phoneNumber, LocalDateTime createdAt) {
        this.authenticationId = authenticationId;
        this.code = code;
        this.authenticated = false;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
        this.count = 1;
    }

    public void authenticatedAuthenticationCode() {
        this.authenticated = true;
    }
}
