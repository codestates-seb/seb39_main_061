package com.project.QR.security;


import com.project.QR.member.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class MemberDetails implements UserDetails, OAuth2User {
  private Member member;
  private Map<String, Object> attributes;

  public MemberDetails(String email, String role) {
    this.member = Member.builder()
      .email(email)
      .role(role)
      .build();
  }

  public static MemberDetails create(Member member) {
    return new MemberDetails(
      member.getEmail(),
      member.getRole()
    );
  }

  public static MemberDetails create(Member member, Map<String, Object> attributes) {
    MemberDetails memberDetails = MemberDetails.create(member);
    memberDetails.setAttributes(attributes);
    return memberDetails;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    this.member.getRoleList().forEach(n -> {
      authorities.add(() -> n);
    });
    return authorities;
  }

  public Member getMember() {
    return this.member;
  }

  @Override
  public String getUsername() {
    return this.member.getEmail();
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  // OAuth2User Override
  @Override
  public String getName() {
    return member.getName();
  }

  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  public void setAttributes(Map<String, Object> attributes) {
    this.attributes = attributes;
  }
}

