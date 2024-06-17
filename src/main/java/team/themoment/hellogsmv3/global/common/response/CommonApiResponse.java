package team.themoment.hellogsmv3.global.common.response;

import jakarta.annotation.Nonnull;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public record CommonApiResponse<T>(
        HttpStatus status,
        int code,
        String message,
        T data
) {
    public static CommonApiResponse success(@Nonnull String message) {
        return new CommonApiResponse(HttpStatus.OK, HttpStatus.OK.value(), message, null);
    }

    public static CommonApiResponse created(@Nonnull String message) {
        return new CommonApiResponse(HttpStatus.CREATED, HttpStatus.CREATED.value(), message, null);
    }

    public static CommonApiResponse error(@Nonnull String message, @Nonnull HttpStatus status) {
        return new CommonApiResponse(status, status.value(), message, null);
    }
}
