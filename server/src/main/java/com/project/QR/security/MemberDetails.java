package com.project.QR.security;


import com.project.QR.member.entity.Member;
import org.apache.catalina.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MemberDetails implements UserDetails, OAuth2User {
  private long memberId;
  private String name;
  private String email;
  private Collection<? extends GrantedAuthority> roles;
  private Map<String, Object> attributes;

  public MemberDetails(long memberId,
                       String email,
                       String name,
                       List<String> roleList) {
    Collection<GrantedAuthority> roles = new ArrayList<>();
    roleList.stream().forEach(n -> {
      roles.add(() -> n);
    });
    this.memberId = memberId;
    this.name = name;
    this.email = email;
    this.roles = roles;
  }

  public static MemberDetails create(Member member) {
   return new MemberDetails(member.getMemberId(), member.getEmail(), member.getName(), member.getRoleList());
  }

  public static MemberDetails create(Member member, Map<String, Object> attributes) {

    MemberDetails memberDetails = new MemberDetails(member.getMemberId(), member.getEmail(), member.getName(), member.getRoleList());
    memberDetails.setAttributes(attributes);
    return memberDetails;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles;
  }

  @Override
  public String getUsername() {
    return this.email;
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
    return this.name;
  }

  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  public void setAttributes(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  public String getRole() {
    System.out.println(this.roles.toString());
    return this.roles.toString();
  }

  public Member getMember() {
    return Member.builder()
      .email(this.email)
      .memberId(this.memberId)
      .name(this.name)
      .build();
  }
}

