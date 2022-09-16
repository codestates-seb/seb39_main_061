package com.project.QR.qrcode.service;

import com.project.QR.member.entity.Member;
import com.project.QR.qrcode.entity.QrCode;
import com.project.QR.qrcode.repository.QrCodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class QrCodeService {
  private final QrCodeRepository qrCodeRepository;



  /**
   * QrCode 등록
   */
  public QrCode createQrCode(QrCode qrCode) {

    QrCode qrCode1 = qrCodeRepository.save(qrCode);

    return qrCodeRepository.save(qrCode1);
  }


  /**
   * QrCode 변경
   */
  public QrCode updateQrCode(QrCode QrCode) {

    //QrCode findQrCode = findVerifiedQrCode (QrCode.getQrCodeId());

    //Optional.ofNullable(QrCode.getQrCodeImg())
    //        .ifPresent(findQrCode::setQrCodeImg);

    return qrCodeRepository.save(QrCode);
  }

  /**
   * 특정 QrCode 조회
   */
  public QrCode getQrCode(long memberId, long qrCodeId) {

    QrCode QrCode = getQrCode(memberId, qrCodeId);

    return QrCode;
  }


  /**
   * 특정 QrCode 삭제
   */
  public void deleteQrCode(long qrCodeId, long memberId) {

    //QrCode getQrCode = existQrCode(qrCodeId);


    //qrCodeRepository.delete(qrCodeId);

  }
}