package com.project.QR.business.mapper;

import com.project.QR.business.dto.BusinessRequestDto;
import com.project.QR.business.dto.BusinessResponseDto;
import com.project.QR.business.entity.Business;
import com.project.QR.member.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BusinessMapper {
  default Business updateBusinessDtoToBusiness(BusinessRequestDto.UpdateBusinessDto updateBusinessDto) {
    Member member = new Member();
    member.setMemberId(updateBusinessDto.getMemberId());
    Business business = new Business();
    business.setMember(member);
    business.setName(updateBusinessDto.getName());
    business.setIntroduction(updateBusinessDto.getIntroduction());
    business.setAddress(updateBusinessDto.getAddress());
    business.setOpenTime(updateBusinessDto.getOpenTime());
    business.setHoliday(updateBusinessDto.getHoliday());
    business.setLat(updateBusinessDto.getLat());
    business.setLon(updateBusinessDto.getLon());
    business.setPhone(updateBusinessDto.getPhone());
    return business;
  }

  BusinessResponseDto.BusinessInfoDto businessToBusinessInfoDto(Business business);
}
