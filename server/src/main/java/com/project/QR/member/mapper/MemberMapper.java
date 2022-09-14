package com.project.QR.member.mapper;

import com.project.QR.member.dto.MemberRequestDto;
import com.project.QR.member.entity.AuthProvider;
import com.project.QR.member.entity.Member;
import com.project.QR.sector.entity.Sector;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {

  default Member createMemberDtoToMember(MemberRequestDto.CreateMemberDto createMemberDto) {
    Sector sector = new Sector();
    sector.setSectorId(createMemberDto.getSectorId());
    return Member.builder()
      .phone(createMemberDto.getPhone())
      .name(createMemberDto.getName())
      .email(createMemberDto.getEmail())
      .password(createMemberDto.getPassword())
      .provider(AuthProvider.local)
      .role("ROLE_"+createMemberDto.getRole().toUpperCase())
      .sector(sector)
      .build();
  }

  Member loginDtoToMember(MemberRequestDto.LoginDto loginDto);

  default Member updateMemberDtoToMember(MemberRequestDto.UpdateMemberDto updateMemberDto) {
    Sector sector = new Sector();
    sector.setSectorId(updateMemberDto.getSectorId());
    return Member.builder()
      .email(updateMemberDto.getEmail())
      .name(updateMemberDto.getName())
      .businessName(updateMemberDto.getBusinessName())
      .sector(sector)
      .password(updateMemberDto.getPassword())
      .phone(updateMemberDto.getPhone())
      .role(updateMemberDto.getService().stream().reduce((sec1, sec2) -> {
        return "ROLE_"+sec1.toUpperCase() + "," + "ROLE_"+sec2.toUpperCase();
      }).toString())
      .build();
  }

  default Member oAuthUpdateDtoToMember(MemberRequestDto.OAuthUpdateDto oAuthUpdateDto) {
    Sector sector = new Sector();
    sector.setSectorId(oAuthUpdateDto.getSectorId());
    return Member.builder()
      .email(oAuthUpdateDto.getEmail())
      .name(oAuthUpdateDto.getName())
      .businessName(oAuthUpdateDto.getBusinessName())
      .sector(sector)
      .phone(oAuthUpdateDto.getPhone())
      .role("ROLE_"+oAuthUpdateDto.getService().toUpperCase())
      .build();
  }
}
