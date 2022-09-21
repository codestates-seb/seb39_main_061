package com.project.QR.member.service;

import com.project.QR.auth.service.AuthService;
import com.project.QR.business.entity.Business;
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

@Service
@Transactional
@AllArgsConstructor
public class MemberService {
  private final MemberRepository memberRepository;
  private final AuthService authService;
  private final PasswordEncoder passwordEncoder;
  private final CustomBeanUtils<Member> MemberBeanUtils;
  private final CustomBeanUtils<Business> BusinessBeanUtils;
  private final FileSystemStorageService fileSystemStorageService;
  private final RedisTemplate<String, Object> redisTemplate;

  /**
   * 이메일로 회원 조회
   */
  public Member getMember(String email) {
    return authService.findVerifiedMember(email);
  }

  /**
   * 회원 정보 변경
   */
  public Member updateMember(Member member, MultipartFile multipartFile) {
    Member findMember = authService.findVerifiedMember(member.getEmail());
    member.setMemberId(findMember.getMemberId());
    if(!multipartFile.isEmpty()) {
      if(findMember.getProfileImg() != null)
        fileSystemStorageService.remove(findMember.getProfileImg());
      member.setProfileImg(fileSystemStorageService.store(multipartFile, String.format("%d/profile", member.getMemberId())));
    }
    if(member.getPassword() != null) {
      member.setPassword(passwordEncoder.encode(member.getPassword()));
    }
    Business updatingBusiness = BusinessBeanUtils.copyNonNullProperties(member.getBusiness(), findMember.getBusiness());
    member.setBusiness(updatingBusiness);
    Member updatingMember = MemberBeanUtils.copyNonNullProperties(member, findMember);
    return memberRepository.save(updatingMember);
  }


  /**
   * 로그아웃
   */
  public void logout(String email) {
    redisTemplate.opsForHash().delete(email);
  }
}
