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
}
