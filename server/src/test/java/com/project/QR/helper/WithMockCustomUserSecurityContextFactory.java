package com.clone.stackoverflow.helper;


import com.clone.stackoverflow.auth.PrincipalDetails;
import com.clone.stackoverflow.member.entity.Member;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

  @Override
  public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    Member member = getMember(customUser);
    PrincipalDetails principal = new PrincipalDetails(member);
    Authentication auth =
      new UsernamePasswordAuthenticationToken(principal, customUser.password(), principal.getAuthorities());
    context.setAuthentication(auth);
    return context;
  }

  private Member getMember(WithMockCustomUser customUser) {
    Member member = new Member();
    member.setMemberId(customUser.memberId());
    member.setEmail(customUser.email());
    member.setPassword(customUser.password());
    member.setDisplayName(customUser.displayName());
    member.setProvider(customUser.provider());
    member.setRole(customUser.role());
    return member;
  }
}
