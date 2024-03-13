package team.themoment.hellogsmv3.domain.applicant.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@RedisHash(timeToLive = 3600L)
public class AuthenticationCode {
    @Id
    private String code;
    @Indexed
    private Long applicantId;
    private Boolean authenticated;
    private String phoneNumber;
    private LocalDateTime createdAt;
}
