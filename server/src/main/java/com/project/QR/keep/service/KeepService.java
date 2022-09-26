package com.project.QR.keep.service;

import com.project.QR.business.service.BusinessService;
import com.project.QR.exception.BusinessLogicException;
import com.project.QR.exception.ExceptionCode;
import com.project.QR.keep.entity.Keep;
import com.project.QR.keep.repository.KeepRepository;
import com.project.QR.util.CustomBeanUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

import static org.springframework.transaction.annotation.Propagation.*;

@Service
@Transactional
@AllArgsConstructor
public class KeepService {
  private KeepRepository keepRepository;
  private CustomBeanUtils<Keep> beanUtils;
  private BusinessService businessService;

  /**
   * 자재 등록
   */
  @Transactional(propagation = REQUIRED)
  public Keep createKeep(Keep keep) {
    if (findExistKeep(keep.getQrCode().getQrCodeId(), keep.getInfo()) != 0)
      throw new BusinessLogicException(ExceptionCode.KEEP_ALREADY_EXISTS);
    return keepRepository.save(keep);
  }

  /**
   * 예약 리스트 조회(업주 입장)
   */
  public Page<Keep> getAdminKeepList(long businessId, long qrCodeId, Long memberId, int page, int size) {
    businessService.getBusiness(businessId, memberId);
    return keepRepository.findAllByBusinessIdAndQrCodeId(businessId, qrCodeId,
            PageRequest.of(page, size, Sort.by("CREATED_AT").descending()));
  }

  /**
   * 등록된 자재가 있는지 확인
   */
  @Transactional(readOnly = true)
  public long findExistKeep(long qrCodeId, String info) {

    return keepRepository.existsInfoAndQrCodeId(qrCodeId, info);
  }

  /**
   * 자재 정보 변경
   */
  public Keep updateKeep(Keep keep) {
    Keep findKeep = findVerifiedKeep(keep.getKeepId(), keep.getQrCode().getQrCodeId());
    if (!findKeep.getInfo().equals(keep.getInfo())) {
      throw new BusinessLogicException(ExceptionCode.INVALID_INFO);
    }
    Keep updatingKeep = beanUtils.copyNonNullProperties(keep, findKeep);
    return keepRepository.save(updatingKeep);
  }

  /**
   * 특정 자재 조회
   */
  public Keep findVerifiedKeep(long keepId, long qrCodeId) {
    Optional<Keep> optionalKeep = keepRepository.findByIdAndQrCodeId(keepId, qrCodeId);
    return optionalKeep.orElseThrow(() -> new BusinessLogicException(ExceptionCode.KEEP_NOT_FOUND));
  }

  /**
   * 자재 삭제
   */
  public void deleteKeep(Keep keep) {
    Keep findKeep = findVerifiedKeep(keep.getKeepId(), keep.getQrCode().getQrCodeId());
    if (!findKeep.getInfo().equals(keep.getInfo()))
      throw new BusinessLogicException(ExceptionCode.INVALID_INFO);
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