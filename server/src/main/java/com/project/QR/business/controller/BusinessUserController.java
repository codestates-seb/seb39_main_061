package com.project.QR.business.controller;

import com.project.QR.business.entity.Business;
import com.project.QR.business.mapper.BusinessMapper;
import com.project.QR.business.service.BusinessService;
import com.project.QR.dto.SingleResponseWithMessageDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/business")
@AllArgsConstructor
public class BusinessUserController {
  private final BusinessService businessService;
  private final BusinessMapper mapper;

  /**
   * 매장 정보 조회 API
   */
  @GetMapping("/{business-id}")
  public ResponseEntity getBusiness(@Positive @PathVariable("business-id") long businessId) {
    Business business = businessService.getBusinessByBusinessId(businessId);

    return new ResponseEntity<>(new SingleResponseWithMessageDto<>(mapper.businessToBusinessInfoDto(business),
      "SUCCESS"),
      HttpStatus.OK);
  }
}
