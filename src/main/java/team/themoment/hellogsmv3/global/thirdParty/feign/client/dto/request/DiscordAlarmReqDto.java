package team.themoment.hellogsmv3.global.thirdParty.feign.client.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team.themoment.hellogsmv3.global.thirdParty.feign.client.type.Env;
import team.themoment.hellogsmv3.global.thirdParty.feign.client.type.NoticeLevel;

@Getter
@AllArgsConstructor
public class DiscordAlarmReqDto {
    private String title;
    private String content;
    private NoticeLevel noticeLevel;
    private Env env;
}
