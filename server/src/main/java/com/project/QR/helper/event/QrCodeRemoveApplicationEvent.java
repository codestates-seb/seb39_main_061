package com.project.QR.helper.event;

import com.project.QR.qrcode.entity.QrCode;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class QrCodeRemoveApplicationEvent extends ApplicationEvent {
  private QrCode qrCode;

  public QrCodeRemoveApplicationEvent(Object source, QrCode qrCode) {
    super(source);
    this.qrCode = qrCode;
  }
}
