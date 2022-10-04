package com.project.QR.member.mapper;

<<<<<<< HEAD
=======
import com.project.QR.business.dto.BusinessResponseDto;
import com.project.QR.business.entity.Business;
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
import com.project.QR.member.dto.MemberRequestDto;
import com.project.QR.member.dto.MemberResponseDto;
import com.project.QR.member.entity.AuthProvider;
import com.project.QR.member.entity.Member;
<<<<<<< HEAD
import com.project.QR.sector.entity.Sector;
=======
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MemberMapper {

  default Member createMemberDtoToMember(MemberRequestDto.CreateMemberDto createMemberDto) {
<<<<<<< HEAD
    Sector sector = new Sector();
    sector.setSectorId(createMemberDto.getSectorId());
=======
    Business business = new Business();
    business.setName(createMemberDto.getBusinessName());
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
    return Member.builder()
      .phone(createMemberDto.getPhone())
      .name(createMemberDto.getName())
      .email(createMemberDto.getEmail())
      .password(createMemberDto.getPassword())
      .provider(AuthProvider.local)
<<<<<<< HEAD
      .role("ROLE_GUEST")
      .joinRole("ROLE_"+createMemberDto.getRole().toUpperCase())
      .businessName(createMemberDto.getBusinessName())
      .sector(sector)
=======
      .business(business)
      .role("ROLE_GUEST")
      .joinRole("ROLE_"+createMemberDto.getRole().toUpperCase())
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
      .build();
  }

  Member loginDtoToMember(MemberRequestDto.LoginDto loginDto);

  default Member updateMemberDtoToMember(MemberRequestDto.UpdateMemberDto updateMemberDto) {
<<<<<<< HEAD
    Sector sector = new Sector();
    sector.setSectorId(updateMemberDto.getSectorId());
    return Member.builder()
      .email(updateMemberDto.getEmail())
      .name(updateMemberDto.getName())
      .businessName(updateMemberDto.getBusinessName())
      .sector(sector)
=======
    return Member.builder()
      .email(updateMemberDto.getEmail())
      .name(updateMemberDto.getName())
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
      .password(updateMemberDto.getPassword())
      .phone(updateMemberDto.getPhone())
      .role(updateMemberDto.getService().stream()
        .map(role -> "ROLE_"+role)
        .collect(Collectors.joining(","))
      )
      .build();
  }

  default Member oAuthUpdateDtoToMember(MemberRequestDto.OAuthUpdateDto oAuthUpdateDto) {
<<<<<<< HEAD
    Sector sector = new Sector();
    sector.setSectorId(oAuthUpdateDto.getSectorId());
    return Member.builder()
      .email(oAuthUpdateDto.getEmail())
      .name(oAuthUpdateDto.getName())
      .businessName(oAuthUpdateDto.getBusinessName())
      .sector(sector)
=======
    Business business = new Business();
    business.setName(oAuthUpdateDto.getBusinessName());
    business.setIntroduction(oAuthUpdateDto.getBusinessIntroduction());
    return Member.builder()
      .email(oAuthUpdateDto.getEmail())
      .name(oAuthUpdateDto.getName())
      .business(business)
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
      .phone(oAuthUpdateDto.getPhone())
      .role("ROLE_"+oAuthUpdateDto.getService().toUpperCase())
      .build();
  }

  default MemberResponseDto.MemberInfoDto memberToMemberInfoDto(Member member) {
<<<<<<< HEAD
    return MemberResponseDto.MemberInfoDto.builder()
      .businessName(member.getBusinessName())
=======

    return MemberResponseDto.MemberInfoDto.builder()
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
      .email(member.getEmail())
      .profileImg(member.getProfileImg())
      .name(member.getName())
      .phone(member.getPhone())
      .service(member.getRoleList().stream()
        .map(role -> role.substring(5))
        .collect(Collectors.toList())
      )
<<<<<<< HEAD
      .sector(member.getSector())
=======
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
      .build();
  }
}
