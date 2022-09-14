package com.project.QR.auth.controller;

import com.project.QR.auth.service.AuthService;
import com.project.QR.dto.ExistDto;
import com.project.QR.dto.SingleResponseDto;
import com.project.QR.dto.SingleResponseWithMessageDto;
import com.project.QR.dto.TokenDto;
import com.project.QR.member.dto.MemberRequestDto;
import com.project.QR.member.entity.Member;
import com.project.QR.member.mapper.MemberMapper;
import com.project.QR.security.MemberDetails;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.websocket.server.PathParam;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
  private final AuthService authService;
  private final MemberMapper mapper;

  /**
   * 이메일 중복검사 api
   */
  @PostMapping("/email-validation")
  public ResponseEntity emailValidation(@Valid @RequestBody MemberRequestDto.EmailDto emailDto) {
    boolean exist = authService.findExistsEmail(emailDto.getEmail());
    return new ResponseEntity(new SingleResponseWithMessageDto<>(new ExistDto(exist),
      "SUCCESS"),
      HttpStatus.OK);
  }

  /**
   * 회원가입 api
   */
  @PostMapping("/signup")
  public ResponseEntity signUp(@Valid @RequestBody MemberRequestDto.CreateMemberDto createMemberDto) {
    authService.createMember(mapper.createMemberDtoToMember(createMemberDto));
    return new ResponseEntity(new SingleResponseDto<>("WELCOME"), HttpStatus.CREATED);
  }

  /**
   * 로그인 api
   */
  @PostMapping("/login")
  public ResponseEntity login(@Valid @RequestBody MemberRequestDto.LoginDto loginDto,
                              HttpServletResponse response) {
    TokenDto.TokenInfoDto tokenInfoDto = authService.loginMember(mapper.loginDtoToMember(loginDto), response);
    return new ResponseEntity(new SingleResponseWithMessageDto<>(tokenInfoDto,
      "WELCOME"),
      HttpStatus.OK);
  }

  /**
   * accessToken 재발급 api
   */
  @PostMapping("/reissue")
  public ResponseEntity reIssue(@Valid @RequestBody TokenDto.ReIssueDto reIssueDto,
                                @CookieValue(name = "refresh", required = false) Cookie cookie) {
    TokenDto.TokenInfoDto tokenInfoDto = authService.reIssue(reIssueDto.getAccessToken(), cookie.getValue());
    return new ResponseEntity(new SingleResponseWithMessageDto<>(tokenInfoDto,
      "SUCCESS"),
      HttpStatus.OK);
  }

  /**
   * 이메일 인증 api
   */
  @GetMapping("/validation")
  public ResponseEntity validation(@NotBlank @PathParam("email") String email,
                                   @NotBlank @PathParam("code") String code) {
    authService.validation(email, code);
    return new ResponseEntity(new SingleResponseDto<>("SUCCESS"), HttpStatus.OK);
  }

  /**
   * OAuth2 추가 정보 입력
   */
  @PatchMapping("/members")
  public ResponseEntity updateMember(@AuthenticationPrincipal MemberDetails memberDetails,
                                     @Valid @RequestBody MemberRequestDto.OAuthUpdateDto oAuthUpdateDto) {
    oAuthUpdateDto.setEmail(memberDetails.getUsername());
    Member member = authService.updateMember(mapper.oAuthUpdateDtoToMember(oAuthUpdateDto));
    return new ResponseEntity(new SingleResponseDto<>("SUCCESS"), HttpStatus.OK);
  }

  /**
   * password 재발급 api
   */
  @PostMapping("/password")
  public ResponseEntity reIssuePassword(@Valid @RequestBody MemberRequestDto.EmailDto emailDto) {
    authService.reIssuePassword(emailDto.getEmail());
    return new ResponseEntity(new SingleResponseDto<>("SUCCESS"), HttpStatus.OK);
  }
}
