package team.themoment.hellogsmv3.global.security.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CookieName {
    JSESSIONID("JSESSIONID"),
    REMEMBER_ME("remember_me");

    private final String name;
}
