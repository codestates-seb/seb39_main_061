package com.project.QR.member.mapper;

import com.project.QR.member.dto.MemberRequestDto;
import com.project.QR.member.entity.AuthProvider;
import com.project.QR.member.entity.Authority;
import com.project.QR.member.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {

  default Member createMemberDtoToMember(MemberRequestDto.CreateMemberDto createMemberDto) {
    return Member.builder()
      .phone(createMemberDto.getPhone())
      .name(createMemberDto.getName())
      .email(createMemberDto.getEmail())
      .password(createMemberDto.getPassword())
      .provider(AuthProvider.local)
      .authority(Authority.ROLE_GUEST)
      .build();
  }
}
