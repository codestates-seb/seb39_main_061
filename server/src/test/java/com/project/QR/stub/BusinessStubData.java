package com.project.QR.stub;

import com.project.QR.business.dto.BusinessRequestDto;
import com.project.QR.business.dto.BusinessResponseDto;
import com.project.QR.business.entity.Business;
import com.project.QR.member.entity.Member;

public class BusinessStubData {
  private static final Member member = MemberStubData.member();

  public static Business business() {
    Business business = new Business();
    business.setBusinessId(1L);
    business.setName("business");
    business.setIntroduction("introduction");
    return business;
  }

  public static BusinessResponseDto.BusinessInfoDto businessInfoDto(Business business) {
    return BusinessResponseDto.BusinessInfoDto.builder()
      .businessId(business.getBusinessId())
      .name(business.getName())
      .introduction(business.getIntroduction())
      .address(business.getAddress())
      .holiday(business.getHoliday())
      .lat(business.getLat())
      .lon(business.getLon())
      .openTime(business.getOpenTime())
      .phone(business.getPhone())
      .build();
  }

  public static Business business(long businessId, String name, String introduction, String holiday,
                                         String openTime, String address, double lon, double lat, String phone) {
    Business business = new Business();
    business.setBusinessId(businessId);
    business.setName(name);
    business.setIntroduction(introduction);
    business.setHoliday(holiday);
    business.setOpenTime(openTime);
    business.setAddress(address);
    business.setLat(lat);
    business.setLon(lon);
    business.setPhone(phone);
    business.setMember(member);
    return business;
  }

  public static BusinessRequestDto.UpdateBusinessDto updateBusinessDto(Business business) {
    return BusinessRequestDto.UpdateBusinessDto.builder()
      .address(business.getAddress())
      .lat(business.getLat())
      .lon(business.getLon())
      .memberId(1L)
      .holiday(business.getHoliday())
      .introduction(business.getIntroduction())
      .openTime(business.getOpenTime())
      .phone(business.getPhone())
      .name(business.getName())
      .build();
  }
}
