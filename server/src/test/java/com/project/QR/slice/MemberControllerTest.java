package com.project.QR.slice;

import com.google.gson.Gson;
import com.project.QR.dto.SingleResponseDto;
import com.project.QR.dto.SingleResponseWithMessageDto;
import com.project.QR.helper.WithMockCustomUser;
import com.project.QR.member.controller.MemberController;
import com.project.QR.member.dto.MemberRequestDto;
import com.project.QR.member.dto.MemberResponseDto;
import com.project.QR.member.entity.Member;
import com.project.QR.member.mapper.MemberMapper;
import com.project.QR.member.service.MemberService;
import com.project.QR.sector.service.SectorService;
import com.project.QR.security.MemberDetails;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import java.util.List;

import static com.project.QR.util.ApiDocumentUtils.getRequestPreProcessor;
import static com.project.QR.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
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

  @MockBean
  private SectorService sectorService;


//  /**
//   * 로그아웃 api
//   */
//  public ResponseEntity logout(@AuthenticationPrincipal MemberDetails memberDetails) {
//    memberService.logout(memberDetails.getUsername());
//    return new ResponseEntity(new SingleResponseDto<>("BYE"), HttpStatus.OK);
//  }

  @Test
  @DisplayName("회원 정보 조회 테스트")
  public void ss() throws Exception {
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
      .andExpect(jsonPath("$.data.sector.sectorId").value(memberInfoDto.getSector().getSectorId()))
      .andExpect(jsonPath("$.data.sector.name").value(memberInfoDto.getSector().getName()))
      .andExpect(jsonPath("$.data.phone").value(memberInfoDto.getPhone()))
      .andExpect(jsonPath("$.data.name").value(memberInfoDto.getName()))
      .andExpect(jsonPath("$.data.businessName").value(memberInfoDto.getBusinessName()))
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
              fieldWithPath("data.sector").type(JsonFieldType.OBJECT).description("업종 데이터"),
              fieldWithPath("data.service").type(JsonFieldType.ARRAY).description("가입한 서비스"),
              fieldWithPath("data.sector.sectorId").type(JsonFieldType.NUMBER).description("업종 식별자"),
              fieldWithPath("data.sector.name").type(JsonFieldType.STRING).description("업종"),
              fieldWithPath("data.businessName").type(JsonFieldType.STRING).description("사업명"),
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
    //  /**
//   * 회원 정보 변경 api
//   */
//  @PostMapping(value = "/profile", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
//  public ResponseEntity updateMember(@AuthenticationPrincipal MemberDetails memberDetails,
//                                     @Valid @RequestPart(name = "data") MemberRequestDto.UpdateMemberDto updateMemberDto,
//                                     @RequestPart(name = "file", required = false) MultipartFile multipartFile) {
//    updateMemberDto.setEmail(memberDetails.getUsername());
//    Member member = memberService.updateMember(mapper.updateMemberDtoToMember(updateMemberDto), multipartFile);
//    MemberResponseDto.MemberInfoDto response = mapper.memberToMemberInfoDto(member);
//    response.getSector().setName(sectorService.getSector(response.getSector().getSectorId()).getName());
//    return new ResponseEntity(new SingleResponseWithMessageDto<>(response,
//      "SUCCESS"),
//      HttpStatus.OK);
//  }
//
    MemberRequestDto.UpdateMemberDto updateMemberDto = MemberStubData.updateMemberDto();

  }
}
