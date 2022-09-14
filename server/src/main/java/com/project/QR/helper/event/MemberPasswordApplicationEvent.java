package com.project.QR.helper.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MemberPasswordApplicationEvent extends ApplicationEvent {
  private String name;
  private String password;
  private String email;

  public MemberPasswordApplicationEvent(Object source, String email, String name, String password) {
    super(source);
    this.name = name;
    this.email = email;
    this.password = password;
  }
}
