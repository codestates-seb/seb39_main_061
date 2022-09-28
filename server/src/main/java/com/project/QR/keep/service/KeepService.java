package com.project.QR.keep.service;

import com.project.QR.business.service.BusinessService;
import com.project.QR.exception.BusinessLogicException;
import com.project.QR.exception.ExceptionCode;
import com.project.QR.keep.entity.Keep;
import com.project.QR.keep.repository.KeepRepository;
import com.project.QR.qrcode.service.QrCodeService;
import com.project.QR.util.CustomBeanUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class KeepService {
  private KeepRepository keepRepository;
  private CustomBeanUtils<Keep> beanUtils;
  private BusinessService businessService;
  private QrCodeService qrCodeService;

  /**
   * 자재 등록
   */
  public Keep createKeep(Keep keep) {
    qrCodeService.getQrCode(
            keep.getQrCode().getQrCodeId(),
            keep.getQrCode().getBusiness().getBusinessId(),
            keep.getQrCode().getBusiness().getMember().getMemberId());
    return keepRepository.save(keep);
  }

  /**
   * 자재 리스트 조회(업주 입장)
   */
  public Page<Keep> getAdminKeepList(long businessId, long qrCodeId, long memberId, int page, int size) {
    businessService.getBusiness(businessId, memberId);
    return keepRepository.findAllByBusinessIdAndQrCodeId(businessId, qrCodeId,
            PageRequest.of(page, size, Sort.by("CREATED_AT").descending()));
  }

  /**
   * 자재 정보 변경
   */
  public Keep updateKeep(Keep keep) {
    qrCodeService.getQrCode(keep.getQrCode().getQrCodeId(),
                            keep.getQrCode().getBusiness().getBusinessId(),
                            keep.getQrCode().getBusiness().getMember().getMemberId());
    Keep findKeep = findVerifiedKeep(keep.getKeepId(), keep.getQrCode().getQrCodeId());
    Keep updatingKeep = beanUtils.copyNonNullProperties(keep, findKeep);
    return keepRepository.save(updatingKeep);
  }

  /**
   * 자재 조회
   */
  public Keep findVerifiedKeep(long keepId, long qrCodeId) {
    Optional<Keep> optionalKeep = keepRepository.findByIdAndQrCodeId(keepId, qrCodeId);
    return optionalKeep.orElseThrow(() -> new BusinessLogicException(ExceptionCode.KEEP_NOT_FOUND));
  }

  /**
   * 자재 삭제
   */
  public void deleteKeep(Keep keep) {
    qrCodeService.getQrCode(
            keep.getQrCode().getQrCodeId(),
            keep.getQrCode().getBusiness().getBusinessId(),
            keep.getQrCode().getBusiness().getMember().getMemberId());
    Keep findKeep = findVerifiedKeep(keep.getKeepId(), keep.getQrCode().getQrCodeId());
    if(findKeep.getTarget() != null)
    keepRepository.delete(findKeep);
  }

  /**
   * 자재 리스트 조회(사용자 입장)
   */
  @Transactional(readOnly = true)
  public Page<Keep> getUserKeepList(long businessId, long qrCodeId, int page, int size) {
    return keepRepository.findAllByBusinessIdAndQrCodeId(businessId, qrCodeId,
            PageRequest.of(page, size, Sort.by("CREATED_AT").descending()));
  }
}