package com.project.QR.stub;

import com.project.QR.business.dto.BusinessResponseDto;
import com.project.QR.business.entity.Business;

public class BusinessStubData {
  public static Business business() {
    Business business = new Business();
    business.setBusinessId(1L);
    business.setName("business");
    business.setIntroduction("introduction");
    return business;
  }

  public static BusinessResponseDto.BusinessInfoDto businessInfoDto() {
    Business business = business();
    return BusinessResponseDto.BusinessInfoDto.builder()
      .businessId(business().getBusinessId())
      .name(business.getName())
      .introduction(business.getIntroduction())
      .build();
  }

  public static Business updatedBusiness() {
    Business business = new Business();
    business.setBusinessId(1L);
    business.setName("changeBusinessName");
    business.setIntroduction("change introduction");
    return business;
  }
}
