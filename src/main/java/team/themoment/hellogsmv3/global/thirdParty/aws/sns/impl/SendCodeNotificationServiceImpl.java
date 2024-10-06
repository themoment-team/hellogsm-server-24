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
        sendSmsService.execute(phoneNumber, createContentMessage(code), createFooterMessage());
    }

    private static String createContentMessage(String code) {
        return String.format(
                """
                [Hello, GSM | 광주소프트웨어마이스터고등학교 입학지원시스템]
                
                인증번호 [%s]를 입력해주세요.
                """, code
        );
    }

    private static String createFooterMessage() {
        return "본인이 요청한게 아니라면 행정실 062-949-6800 으로 문의해주세요.";
    }
}
