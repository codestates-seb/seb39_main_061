package com.project.QR.keep.service;

import com.project.QR.keep.entity.Keep;
import com.project.QR.qrcode.service.QrCodeService;
import com.project.QR.util.CustomBeanUtils;

public class KeepService {
  private KeepRepository keepRepository;
  private CustomBeanUtils<Keep> beanUtils;
  private QrCodeService qrCodeService;

  /**
   * 자재 등록
   */
  public Keep createkeep(Keep keep) {
    if(findExisKeep(keep.getQrCode().getQrCodeId(), keep.get))
  }
}
