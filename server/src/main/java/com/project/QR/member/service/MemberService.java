package com.project.QR.member.service;

import com.project.QR.exception.BusinessLogicException;
import com.project.QR.exception.ExceptionCode;
import com.project.QR.file.service.FileSystemStorageService;
import com.project.QR.member.entity.Member;
import com.project.QR.member.repository.MemberRepository;
import com.project.QR.util.CustomBeanUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class MemberService {
  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;
  private final CustomBeanUtils<Member> memberBeanUtils;
  private final FileSystemStorageService fileSystemStorageService;
  private final RedisTemplate<String, Object> redisTemplate;

  /**
   * 이메일로 회원 조회
   */
  public Member getMember(String email) {
    return findVerifiedMember(email);
  }

  /**
   * 회원 정보 변경
   */
  public Member updateMember(Member member, MultipartFile multipartFile) {
    Member findMember = findVerifiedMember(member.getEmail());
    member.setMemberId(findMember.getMemberId());
    if(!multipartFile.isEmpty()) {
      if(findMember.getProfileImg() != null)
        fileSystemStorageService.remove(findMember.getProfileImg());
      member.setProfileImg(fileSystemStorageService.store(multipartFile, String.format("%d/profile", member.getMemberId())));
    }
    if(member.getPassword() != null) {
      member.setPassword(passwordEncoder.encode(member.getPassword()));
    }

    Member updatingMember = memberBeanUtils.copyNonNullProperties(member, findMember);
    return memberRepository.save(updatingMember);
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
   * 로그아웃
   */
  public void logout(String email) {
    redisTemplate.opsForHash().delete(email);
  }
}
