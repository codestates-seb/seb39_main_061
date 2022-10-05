package com.project.QR.qrcode.service;

import com.project.QR.business.service.BusinessService;
import com.project.QR.exception.BusinessLogicException;
import com.project.QR.exception.ExceptionCode;
import com.project.QR.file.service.StorageService;
import com.project.QR.qrcode.entity.QrCode;
import com.project.QR.qrcode.repository.QrCodeRepository;
import com.project.QR.util.CustomBeanUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class QrCodeService {
  private final QrCodeRepository qrCodeRepository;
  private final StorageService storageService;
  private final CustomBeanUtils<QrCode> beanUtils;
  private final BusinessService businessService;

  /**
   * QrCode 등록
   */
  public QrCode createQrCode(QrCode qrCode) {
    businessService.existBusiness(qrCode.getBusiness().getBusinessId(), qrCode.getBusiness().getMember().getMemberId());
    return qrCodeRepository.save(qrCode);
  }


  /**
   * QrCode 변경
   */
  public QrCode updateQrCode(QrCode qrCode, MultipartFile multipartFile) {
    QrCode findQrCode = findVerifiedQrCode(qrCode.getQrCodeId(), qrCode.getBusiness().getBusinessId());
    if (!multipartFile.isEmpty()) {
      if (findQrCode.getQrCodeImg() != null)
        storageService.remove(findQrCode.getQrCodeImg());
      qrCode.setQrCodeImg(storageService.store(multipartFile,
              String.format("%d/qr-code", qrCode.getBusiness().getMember().getMemberId())));
    }
    QrCode updatingQrCode = beanUtils.copyNonNullProperties(qrCode, findQrCode);
    return qrCodeRepository.save(updatingQrCode);
  }

  /**
   * qrCode 아이디로 QR 코드 찾기
   */
  @Transactional(readOnly = true)
  private QrCode findVerifiedQrCode(long qrCodeId, long businessId) {
    Optional<QrCode> optionalQrCode = qrCodeRepository.findByIdAndBusinessId(qrCodeId, businessId);
    return optionalQrCode.orElseThrow(() -> new BusinessLogicException(ExceptionCode.QR_CODE_NOT_FOUND));
  }

  /**
   * 특정 QrCode 조회
   */
  @Transactional(readOnly = true)
  public QrCode getQrCode(long qrCodeId, long businessId, long memberId) {
    QrCode qrCode = findVerifiedQrCode(qrCodeId, businessId);
    if (qrCode.getBusiness().getMember().getMemberId() != memberId)
      throw new BusinessLogicException(ExceptionCode.QR_CODE_NOT_FOUND);
    return qrCode;
  }

  /**
   * 전체 QrCode 리스트 조회
   */
  public Page<QrCode> getQrCodes(int page, int size, long businessId, long memberId) {
    return qrCodeRepository.findAllByBusinessIdAndMemberId(businessId, memberId,
      PageRequest.of(page, size, Sort.by("CREATED_AT").descending()));
  }

  /**
   * 특정 QrCode 삭제
   */
  public void deleteQrCode(long qrCodeId, long businessId, long memberId) {
    QrCode qrCode = findVerifiedQrCode(qrCodeId, businessId);
    if(qrCode.getBusiness().getMember().getMemberId() != memberId)
      throw new BusinessLogicException(ExceptionCode.QR_CODE_NOT_FOUND);
    if(qrCode.getQrCodeImg() != null)
      storageService.remove(qrCode.getQrCodeImg());
    qrCodeRepository.delete(qrCode);
  }
}