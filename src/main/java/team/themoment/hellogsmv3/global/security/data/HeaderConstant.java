package team.themoment.hellogsmv3.global.security.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HeaderConstant {
    X_H_ENV("X-HG-ENV");

    private final String content;
}
