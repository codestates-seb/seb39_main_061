package com.project.QR.member.mapper;

import com.project.QR.business.dto.BusinessResponseDto;
import com.project.QR.business.entity.Business;
import com.project.QR.member.dto.MemberRequestDto;
import com.project.QR.member.dto.MemberResponseDto;
import com.project.QR.member.entity.AuthProvider;
import com.project.QR.member.entity.Member;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MemberMapper {

  default Member createMemberDtoToMember(MemberRequestDto.CreateMemberDto createMemberDto) {
    Business business = new Business();
    business.setName(createMemberDto.getBusinessName());
    return Member.builder()
      .phone(createMemberDto.getPhone())
      .name(createMemberDto.getName())
      .email(createMemberDto.getEmail())
      .password(createMemberDto.getPassword())
      .provider(AuthProvider.local)
      .business(business)
      .role("ROLE_GUEST")
      .joinRole("ROLE_"+createMemberDto.getRole().toUpperCase())
      .build();
  }

  Member loginDtoToMember(MemberRequestDto.LoginDto loginDto);

  default Member updateMemberDtoToMember(MemberRequestDto.UpdateMemberDto updateMemberDto) {
    return Member.builder()
      .email(updateMemberDto.getEmail())
      .name(updateMemberDto.getName())
      .password(updateMemberDto.getPassword())
      .phone(updateMemberDto.getPhone())
      .role(updateMemberDto.getService().stream()
        .map(role -> "ROLE_"+role)
        .collect(Collectors.joining(","))
      )
      .build();
  }

  default Member oAuthUpdateDtoToMember(MemberRequestDto.OAuthUpdateDto oAuthUpdateDto) {
    Business business = new Business();
    business.setName(oAuthUpdateDto.getBusinessName());
    business.setIntroduction(oAuthUpdateDto.getBusinessIntroduction());
    return Member.builder()
      .email(oAuthUpdateDto.getEmail())
      .name(oAuthUpdateDto.getName())
      .business(business)
      .phone(oAuthUpdateDto.getPhone())
      .role("ROLE_"+oAuthUpdateDto.getService().toUpperCase())
      .build();
  }

  default MemberResponseDto.MemberInfoDto memberToMemberInfoDto(Member member) {

    return MemberResponseDto.MemberInfoDto.builder()
      .email(member.getEmail())
      .profileImg(member.getProfileImg())
      .name(member.getName())
      .phone(member.getPhone())
      .service(member.getRoleList().stream()
        .map(role -> role.substring(5))
        .collect(Collectors.toList())
      )
      .build();
  }
}
