package com.project.QR.slice;

import com.google.gson.Gson;
import com.project.QR.auth.controller.AuthController;
import com.project.QR.auth.service.AuthService;
import com.project.QR.dto.ExistDto;
import com.project.QR.dto.TokenDto;
import com.project.QR.helper.WithMockCustomUser;
import com.project.QR.member.dto.MemberRequestDto;
import com.project.QR.member.entity.Member;
import com.project.QR.member.mapper.MemberMapper;
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
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.http.Cookie;
import java.util.List;
import java.util.UUID;

import static com.project.QR.util.ApiDocumentUtils.getRequestPreProcessor;
import static com.project.QR.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    // given
    MemberRequestDto.LoginDto loginDto = MemberStubData.loginDto();
    TokenDto.TokenInfoDto tokenInfoDto = TokenStubData.tokenInfoDTO();
    String content = gson.toJson(loginDto);

    given(mapper.loginDtoToMember(Mockito.any(MemberRequestDto.LoginDto.class))).willReturn(new Member());
    given(authService.loginMember(Mockito.any(Member.class), Mockito.any())).willReturn(tokenInfoDto);



    // when
    ResultActions actions = mockMvc.perform(
      post("/auth/login")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(content)
    );

    // then
    actions
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.accessToken").value(tokenInfoDto.getAccessToken()))
      .andExpect(jsonPath("$.data.grantType").value(tokenInfoDto.getGrantType()))
      .andExpect(jsonPath("$.message").value("WELCOME"))
      .andDo(
        document(
          "member-login",
          getRequestPreProcessor(),
          getResponsePreProcessor(),
          requestFields(
            List.of(
              fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
              fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
            )
          ),
          responseFields(
            List.of(
              fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
              fieldWithPath("data.grantType").type(JsonFieldType.STRING).description("권한 부여 타입"),
              fieldWithPath("data.accessToken").type(JsonFieldType.STRING).description("엑세스 토큰"),
              fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지")
            )
          )
        )
      );
  }

  @Test
  @DisplayName("토큰 재발급 테스트")
  public void reIssueTest() throws Exception {
    // given
    TokenDto.ReIssueDto reIssueDto = TokenStubData.reIssueDto();
    TokenDto.TokenInfoDto tokenInfoDto = TokenStubData.newTokenInfoDto();
    Cookie cookie = TokenStubData.cookie();
    String content = gson.toJson(reIssueDto);

    given(authService.reIssue(Mockito.anyString(), Mockito.anyString())).willReturn(tokenInfoDto);

    // when
    ResultActions actions = mockMvc.perform(
      post("/auth/reissue")
        .cookie(cookie)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(content)
    );

    // then
    actions
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.grantType").value(tokenInfoDto.getGrantType()))
      .andExpect(jsonPath("$.data.accessToken").value(tokenInfoDto.getAccessToken()))
      .andExpect(jsonPath("$.message").value("SUCCESS"))
      .andDo(
        document(
          "token-reissue",
          getRequestPreProcessor(),
          getResponsePreProcessor(),
          requestFields(
            List.of(
              fieldWithPath("accessToken").type(JsonFieldType.STRING).description("만료된 엑세스 토큰")
            )
          ),
          responseFields(
            fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
            fieldWithPath("data.grantType").type(JsonFieldType.STRING).description("권한 부여 타입"),
            fieldWithPath("data.accessToken").type(JsonFieldType.STRING).description("엑세스 토큰"),
            fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지")
          )
        )
      );
  }

  @Test
  @DisplayName("이메일 인증 테스트")
  public void validationTest() throws Exception {
    // given
    Member member = MemberStubData.member();
    String email = member.getEmail();
    String code = UUID.randomUUID().toString();

    doNothing().when(authService).validation(Mockito.anyString(), Mockito.anyString());

    // when
    ResultActions actions = mockMvc.perform(
      get("/auth/validation?email={email}&code={code}", email, code)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
    );

    // then
    actions
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data").value("SUCCESS"))
      .andDo(
        document(
          "validation",
          getRequestPreProcessor(),
          getResponsePreProcessor(),
          requestParameters(
            parameterWithName("email").description("이메일"),
            parameterWithName("code").description("일치 여부 확인 코드")
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
  @WithMockCustomUser
  @DisplayName("OAuth2 추가 정보 입력 테스트")
  public void updateMemberTest() throws Exception {
    // given
    Member member = MemberStubData.member();
    MemberRequestDto.OAuthUpdateDto oAuthUpdateDto = MemberStubData.oAuthUpdateDto();
    TokenDto.TokenInfoDto tokenInfoDto = TokenStubData.tokenInfoDTO();
    String content = gson.toJson(oAuthUpdateDto);

    given(mapper.oAuthUpdateDtoToMember(Mockito.any(MemberRequestDto.OAuthUpdateDto.class))).willReturn(new Member());
    given(authService.updateMember(Mockito.any(Member.class), Mockito.any())).willReturn(tokenInfoDto);

    // when
    ResultActions actions = mockMvc.perform(
      patch("/auth/members")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer {ACCESS_TOKEN}")
        .content(content)
    );

    // then
    actions
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.grantType").value(tokenInfoDto.getGrantType()))
      .andExpect(jsonPath("$.data.accessToken").value(tokenInfoDto.getAccessToken()))
      .andExpect(jsonPath("$.message").value("SUCCESS"))
      .andDo(
        document(
          "oauthMember-update",
          getRequestPreProcessor(),
          getResponsePreProcessor(),
          requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
          requestFields(
            List.of(
              fieldWithPath("email").type(JsonFieldType.STRING).description("이메일").ignored(),
              fieldWithPath("service").type(JsonFieldType.STRING).description("가입할 서비스"),
              fieldWithPath("businessName").type(JsonFieldType.STRING).description("사업명"),
              fieldWithPath("phone").type(JsonFieldType.STRING).description("연락처"),
              fieldWithPath("name").type(JsonFieldType.STRING).description("이름")
            )
          ),
          responseFields(
            List.of(
              fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
              fieldWithPath("data.grantType").type(JsonFieldType.STRING).description("권한 부여 타입"),
              fieldWithPath("data.accessToken").type(JsonFieldType.STRING).description("엑세스 토큰"),
              fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지")
            )
          )
        )
      );
  }

  @Test
  @DisplayName("비밀번호 재발급 테스트")
  public void reIssuePasswordTest() throws Exception {
    // given
    String email = "test@test.com";
    MemberRequestDto.EmailDto emailDto = MemberStubData.emailDto(email);
    String content = gson.toJson(emailDto);

    doNothing().when(authService).reIssuePassword(emailDto.getEmail());

    // when
    ResultActions actions = mockMvc.perform(
      post("/auth/password")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(content)
    );

    // then
    actions
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data").value("SUCCESS"))
      .andDo(
        document(
          "password-reIssue",
          getRequestPreProcessor(),
          getResponsePreProcessor(),
          requestFields(
            List.of(
              fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
            )
          ),
          responseFields(
            List.of(
              fieldWithPath("data").type(JsonFieldType.STRING).description("결과 메시지")
            )
          )
        )
      );
  }
}
