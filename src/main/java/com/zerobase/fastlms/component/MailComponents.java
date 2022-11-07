package com.zerobase.fastlms.component;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@RequiredArgsConstructor
@Component
public class MailComponents {
    // 1. 도메인 구매
    // 2. 해당하는 메일 서버 구축하고
    // ---> 구글에서 제공해줌

    private final JavaMailSender javaMailSender;

    public void sendMailTest() {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("chi4321@naver.com");
        msg.setSubject("안녕하세요 제로베이스입니다.");
        msg.setText("   안녕하세요, 제로베이스입니다. 반갑습니다.  ");

        javaMailSender.send(msg);
    }

    public boolean sendMail(String mail, String subject, String text) {

        boolean result = false;

        MimeMessagePreparator msg = new MimeMessagePreparator() {

            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
                helper.setTo(mail);
                helper.setSubject(subject);
                helper.setText(text, true);
            }
        };
        try {
            javaMailSender.send(msg);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
