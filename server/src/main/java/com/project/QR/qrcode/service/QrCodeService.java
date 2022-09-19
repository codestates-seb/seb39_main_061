package com.project.QR.qrcode.service;

import com.project.QR.exception.BusinessLogicException;
import com.project.QR.exception.ExceptionCode;
import com.project.QR.file.service.FileSystemStorageService;
import com.project.QR.qrcode.entity.QrCode;
import com.project.QR.qrcode.repository.QrCodeRepository;
import com.project.QR.util.CustomBeanUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class QrCodeService {
  private final QrCodeRepository qrCodeRepository;
  private final FileSystemStorageService fileSystemStorageService;
  private final CustomBeanUtils<QrCode> beanUtils;

  /**
   * QrCode 등록
   */
  public QrCode createQrCode(QrCode qrCode) {
    return qrCodeRepository.save(qrCode);
  }


  /**
   * QrCode 변경
   */
  public QrCode updateQrCode(QrCode qrCode, MultipartFile multipartFile) {
    QrCode findQrCode = findVerifiedQrCode(qrCode.getQrCodeId());
    if(!multipartFile.isEmpty()) {
      if(findQrCode.getQrCodeImg() != null)
        fileSystemStorageService.remove(findQrCode.getQrCodeImg());
      qrCode.setQrCodeImg(fileSystemStorageService.store(multipartFile,
        String.format("%d/qr-code", qrCode.getMember().getMemberId())));
    }
    QrCode updatingQrCode = beanUtils.copyNonNullProperties(qrCode, findQrCode);
    return qrCodeRepository.save(updatingQrCode);
  }

  /**
   * qrCode 아이디로 QR 코드 찾기
   */
  @Transactional(readOnly = true)
  public QrCode findVerifiedQrCode(long qrCodeId) {
    Optional<QrCode> optionalQrCode = qrCodeRepository.findById(qrCodeId);
    return optionalQrCode.orElseThrow(() -> new BusinessLogicException(ExceptionCode.QR_CODE_NOT_FOUND));
  }

  /**
   * 특정 QrCode 조회
   */
  @Transactional(readOnly = true)
  public QrCode getQrCode(long memberId, long qrCodeId) {
    QrCode qrCode = findVerifiedQrCode(qrCodeId);
    if(qrCode.getMember().getMemberId() != memberId)
      throw new BusinessLogicException(ExceptionCode.QR_CODE_NOT_FOUND);
    return qrCode;
  }

  /**
   * 특정 QrCode 삭제
   */
  public void deleteQrCode(long qrCodeId, long memberId) {
    QrCode qrCode = findVerifiedQrCode(qrCodeId);
    if(qrCode.getMember().getMemberId() != memberId)
      throw new BusinessLogicException(ExceptionCode.QR_CODE_NOT_FOUND);
    qrCodeRepository.delete(qrCode);
  }

  public void getStatisticsByMonth(String date, Long memberId) {
  }

  public void getStatisticsByYear(String date, Long memberId) {
  }

  public void getStatisticsByTime(String date, Long memberId) {
  }
}