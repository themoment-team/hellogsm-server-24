package team.themoment.hellogsmv3.global.thirdParty.aws.sns.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.member.service.SendCodeNotificationService;
import team.themoment.hellogsmv3.global.thirdParty.aws.sns.SendSmsService;

@Service
@RequiredArgsConstructor
public class SendCodeNotificationServiceImpl implements SendCodeNotificationService {

    private final SendSmsService sendSmsService;

    @Override
    public void execute(String phoneNumber, String code) {
        sendSmsService.execute(phoneNumber, createMessage(code));
    }

    private static String createMessage(String code) {
        return "[Hello, GSM] 본인인증번호 [" + code + "]를 입력해주세요.";
    }
}
