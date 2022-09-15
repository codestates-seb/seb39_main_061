package com.project.QR.stub;

import com.project.QR.member.dto.MemberRequestDto;
import com.project.QR.member.entity.AuthProvider;
import com.project.QR.member.entity.Member;
import com.project.QR.sector.entity.Sector;

public class MemberStubData {
  public static Member member() {
    Sector sector = new Sector();
    sector.setSectorId(1L);
    sector.setName("업종 1");
    Member member = new Member();
    member.setMemberId(1L);
    member.setEmail("hgd@gmail.com");
    member.setProvider(AuthProvider.local);
    member.setPassword("1234");
    member.setName("hgd");
    member.setSector(sector);
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
      .role(member.getRole())
      .sectorId(member.getSector().getSectorId())
      .build();
  }
}
