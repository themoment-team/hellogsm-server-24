package team.themoment.hellogsmv3.global.thirdParty.feign.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import team.themoment.hellogsmv3.global.thirdParty.feign.client.DiscordAlarmClient;
import team.themoment.hellogsmv3.global.thirdParty.feign.client.dto.request.DiscordAlarmReqDto;

@Component
@RequiredArgsConstructor
public class DiscordAlarmFeignClientService {

    private final DiscordAlarmClient discordAlarmClient;

    public void send(DiscordAlarmReqDto reqDto) {
        discordAlarmClient.sendAlarm(reqDto);
    }
}
