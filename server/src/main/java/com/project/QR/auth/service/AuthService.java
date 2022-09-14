package com.project.QR.auth.service;

import com.project.QR.dto.TokenDto;
import com.project.QR.exception.BusinessLogicException;
import com.project.QR.exception.ExceptionCode;
import com.project.QR.helper.event.MemberRegistrationApplicationEvent;
import com.project.QR.member.entity.Member;
import com.project.QR.member.repository.MemberRepository;
import com.project.QR.security.MemberDetails;
import com.project.QR.security.jwt.TokenProvider;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpResponse;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class AuthService {
  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenProvider tokenProvider;
  private final RedisTemplate<String, Object> redisTemplate;
  private final ApplicationEventPublisher publisher;

  public Member createMember(Member member) {
    verifyExistsEmail(member.getEmail());
    member.setPassword(passwordEncoder.encode(member.getPassword()));
    Member savedMember = memberRepository.save(member);
    // TO-DO : 이메일 인증 로직 처리
    publisher.publishEvent(new MemberRegistrationApplicationEvent(this, savedMember));
    return savedMember;
  }

  /**
   * 로그인 api
   */
  public TokenDto.TokenInfoDto loginMember(Member member, HttpServletResponse response) {
    Member findMember = findVerifiedMember(member.getEmail());
    if(!passwordEncoder.matches(member.getPassword(), findMember.getPassword())) {
      throw new BusinessLogicException(ExceptionCode.MEMBER_INFO_INCORRECT);
    }
    return tokenProvider.createToken(findMember, response);
  }

  /**
   * 이메일 존재 여부 확인 여부 반환
   */
  @Transactional(readOnly = true)
  public boolean findExistsEmail(String email) {
    return memberRepository.existsByEmail(email);
  }

  /**
   * 이메일 존재 여부에 따른 예외처리
   */
  @Transactional(readOnly = true)
  public void verifyExistsEmail(String email) {
    if(findExistsEmail(email)) {
      throw new BusinessLogicException(ExceptionCode.MEMBER_ALREADY_EXISTS);
    }
  }

  /**
   * 이메일로 회원 찾기
   */
  @Transactional(readOnly = true)
  public Member findVerifiedMember(String email) {
    Optional<Member> optionalMember = memberRepository.findByEmail(email);
    return optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
  }

  /**
   * 토큰 재발급
   * redis에 저장되어 있는 refresh 토큰과 쿠키로 받은 refresh 토큰 비교
   */
  public TokenDto.TokenInfoDto reIssue(String accessToken, String refreshToken) {
    Authentication authentication = tokenProvider.getAuthentication(accessToken);
    String redisRefreshToken
      = (String) redisTemplate.opsForValue().get(authentication.getName());
    if(StringUtils.hasText(redisRefreshToken)) {
      if(redisRefreshToken.equals(refreshToken)) {
        return tokenProvider.createAccessToken(authentication);
      }
      else {
        throw new BusinessLogicException(ExceptionCode.TOKEN_IS_INVALID);
      }
    } else {
      throw new BusinessLogicException(ExceptionCode.REFRESH_TOKEN_IS_EXPIRED);
    }
  }


  public void deleteMember(long memberId) {
  }
}
