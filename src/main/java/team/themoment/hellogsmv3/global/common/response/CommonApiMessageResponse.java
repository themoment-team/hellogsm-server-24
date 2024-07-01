package team.themoment.hellogsmv3.global.common.response;

import jakarta.annotation.Nonnull;
import org.springframework.http.HttpStatus;

public record CommonApiMessageResponse<T>(
        HttpStatus status,
        int code,
        String message
) {
}
