package com.project.QR.security;

<<<<<<< HEAD
import com.project.QR.member.entity.Member;
import com.project.QR.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
=======
import com.project.QR.exception.BusinessLogicException;
import com.project.QR.exception.ExceptionCode;
import com.project.QR.member.entity.Member;
import com.project.QR.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
<<<<<<< HEAD
=======
import java.util.ArrayList;
import java.util.Collection;
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d

@Service
@Transactional
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {
  private final MemberRepository memberRepository;

  @Override
<<<<<<< HEAD
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Member member = memberRepository.findByEmail(email).orElse(null);
=======
  @Cacheable(key = "#email", value = "loadUserByUsername")
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Member member = memberRepository.findByEmail(email)
      .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
    return MemberDetails.create(member);
  }
}
