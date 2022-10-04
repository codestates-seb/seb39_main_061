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
        "<div>아래 버튼을 통해 이메일 인증을 완료해주세요</div>" +
        "<div>" +
        "<a href=\"" + redirectUrl + "/email-validation?email=" + event.getMember().getEmail() + "&code=" + event.getMember().getVerifiedCode() +"\">" +
        "<button style='appearance: button;\n" +
        "  backface-visibility: hidden;\n" +
        "  background-color: #405cf5;\n" +
        "  border-radius: 6px;\n" +
        "  border-width: 0;\n" +
        "  box-shadow: rgba(50, 50, 93, .1) 0 0 0 1px inset,rgba(50, 50, 93, .1) 0 2px 5px 0,rgba(0, 0, 0, .07) 0 1px 1px 0;\n" +
        "  box-sizing: border-box;\n" +
        "  color: #fff;\n" +
        "  cursor: pointer;\n" +
        "  font-family: -apple-system,system-ui,\"Segoe UI\",Roboto,\"Helvetica Neue\",Ubuntu,sans-serif;\n" +
        "  font-size: 100%;\n" +
        "  height: 44px;\n" +
        "  line-height: 1.15;\n" +
        "  margin: 12px 0 0;\n" +
        "  outline: none;\n" +
        "  overflow: hidden;\n" +
        "  padding: 0 25px;\n" +
        "  position: relative;\n" +
        "  text-align: center;\n" +
        "  text-transform: none;\n" +
        "  transform: translateZ(0);\n" +
        "  transition: all .2s,box-shadow .08s ease-in;\n" +
        "  user-select: none;\n" +
        "  -webkit-user-select: none;\n" +
        "  touch-action: manipulation;\n" +
        "  width: 100%;'>홈페이지로 이동</button></a></div>";
      emailSender.sendEmail(to, subject, message);
    } catch (MailSendException e) {
      e.printStackTrace();
      log.error("MailSendException: rollback for Member Registration:");
      Member member = event.getMember();
      authService.deleteMember(member.getMemberId());
    }
  }
}
