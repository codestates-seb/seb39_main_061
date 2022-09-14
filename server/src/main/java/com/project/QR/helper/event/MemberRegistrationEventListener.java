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
public class MemberRegistrationEventListener {
  @Value("${mail.subject.member.registration}")
  private String subject;
  @Value("${mail.redirect-url}")
  private String redirectUrl;
  private final EmailSender emailSender;
  private final AuthService authService;

  public MemberRegistrationEventListener(EmailSender emailSender, AuthService authService) {
    this.emailSender = emailSender;
    this.authService = authService;
  }

  @Async
  @EventListener
  public void listen(MemberRegistrationApplicationEvent event) throws Exception {
    try {
      String[] to = new String[]{event.getMember().getEmail()};
      String message = "<h1>" + event.getMember().getName() + "님, 회원 가입이 성공적으로 완료되었습니다</h1>.\n" +
        "<div>아래 링크를 통해 이메일 인증을 완료해주세요</div>" +
        "<div>"+ redirectUrl + "/auth/validation?email=" + event.getMember().getEmail() + "&code=" + event.getMember().getVerifiedCode() +"</div>";
      emailSender.sendEmail(to, subject, message);
    } catch (MailSendException e) {
      e.printStackTrace();
      log.error("MailSendException: rollback for Member Registration:");
      Member member = event.getMember();
      authService.deleteMember(member.getMemberId());
    }
  }
}
