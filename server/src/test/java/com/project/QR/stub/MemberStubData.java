package com.project.QR.stub;

import com.project.QR.member.dto.MemberRequestDto;
import com.project.QR.member.dto.MemberResponseDto;
import com.project.QR.member.entity.AuthProvider;
import com.project.QR.member.entity.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.project.QR.stub.BusinessStubData.*;

public class MemberStubData {

  public static Member member() {
    Member member = new Member();
    member.setMemberId(1L);
    member.setEmail("hgd@gmail.com");
    member.setProvider(AuthProvider.local);
    member.setPassword("1234");
    member.setName("hgd");
    member.setBusiness(business());
    member.setPhone("010-1234-5678");
    member.setRole("ROLE_RESERVATION");
    return member;
  }

  public static MemberRequestDto.CreateMemberDto createMemberDto() {
    Member member = member();
    return MemberRequestDto.CreateMemberDto.builder()
      .email(member.getEmail())
      .businessName(member.getBusiness().getName())
      .name(member().getName())
      .password(member().getPassword())
      .phone(member.getPhone())
      .role("RESERVATION")
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
      .businessName(member.getBusiness().getName())
      .email(member.getEmail())
      .name(member.getName())
      .phone(member.getPhone())
      .build();
  }

  public static MemberResponseDto.MemberInfoDto memberInfoDto() {
    Member member = member();
    return MemberResponseDto.MemberInfoDto.builder()
      .profileImg(member.getProfileImg())
      .phone(member.getPhone())
      .name(member.getName())
      .email(member.getEmail())
      .build();
  }

  public static MemberResponseDto.MemberInfoDto memberInfoDto(Member member) {
    return MemberResponseDto.MemberInfoDto.builder()
      .profileImg(member.getProfileImg())
      .phone(member.getPhone())
      .name(member.getName())
      .email(member.getEmail())
      .profileImg(member.getProfileImg())
      .build();
  }

  public static MemberRequestDto.UpdateMemberDto updateMemberDto() {
    Member member = member();
    List<String> service = new ArrayList<>();
    service.add("reservation");
    service.add("keep");
    return MemberRequestDto.UpdateMemberDto.builder()
      .email(member.getEmail())
      .name("changeName")
      .password("1234")
      .phone("010-8765-4321")
      .profileImg("profile-img-url")
      .build();
  }

  public static Member updatedMember(MemberRequestDto.UpdateMemberDto updateMemberDto) {
    Member member = new Member();
    member.setMemberId(1L);
    member.setEmail(updateMemberDto.getEmail());
    member.setProvider(AuthProvider.local);
    member.setPassword(updateMemberDto().getPassword());
    member.setName(updateMemberDto().getName());
    member.setPhone(updateMemberDto.getPhone());
    member.setRole("ROLE_RESERVATION");
    member.setProfileImg(updateMemberDto.getProfileImg());
    return member;
  }


}
