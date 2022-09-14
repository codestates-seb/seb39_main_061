package com.project.QR.helper.event;

import com.project.QR.auth.service.AuthService;
import com.project.QR.helper.email.EmailSender;
import com.project.QR.member.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.mail.MailSendException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@EnableAsync
@Configuration
@Component
@Slf4j
public class MemberPasswordEventListener {
  @Value("${mail.subject.member.password}")
  private String subject;
  private final EmailSender emailSender;

  public MemberPasswordEventListener(EmailSender emailSender) {
    this.emailSender = emailSender;
  }

  @Async
  @EventListener
  public void listen(MemberPasswordApplicationEvent event) throws Exception {
    try {
      String[] to = new String[]{event.getEmail()};
      String message = "<h1>" + event.getName() + "님, 비밀번호를 재발급해드립니다.</h1>\n" +
        "<div>아래 비밀번호로 로그인 후 비밀번호 변경을 해주세요.</div>" +
        "<div>"+ event.getPassword() +"</div>";
      emailSender.sendEmail(to, subject, message);
    } catch (MailSendException e) {
      log.error("MailSendException: rollback for Member Registration:");
    }
  }
}
