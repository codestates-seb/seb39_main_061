package com.project.QR.member.controller;

import com.project.QR.dto.SingleResponseDto;
import com.project.QR.dto.SingleResponseWithMessageDto;
import com.project.QR.member.dto.MemberRequestDto;
<<<<<<< HEAD
=======
import com.project.QR.member.dto.MemberResponseDto;
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
import com.project.QR.member.entity.Member;
import com.project.QR.member.mapper.MemberMapper;
import com.project.QR.member.service.MemberService;
import com.project.QR.security.MemberDetails;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

<<<<<<< HEAD
@Valid
=======
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {
  private final MemberService memberService;
  private final MemberMapper mapper;

  /**
   * 회원 정보 조회 api
   */
  @GetMapping("/profile")
  public ResponseEntity getMember(@AuthenticationPrincipal MemberDetails memberDetails) {
    Member member = memberService.getMember(memberDetails.getUsername());
<<<<<<< HEAD
    return new ResponseEntity(new SingleResponseWithMessageDto<>(mapper.memberToMemberInfoDto(member),
=======

    return new ResponseEntity<>(new SingleResponseWithMessageDto<>(mapper.memberToMemberInfoDto(member),
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
      "SUCCESS"),
      HttpStatus.OK);
  }

  /**
   * 회원 정보 변경 api
   */
  @PostMapping(value = "/profile", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity updateMember(@AuthenticationPrincipal MemberDetails memberDetails,
                                     @Valid @RequestPart(name = "data") MemberRequestDto.UpdateMemberDto updateMemberDto,
                                     @RequestPart(name = "file", required = false) MultipartFile multipartFile) {
    updateMemberDto.setEmail(memberDetails.getUsername());
    Member member = memberService.updateMember(mapper.updateMemberDtoToMember(updateMemberDto), multipartFile);
<<<<<<< HEAD
    return new ResponseEntity(new SingleResponseWithMessageDto<>(mapper.memberToMemberInfoDto(member),
=======
    MemberResponseDto.MemberInfoDto response = mapper.memberToMemberInfoDto(member);

    return new ResponseEntity<>(new SingleResponseWithMessageDto<>(response,
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
      "SUCCESS"),
      HttpStatus.OK);
  }

  /**
   * 로그아웃 api
   */
<<<<<<< HEAD
  public ResponseEntity logout(@AuthenticationPrincipal MemberDetails memberDetails) {
    memberService.logout(memberDetails.getUsername());
    return new ResponseEntity(new SingleResponseDto<>("BYE"), HttpStatus.OK);
=======
  @DeleteMapping("/logout")
  public ResponseEntity logout(@AuthenticationPrincipal MemberDetails memberDetails) {
    memberService.logout(memberDetails.getUsername());

    return new ResponseEntity<>(new SingleResponseDto<>("BYE"), HttpStatus.OK);
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
  }
}
