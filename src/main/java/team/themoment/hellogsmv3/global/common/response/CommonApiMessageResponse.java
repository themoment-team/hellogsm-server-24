package team.themoment.hellogsmv3.global.common.response;

import jakarta.annotation.Nonnull;
import org.springframework.http.HttpStatus;

public record CommonApiMessageResponse<T>(
        HttpStatus status,
        int code,
        String message
) {
    public static CommonApiMessageResponse success(@Nonnull String message) {
        return new CommonApiMessageResponse(HttpStatus.OK, HttpStatus.OK.value(), message);
    }

    public static CommonApiMessageResponse created(@Nonnull String message) {
        return new CommonApiMessageResponse(HttpStatus.CREATED, HttpStatus.CREATED.value(), message);
    }

    public static CommonApiMessageResponse error(@Nonnull String message, @Nonnull HttpStatus status) {
        return new CommonApiMessageResponse(status, status.value(), message);
    }
}
