package com.project.QR.auth.service;

import com.project.QR.exception.BusinessLogicException;
import com.project.QR.exception.ExceptionCode;
import com.project.QR.member.entity.Member;
import com.project.QR.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class AuthService {
  private final MemberRepository memberRepository;

  public Member createMember(Member member) {
    verifyExistsEmail(member.getEmail());
    System.out.println(member.getPhone());
    return memberRepository.save(member);
  }

  @Transactional(readOnly = true)
  public boolean findExistsEmail(String email) {
    return memberRepository.existsByEmail(email);
  }

  @Transactional(readOnly = true)
  public void verifyExistsEmail(String email) {
    if(findExistsEmail(email)) {
      throw new BusinessLogicException(ExceptionCode.MEMBER_ALREADY_EXISTS);
    }
  }
}
