package team.themoment.hellogsmv3.global.common.response;

import jakarta.annotation.Nonnull;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public record CommonApiResponse<T>(
        HttpStatus status,
        int code,
        String message,
        T data
) { }
