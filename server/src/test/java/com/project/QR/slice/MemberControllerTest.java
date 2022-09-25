package com.project.QR.slice;

import com.google.gson.Gson;
import com.project.QR.helper.WithMockCustomUser;
import com.project.QR.member.controller.MemberController;
import com.project.QR.member.dto.MemberRequestDto;
import com.project.QR.member.dto.MemberResponseDto;
import com.project.QR.member.entity.Member;
import com.project.QR.member.mapper.MemberMapper;
import com.project.QR.member.service.MemberService;
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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.project.QR.util.ApiDocumentUtils.getRequestPreProcessor;
import static com.project.QR.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockCustomUser
@WebMvcTest(MemberController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class MemberControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private Gson gson;

  @MockBean
  private MemberService memberService;

  @MockBean
  private MemberMapper mapper;

  @Test
  @DisplayName("회원 정보 조회 테스트")
  public void getMemberTest() throws Exception {
    // given
    Member member = MemberStubData.member();
    MemberResponseDto.MemberInfoDto memberInfoDto = MemberStubData.memberInfoDto();

    given(memberService.getMember(Mockito.anyString())).willReturn(member);
    given(mapper.memberToMemberInfoDto(Mockito.any(Member.class))).willReturn(memberInfoDto);

    // when
    ResultActions actions = mockMvc.perform(
      get("/api/v1/members/profile")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer {ACCESS_TOKEN}")
    );

    // then
    actions
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.email").value(memberInfoDto.getEmail()))
      .andExpect(jsonPath("$.data.service").value(memberInfoDto.getService()))
      .andExpect(jsonPath("$.data.profileImg").value(memberInfoDto.getProfileImg()))
      .andExpect(jsonPath("$.data.phone").value(memberInfoDto.getPhone()))
      .andExpect(jsonPath("$.data.name").value(memberInfoDto.getName()))
      .andExpect(jsonPath("$.message").value("SUCCESS"))
      .andDo(
        document(
          "member-get",
          getRequestPreProcessor(),
          getResponsePreProcessor(),
          requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
          responseFields (
            List.of(
              fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
              fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
              fieldWithPath("data.profileImg").type(JsonFieldType.STRING).description("프로필 이미지 URL").optional(),
              fieldWithPath("data.service").type(JsonFieldType.ARRAY).description("가입한 서비스"),
              fieldWithPath("data.phone").type(JsonFieldType.STRING).description("연락처"),
              fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름"),
              fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지")
            )
          )
        )
      );
  }

  @Test
  @DisplayName("회원 정보 변경 테스트")
  public void updateMemberTest() throws Exception {
    //given
    MemberRequestDto.UpdateMemberDto updateMemberDto = MemberStubData.updateMemberDto();
    Member updatedMember = MemberStubData.updatedMember(updateMemberDto);
    MemberResponseDto.MemberInfoDto memberInfoDto = MemberStubData.memberInfoDto(updatedMember);
    String content = gson.toJson(updateMemberDto);
    MockMultipartFile dataJson = new MockMultipartFile("data", null,
      "application/json", content.getBytes());
    MockMultipartFile fileData = new MockMultipartFile("file", "profile.jpg", "image/jpeg",
      "profile".getBytes());

    given(mapper.updateMemberDtoToMember(Mockito.any(MemberRequestDto.UpdateMemberDto.class))).willReturn(new Member());
    given(memberService.updateMember(Mockito.any(Member.class), Mockito.any(MultipartFile.class))).willReturn(new Member());
    given(mapper.memberToMemberInfoDto(Mockito.any(Member.class))).willReturn(memberInfoDto);

    // when
    ResultActions actions = mockMvc.perform(
      multipart("/api/v1/members/profile")
        .file(dataJson)
        .file(fileData)
        .accept(MediaType.APPLICATION_JSON, MediaType.MULTIPART_FORM_DATA)
        .header("Authorization", "Bearer {ACCESS_TOKEN}")
    );

    // then
    actions
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.email").value(memberInfoDto.getEmail()))
      .andExpect(jsonPath("$.data.name").value(memberInfoDto.getName()))
      .andExpect(jsonPath("$.data.profileImg").value(memberInfoDto.getProfileImg()))
      .andExpect(jsonPath("$.data.phone").value(memberInfoDto.getPhone()))
      .andExpect(jsonPath("$.message").value("SUCCESS"))
      .andDo(document(
        "member-update",
        getRequestPreProcessor(),
        getResponsePreProcessor(),
        requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
        requestParts(
          partWithName("data").description("회원 업데이트 정보").optional(),
          partWithName("file").description("프로필 이미지 파일").optional()
        ),
        requestPartFields("data", List.of(
          fieldWithPath("email").description("이메일").ignored(),
          fieldWithPath("password").description("비밀번호").optional(),
          fieldWithPath("service").description("가입한 서비스").optional(),
          fieldWithPath("name").description("이름").optional(),
          fieldWithPath("businessId").description("매장 식별자").optional(),
          fieldWithPath("businessName").description("사업명").optional(),
          fieldWithPath("businessIntroduction").description("매장 소개글").optional(),
          fieldWithPath("profileImg").description("프로필 이미지 URL").ignored(),
          fieldWithPath("phone").description("연락처").optional()
        )),
        responseFields(
          List.of(
            fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
            fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
            fieldWithPath("data.service[]").type(JsonFieldType.ARRAY).description("가입한 서비스"),
            fieldWithPath("data.profileImg").type(JsonFieldType.STRING).description("프로필 이미지 URL"),
            fieldWithPath("data.phone").type(JsonFieldType.STRING).description("연락처"),
            fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름"),
            fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지")
          )
        )
      ));
  }

  @Test
  @DisplayName("로그아웃 테스트")
  public void logoutTest() throws Exception {
    // given
    String email = "test@test.com";
    doNothing().when(memberService).logout(email);

    // when
    ResultActions actions = mockMvc.perform(
      delete("/api/v1/members/logout")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer {ACCESS_TOKEN}")
    );

    // then
    actions
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data").value("BYE"))
      .andDo(
        document(
          "member-logout",
          getRequestPreProcessor(),
          getResponsePreProcessor(),
          requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
          responseFields(
            List.of(
              fieldWithPath("data").type(JsonFieldType.STRING).description("결과 데이터")
            )
          )
        )
      );
  }
}
