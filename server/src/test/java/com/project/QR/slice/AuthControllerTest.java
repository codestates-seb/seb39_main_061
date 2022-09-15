package com.project.QR.slice;

import com.google.gson.Gson;
import com.project.QR.auth.controller.AuthController;
import com.project.QR.auth.service.AuthService;
import com.project.QR.helper.WithMockCustomUser;
import com.project.QR.member.dto.MemberRequestDto;
import com.project.QR.member.entity.Member;
import com.project.QR.member.mapper.MemberMapper;
import com.project.QR.stub.MemberStubData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;

@WithMockCustomUser
@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class AuthControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private Gson gson;

  @MockBean
  private AuthService authService;

  @MockBean
  private MemberMapper mapper;

//  @PostMapping("/email-validation")
//  public ResponseEntity emailValidation(@Valid @RequestBody MemberRequestDto.EmailDto emailDto) {
//    boolean exist = authService.findExistsEmail(emailDto.getEmail());
//    return new ResponseEntity(new SingleResponseWithMessageDto<>(new ExistDto(exist),
//      "SUCCESS"),
//      HttpStatus.OK);
//  }
//

//
//  /**
//   * 로그인 api
//   */
//  @PostMapping("/login")
//  public ResponseEntity login(@Valid @RequestBody MemberRequestDto.LoginDto loginDto,
//                              HttpServletResponse response) {
//    TokenDto.TokenInfoDto tokenInfoDto = authService.loginMember(mapper.loginDtoToMember(loginDto), response);
//    return new ResponseEntity(new SingleResponseWithMessageDto<>(tokenInfoDto,
//      "WELCOME"),
//      HttpStatus.OK);
//  }
//
//  /**
//   * accessToken 재발급 api
//   */
//  @PostMapping("/reissue")
//  public ResponseEntity reIssue(@Valid @RequestBody TokenDto.ReIssueDto reIssueDto,
//                                @CookieValue(name = "refresh", required = false) Cookie cookie) {
//    TokenDto.TokenInfoDto tokenInfoDto = authService.reIssue(reIssueDto.getAccessToken(), cookie.getValue());
//    return new ResponseEntity(new SingleResponseWithMessageDto<>(tokenInfoDto,
//      "SUCCESS"),
//      HttpStatus.OK);
//  }
//
//  /**
//   * 이메일 인증 api
//   */
//  @GetMapping("/validation")
//  public ResponseEntity validation(@NotBlank @PathParam("email") String email,
//                                   @NotBlank @PathParam("code") String code) {
//    authService.validation(email, code);
//    return new ResponseEntity(new SingleResponseDto<>("SUCCESS"), HttpStatus.OK);
//  }
//
//  /**
//   * OAuth2 추가 정보 입력
//   */
//  @PatchMapping("/members")
//  public ResponseEntity updateMember(@AuthenticationPrincipal MemberDetails memberDetails,
//                                     @Valid @RequestBody MemberRequestDto.OAuthUpdateDto oAuthUpdateDto) {
//    oAuthUpdateDto.setEmail(memberDetails.getUsername());
//    Member member = authService.updateMember(mapper.oAuthUpdateDtoToMember(oAuthUpdateDto));
//    return new ResponseEntity(new SingleResponseDto<>("SUCCESS"), HttpStatus.OK);
//  }
//
//  /**
//   * password 재발급 api
//   */
//  @PostMapping("/password")
//  public ResponseEntity reIssuePassword(@Valid @RequestBody MemberRequestDto.EmailDto emailDto) {
//    authService.reIssuePassword(emailDto.getEmail());
//    return new ResponseEntity(new SingleResponseDto<>("SUCCESS"), HttpStatus.OK);
//  }

  @Test
  @DisplayName("회원 가입 테스트")
  public void signupTest() {
    //  /**
//   * 회원가입 api
//   */
//  @PostMapping("/signup")
//  public ResponseEntity signUp(@Valid @RequestBody MemberRequestDto.CreateMemberDto createMemberDto) {
//    authService.createMember(mapper.createMemberDtoToMember(createMemberDto));
//    return new ResponseEntity(new SingleResponseDto<>("WELCOME"), HttpStatus.CREATED);
//  }
    // given
    MemberRequestDto.CreateMemberDto createMemberDto = MemberStubData.createMemberDto();
    given(mapper.createMemberDtoToMember(Mockito.any(MemberRequestDto.CreateMemberDto.class))).willReturn(new Member());
//    given(authService.createMember())
    // when

    // then
  }
}
