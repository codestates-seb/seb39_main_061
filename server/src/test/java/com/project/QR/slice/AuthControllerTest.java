package com.project.QR.slice;

import com.google.gson.Gson;
import com.project.QR.auth.controller.AuthController;
import com.project.QR.auth.service.AuthService;
import com.project.QR.dto.ExistDto;
import com.project.QR.dto.SingleResponseDto;
import com.project.QR.dto.SingleResponseWithMessageDto;
import com.project.QR.dto.TokenDto;
import com.project.QR.helper.WithMockCustomUser;
import com.project.QR.member.dto.MemberRequestDto;
import com.project.QR.member.entity.Member;
import com.project.QR.member.mapper.MemberMapper;
import com.project.QR.security.MemberDetails;
import com.project.QR.stub.MemberStubData;
import com.project.QR.stub.TokenStubData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.websocket.server.PathParam;

import java.util.List;

import static com.project.QR.util.ApiDocumentUtils.getRequestPreProcessor;
import static com.project.QR.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
  @DisplayName("이메일 중복 검사 테스트")
  public void emailValidationTest() throws Exception {
    // given
    String email = "test@test.com";
    MemberRequestDto.EmailDto emailDto = MemberStubData.emailDto(email);
    ExistDto response = new ExistDto(false);
    String content = gson.toJson(emailDto);

    given(authService.findExistsEmail(Mockito.anyString())).willReturn(false);

    // when
    ResultActions actions = mockMvc.perform(
      post("/auth/email-validation")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(content)
    );

    // then
    actions
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.exist").value(false))
      .andExpect(jsonPath("$.message").value("SUCCESS"))
      .andDo(
        document(
          "email-validation",
          getRequestPreProcessor(),
          getResponsePreProcessor(),
          requestFields(
            List.of(
              fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
            )
          ),
          responseFields(
            List.of(
              fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
              fieldWithPath("data.exist").type(JsonFieldType.BOOLEAN).description("이메일 중복 여부"),
              fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지")
            )
          )
        )
      );
  }

  @Test
  @DisplayName("회원 가입 테스트")
  public void signupTest() throws Exception {
    // given
    Member member = MemberStubData.member();
    MemberRequestDto.CreateMemberDto createMemberDto = MemberStubData.createMemberDto();
    given(mapper.createMemberDtoToMember(Mockito.any(MemberRequestDto.CreateMemberDto.class))).willReturn(new Member());
    given(authService.createMember(Mockito.any(Member.class))).willReturn(member);
    String content = gson.toJson(createMemberDto);

    // when
    ResultActions actions = mockMvc.perform(
      post("/auth/signup")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(content)
    );

    // then
    actions
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.data").value("WELCOME"))
      .andDo(
        document(
          "signup",
          getRequestPreProcessor(),
          getResponsePreProcessor(),
          requestFields(
            List.of(
              fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
              fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
              fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
              fieldWithPath("phone").type(JsonFieldType.STRING).description("연락처"),
              fieldWithPath("businessName").type(JsonFieldType.STRING).description("사업명"),
              fieldWithPath("sectorId").type(JsonFieldType.NUMBER).description("업종 번호"),
              fieldWithPath("role").type(JsonFieldType.STRING).description("가입한 서비스")
            )
          ),
          responseFields(
            List.of(
              fieldWithPath("data").type(JsonFieldType.STRING).description("결과 데이터")
            )
          )
        )
      );
  }

  @Test
  @DisplayName("로그인 테스트")
  public void loginTest() throws Exception {
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
    // given
    MemberRequestDto.LoginDto loginDto = MemberStubData.loginDto();
    TokenDto.TokenInfoDto tokenInfoDto = TokenStubData.tokenInfoDTO();

    // when

    // then
  }
}
