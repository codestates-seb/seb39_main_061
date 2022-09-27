package com.project.QR.helper.event;

import com.project.QR.helper.email.EmailSender;
import com.project.QR.member.entity.Member;
import com.project.QR.qrcode.entity.QrCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.mail.MailSendException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@EnableAsync
@Configuration
@Component
@Slf4j
public class QrCodeRemoveEventListener {
  @Value("${mail.subject.qrCode.removed}")
  private String subject;
  private final EmailSender emailSender;

  public QrCodeRemoveEventListener(EmailSender emailSender) {
    this.emailSender = emailSender;
  }

  @Async
  @EventListener
  public void listen(QrCodeRemoveApplicationEvent event) throws Exception {
    try {
      Member member = event.getQrCode().getBusiness().getMember();
      QrCode qrCode = event.getQrCode();
      String[] to = new String[]{member.getEmail()};
      String message = "<h1>" + member.getName() + "님, 설정하신 QR 코드가 만료되었습니다.</h1>\n" +
        "<div>대상 : "+qrCode.getTarget()+ "</div>" +
        "<div>만료 기간 : "+ qrCode.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+ "</div>" +
        "<h2>QR 코드를 재생성해주세요.</h2>";
      emailSender.sendEmail(to, subject, message);
    } catch (MailSendException e) {
      log.error("MailSendException: rollback for Member Registration:");
    }
  }
}
