package com.project.QR.security;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.QR.member.entity.Member;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor
public class MemberDetails implements UserDetails, OAuth2User, Serializable {
  private long memberId;
  private String name;
  private String email;
  private List<String> roles;
  private Map<String, Object> attributes;

  public MemberDetails(long memberId,
                       String email,
                       String name,
                       List<String> roleList) {
    this.memberId = memberId;
    this.name = name;
    this.email = email;
    this.roles = roleList;
  }

  public static MemberDetails create(Member member) {
    return new MemberDetails(member.getMemberId(), member.getEmail(), member.getName(), member.getRoleList());
  }

  public static MemberDetails create(Member member, Map<String, Object> attributes) {

    MemberDetails memberDetails = new MemberDetails(member.getMemberId(), member.getEmail(), member.getName(), member.getRoleList());
    memberDetails.setAttributes(attributes);
    return memberDetails;
  }

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles.stream().map(SimpleGrantedAuthority::new)
      .collect(Collectors.toList());
  }

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @Override
  public String getUsername() {
    return this.email;
  }

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @Override
  public String getPassword() {
    return null;
  }

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @Override
  public boolean isEnabled() {
    return true;
  }

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  // OAuth2User Override
  @Override
  public String getName() {
    return this.name;
  }

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  public void setAttributes(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  public String getRole() {
    return String.join(",", roles);
  }

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  public Member getMember() {
    return Member.builder()
      .email(this.email)
      .memberId(this.memberId)
      .name(this.name)
      .role(getRole())
      .build();
  }
}
