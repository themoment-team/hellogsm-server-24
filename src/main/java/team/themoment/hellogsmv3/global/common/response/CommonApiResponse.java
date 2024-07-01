package team.themoment.hellogsmv3.global.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.annotation.Nonnull;
import org.springframework.http.HttpStatus;

public record CommonApiResponse<T>(
        HttpStatus status,
        int code,
        String message,
        @JsonInclude(Include.NON_NULL)
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
