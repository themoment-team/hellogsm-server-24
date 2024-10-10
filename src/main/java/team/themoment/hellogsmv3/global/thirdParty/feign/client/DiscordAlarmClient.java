package team.themoment.hellogsmv3.global.thirdParty.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "discord-alarm-client", url = "${discord-alarm.url}")
public interface DiscordAlarmClient {
    @PostMapping
    void sendAlarm(
            @RequestBody DiscordAlarmClient reqDto
    );
}
