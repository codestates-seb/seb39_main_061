package com.project.QR.security;

import com.project.QR.member.entity.Member;
import com.project.QR.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {
  private final MemberRepository memberRepository;

  @Override
  public MemberDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Member member = memberRepository.findByEmail(email).orElse(null);
    return MemberDetails.create(member);
  }
}
