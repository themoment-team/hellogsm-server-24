package team.themoment.hellogsmv3.global.common.response;

import jakarta.annotation.Nonnull;

public record ApiResponse (String message) {
    public static ApiResponse success(@Nonnull String message) {
        return new ApiResponse(message);
    }
}
