package team.themoment.hellogsmv3.global.common.response;

import jakarta.annotation.Nonnull;

public record CommonApiResponse(String message) {
    public static CommonApiResponse success(@Nonnull String message) {
        return new CommonApiResponse(message);
    }
}
