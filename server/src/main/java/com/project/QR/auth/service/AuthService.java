package com.project.QR.auth.service;

<<<<<<< HEAD
=======
import com.project.QR.business.entity.Business;
import com.project.QR.business.service.BusinessService;
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
import com.project.QR.dto.TokenDto;
import com.project.QR.exception.BusinessLogicException;
import com.project.QR.exception.ExceptionCode;
import com.project.QR.helper.event.MemberPasswordApplicationEvent;
import com.project.QR.helper.event.MemberRegistrationApplicationEvent;
<<<<<<< HEAD
import com.project.QR.member.entity.Member;
import com.project.QR.member.repository.MemberRepository;
=======
import com.project.QR.member.entity.EmailVerified;
import com.project.QR.member.entity.Member;
import com.project.QR.member.repository.MemberRepository;
import com.project.QR.member.service.MemberService;
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
import com.project.QR.security.MemberDetails;
import com.project.QR.security.jwt.TokenProvider;
import com.project.QR.util.CustomBeanUtils;
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
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class AuthService {
  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenProvider tokenProvider;
  private final RedisTemplate<String, Object> redisTemplate;
  private final ApplicationEventPublisher publisher;
<<<<<<< HEAD
  private final CustomBeanUtils<Member> beanUtils;
=======
  private final CustomBeanUtils<Member> memberBeanUtils;
  private final BusinessService businessService;
  private final MemberService memberService;
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d

  /**
   * 회원가입
   */
  public Member createMember(Member member) {
    verifyExistsEmail(member.getEmail());
    member.setPassword(passwordEncoder.encode(member.getPassword()));
    member.setVerifiedCode(UUID.randomUUID().toString());
<<<<<<< HEAD
    Member savedMember = memberRepository.save(member);
=======
    member.setEmailVerified(EmailVerified.N);
    Member savedMember = memberRepository.save(member);
    member.getBusiness().setMember(savedMember);
    businessService.createBusiness(member.getBusiness());
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
    publisher.publishEvent(new MemberRegistrationApplicationEvent(this, savedMember));
    return savedMember;
  }

  /**
   * 로그인
   */
  public TokenDto.TokenInfoDto loginMember(Member member, HttpServletResponse response) {
<<<<<<< HEAD
    Member findMember = findVerifiedMember(member.getEmail());
    if(!passwordEncoder.matches(member.getPassword(), findMember.getPassword())) {
      throw new BusinessLogicException(ExceptionCode.MEMBER_INFO_INCORRECT);
    }
    if(!findMember.getEmailVerified()) {
=======
    Member findMember = memberService.getMember(member.getEmail());
    if(!passwordEncoder.matches(member.getPassword(), findMember.getPassword())) {
      throw new BusinessLogicException(ExceptionCode.MEMBER_INFO_INCORRECT);
    }
    if(findMember.getEmailVerified().equals(EmailVerified.N)) {
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
      throw new BusinessLogicException(ExceptionCode.EMAIL_VALIDATION_NEED);
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

<<<<<<< HEAD
  /**
   * 이메일로 회원 찾기
   */
  @Transactional(readOnly = true)
  public Member findVerifiedMember(String email) {
    Optional<Member> optionalMember = memberRepository.findByEmail(email);
    return optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
  }
=======

>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d

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

  /**
   * 회원 탈퇴
   */
  public void deleteMember(long memberId) {
    memberRepository.deleteById(memberId);
  }

  /**
   * 이메일 인증
   */
  public void validation(String email, String code) {
<<<<<<< HEAD
    Member findMember = findVerifiedMember(email);
=======
    Member findMember = memberService.getMember(email);
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
    if(!findMember.getVerifiedCode().equals(code)) {
      throw new BusinessLogicException(ExceptionCode.VALIDATION_CODE_INCORRECT);
    }
    findMember.setRole(findMember.getJoinRole());
<<<<<<< HEAD
    findMember.setEmailVerified(true);
=======
    findMember.setEmailVerified(EmailVerified.Y);
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
    memberRepository.save(findMember);
  }

  /**
   * oauth2 로그인 이후 추가 정보 기입
   */
<<<<<<< HEAD
  public Member updateMember(Member member) {
    Member findMember = findVerifiedMember(member.getEmail());
    Member updatingMember = beanUtils.copyNonNullProperties(member, findMember);
    updatingMember.setEmailVerified(true);
    return memberRepository.save(updatingMember);
=======
  public TokenDto.TokenInfoDto updateMember(Member member, HttpServletResponse response) {
    Member findMember = memberService.getMember(member.getEmail());
    Member updatingMember = memberBeanUtils.copyNonNullProperties(member, findMember);
    updatingMember.setEmailVerified(EmailVerified.Y);
    updatingMember.setMemberId(findMember.getMemberId());
    Member savedMember = memberRepository.save(updatingMember);
    savedMember.getBusiness().setMember(savedMember);
    businessService.createBusiness(savedMember.getBusiness());
    return tokenProvider.createToken(savedMember, response);
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
  }

  /**
   * 비밀번호 재발급
   */
  public void reIssuePassword(String email) {
<<<<<<< HEAD
    Member member = findVerifiedMember(email);
=======
    Member member = memberService.getMember(email);
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
    String password = UUID.randomUUID().toString().substring(0,10);
    member.setPassword(passwordEncoder.encode(password));
    memberRepository.save(member);
    publisher.publishEvent(new MemberPasswordApplicationEvent(this, email, member.getName(), password));
  }
}
