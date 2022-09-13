package com.project.QR.auth.controller;

import com.project.QR.auth.service.AuthService;
import com.project.QR.dto.SingleResponseDto;
import com.project.QR.dto.SingleResponseWithMessageDto;
import com.project.QR.member.dto.MemberRequestDto;
import com.project.QR.member.entity.Member;
import com.project.QR.member.mapper.MemberMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
  private final AuthService authService;
  private final MemberMapper mapper;

  @PostMapping("/validation")
  public ResponseEntity validation(@Valid @RequestBody MemberRequestDto.EmailDto emailDto) {
    return new ResponseEntity(HttpStatus.OK);
  }

  @PostMapping("/signup")
  public ResponseEntity signUp(@Valid @RequestBody MemberRequestDto.CreateMemberDto createMemberDto) {
    authService.createMember(mapper.createMemberDtoToMember(createMemberDto));
    return new ResponseEntity(new SingleResponseDto<>("WELCOME"), HttpStatus.CREATED);
  }
}
