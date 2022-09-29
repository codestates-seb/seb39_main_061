package com.project.QR.helper.sms;

import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Sms {
  @Value("${app.sms.api_key}")
  private String API_KEY;
  @Value("${app.sms.api_secret}")
  private String API_SECRET;
  @Value("${app.sms.enter}")
  private String ENTER;
  @Value("${app.sms.cancel}")
  private String CANCEL;
  @Value("${app.sms.from}")
  private String FROM;

  public void send(String name, String phone, String type) {
    DefaultMessageService messageService = NurigoApp.INSTANCE.initialize(API_KEY, API_SECRET, "https://api.coolsms.co.kr");
    Message message = new Message();
    message.setFrom(FROM);
    message.setTo(phone.replaceAll("-", ""));
    message.setText(name + (type.equals("enter") ? ENTER : CANCEL));
    SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));
    log.info("sms response: ", response);
  }
}
