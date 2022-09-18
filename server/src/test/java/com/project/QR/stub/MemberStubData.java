package com.project.QR.stub;

import com.project.QR.member.dto.MemberRequestDto;
import com.project.QR.member.dto.MemberResponseDto;
import com.project.QR.member.entity.AuthProvider;
import com.project.QR.member.entity.Member;
import com.project.QR.sector.entity.Sector;

import java.util.List;
import java.util.stream.Collectors;

public class MemberStubData {
  private static List<Sector> sectorList = SectorStubData.sectorList();
  public static Member member() {
    Member member = new Member();
    member.setMemberId(1L);
    member.setEmail("hgd@gmail.com");
    member.setProvider(AuthProvider.local);
    member.setPassword("1234");
    member.setName("hgd");
    member.setSector(sectorList.get(1));
    member.setBusinessName("사업명");
    member.setPhone("01012345678");
    member.setRole("ROLE_RESERVATION");
    return member;
  }

  public static MemberRequestDto.CreateMemberDto createMemberDto() {
    Member member = member();
    return MemberRequestDto.CreateMemberDto.builder()
      .email(member.getEmail())
      .businessName(member.getBusinessName())
      .name(member().getName())
      .password(member().getPassword())
      .phone(member.getPhone())
      .role("RESERVATION")
      .sectorId(member.getSector().getSectorId())
      .build();
  }

  public static MemberRequestDto.EmailDto emailDto(String email) {
    return MemberRequestDto.EmailDto.builder()
      .email(email)
      .build();
  }

  public static MemberRequestDto.LoginDto loginDto() {
    Member member = member();
    return MemberRequestDto.LoginDto.builder()
      .email(member.getEmail())
      .password(member.getPassword())
      .build();
  }

  public static MemberRequestDto.OAuthUpdateDto oAuthUpdateDto() {
    Member member = member();
    return MemberRequestDto.OAuthUpdateDto.builder()
      .businessName(member.getBusinessName())
      .email(member.getEmail())
      .name(member.getName())
      .phone(member.getPhone())
      .sectorId(member.getSector().getSectorId())
      .service("reservation")
      .build();
  }

  public static MemberResponseDto.MemberInfoDto memberInfoDto() {
    Member member = member();
    return MemberResponseDto.MemberInfoDto.builder()
      .profileImg(member.getProfileImg())
      .service(member.getRoleList().stream()
        .map(role -> role.substring(5))
        .collect(Collectors.toList())
      )
      .phone(member.getPhone())
      .sector(member.getSector())
      .name(member.getName())
      .email(member.getEmail())
      .businessName(member.getBusinessName())
      .build();
  }

  public static MemberRequestDto.UpdateMemberDto updateMemberDto() {
    Member member = member();
    return MemberRequestDto.UpdateMemberDto.builder()
      .businessName("changeBusinessName")
      .email(member.getEmail())
      .name("changeName")
      .password("1234")
      .phone("01087654321")
      .sectorId(3)
      .profileImg("profile-img-url")
      .build();
  }
}
