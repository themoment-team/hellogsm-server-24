package team.themoment.hellogsmv3.global.thirdParty.feign.client.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NoticeLevel {
    INFO("info"),
    WARN("warn"),
    ERROR("error");

    private final String content;
}
