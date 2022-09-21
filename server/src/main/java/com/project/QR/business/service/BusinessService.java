package com.project.QR.business.service;

import com.project.QR.business.entity.Business;
import com.project.QR.business.repository.BusinessRepository;
import com.project.QR.exception.BusinessLogicException;
import com.project.QR.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class BusinessService {
  private final BusinessRepository businessRepository;

  /**
   * Business 조회
   */
  @Transactional(readOnly = true)
  public Business getBusiness(long businessId, long memberId) {
    return findVerifiedBusiness(businessId, memberId);
  }

  /**
   * 유효한 Business 조회
   */
  @Transactional(readOnly = true)
  private Business findVerifiedBusiness(long businessId, long memberId) {
    Optional<Business> optionalBusiness = businessRepository.findByIdAndMemberId(businessId, memberId);
    return optionalBusiness.orElseThrow(() -> new BusinessLogicException(ExceptionCode.BUSINESS_NOT_FOUND));
  }

  /**
   * Business 저장
   */
  public Business createBusiness(Business business) {
    return businessRepository.save(business);
  }
}
