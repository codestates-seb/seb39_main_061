package com.project.QR.slice;

import com.google.gson.Gson;
import com.project.QR.business.controller.BusinessAdminController;
import com.project.QR.business.dto.BusinessRequestDto;
import com.project.QR.business.dto.BusinessResponseDto;
import com.project.QR.business.entity.Business;
import com.project.QR.business.mapper.BusinessMapper;
import com.project.QR.business.service.BusinessService;
import com.project.QR.dto.SingleResponseWithMessageDto;
import com.project.QR.helper.WithMockCustomUser;
import com.project.QR.member.controller.MemberController;
import com.project.QR.security.MemberDetails;
import com.project.QR.stub.BusinessStubData;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockCustomUser
@WebMvcTest(BusinessAdminController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class BusinessAdminControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private Gson gson;

  @MockBean
  private BusinessService businessService;

  @MockBean
  private BusinessMapper mapper;

  @Test
  @DisplayName("?????? ?????? ?????? ?????????")
  public void updateBusinessTest() throws Exception {
    // given
    long businessId = 1L;
    Business business = BusinessStubData.business(1L, "business", "introduction",
      "?????? ?????????", "11:00 ~ 22:00", "??????", 37.5, 128.7,
      "000-0000-0000");
    BusinessRequestDto.UpdateBusinessDto updateBusinessDto = BusinessStubData.updateBusinessDto(business);
    BusinessResponseDto.BusinessInfoDto businessInfoDto = BusinessStubData.businessInfoDto(business);
    String content = gson.toJson(updateBusinessDto);

    given(mapper.updateBusinessDtoToBusiness(Mockito.any(BusinessRequestDto.UpdateBusinessDto.class)))
      .willReturn(new Business());
    given(businessService.updateBusiness(Mockito.any(Business.class))).willReturn(business);
    given(mapper.businessToBusinessInfoDto(Mockito.any(Business.class))).willReturn(businessInfoDto);

    // when
    ResultActions actions = mockMvc.perform(
      patch("/api/v1/business/{business-id}", businessId)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer {ACCESS_TOKEN}")
        .content(content)
    );

    // then
    actions
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.businessId").value(businessInfoDto.getBusinessId()))
      .andExpect(jsonPath("$.data.name").value(businessInfoDto.getName()))
      .andExpect(jsonPath("$.data.introduction").value(businessInfoDto.getIntroduction()))
      .andExpect(jsonPath("$.data.openTime").value(businessInfoDto.getOpenTime()))
      .andExpect(jsonPath("$.data.holiday").value(businessInfoDto.getHoliday()))
      .andExpect(jsonPath("$.data.address").value(businessInfoDto.getAddress()))
      .andExpect(jsonPath("$.data.phone").value(businessInfoDto.getPhone()))
      .andExpect(jsonPath("$.data.lon").value(businessInfoDto.getLon()))
      .andExpect(jsonPath("$.data.lat").value(businessInfoDto.getLat()))
      .andExpect(jsonPath("$.message").value("SUCCESS"))
      .andDo(
        document(
          "business-update",
          getRequestPreProcessor(),
          getResponsePreProcessor(),
          requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
          pathParameters(
            parameterWithName("business-id").description("?????? ?????????")
          ),
          requestFields(
            List.of(
              fieldWithPath("businessId").description("?????? ?????????").ignored(),
              fieldWithPath("memberId").description("?????? ?????????").ignored(),
              fieldWithPath("introduction").description("?????? ?????????").optional(),
              fieldWithPath("openTime").description("?????? ??????").optional(),
              fieldWithPath("holiday").description("?????????").optional(),
              fieldWithPath("address").description("??????").optional(),
              fieldWithPath("name").description("?????????").optional(),
              fieldWithPath("lon").description("??????").optional(),
              fieldWithPath("lat").description("??????").optional(),
              fieldWithPath("phone").description("?????????").optional()
            )
          ),
          responseFields(
            List.of(
              fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
              fieldWithPath("data.businessId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
              fieldWithPath("data.name").type(JsonFieldType.STRING).description("?????? ??????"),
              fieldWithPath("data.introduction").type(JsonFieldType.STRING).description("?????? ?????????"),
              fieldWithPath("data.openTime").type(JsonFieldType.STRING).description("?????? ??????"),
              fieldWithPath("data.holiday").type(JsonFieldType.STRING).description("?????????"),
              fieldWithPath("data.address").type(JsonFieldType.STRING).description("??????"),
              fieldWithPath("data.phone").type(JsonFieldType.STRING).description("?????????"),
              fieldWithPath("data.lon").type(JsonFieldType.NUMBER).description("??????"),
              fieldWithPath("data.lat").type(JsonFieldType.NUMBER).description("??????"),
              fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????????")
            )
          )
        )
      );
  }

  @Test
  @DisplayName("?????? ?????? ?????????")
  public void getBusinessTest() throws Exception {
    // given
    Business business = BusinessStubData.business(1L, "business", "introduction",
      "?????? ?????????", "11:00 ~ 22:00", "??????", 37.5, 128.7,
      "000-0000-0000");
    BusinessResponseDto.BusinessInfoDto businessInfoDto = BusinessStubData.businessInfoDto(business);

    given(businessService.getBusinessByMemberId(Mockito.anyLong())).willReturn(business);
    given(mapper.businessToBusinessInfoDto(Mockito.any(Business.class))).willReturn(businessInfoDto);

    // when
    ResultActions actions = mockMvc.perform(
      get("/api/v1/business")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer {ACCESS_TOKEN}")
    );

    // then
    actions
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.businessId").value(businessInfoDto.getBusinessId()))
      .andExpect(jsonPath("$.data.name").value(businessInfoDto.getName()))
      .andExpect(jsonPath("$.data.introduction").value(businessInfoDto.getIntroduction()))
      .andExpect(jsonPath("$.data.openTime").value(businessInfoDto.getOpenTime()))
      .andExpect(jsonPath("$.data.holiday").value(businessInfoDto.getHoliday()))
      .andExpect(jsonPath("$.data.address").value(businessInfoDto.getAddress()))
      .andExpect(jsonPath("$.data.phone").value(businessInfoDto.getPhone()))
      .andExpect(jsonPath("$.data.lon").value(businessInfoDto.getLon()))
      .andExpect(jsonPath("$.data.lat").value(businessInfoDto.getLat()))
      .andExpect(jsonPath("$.message").value("SUCCESS"))
      .andDo(
        document(
          "business-get-admin",
          getRequestPreProcessor(),
          getResponsePreProcessor(),
          requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
          responseFields(
            List.of(
              fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
              fieldWithPath("data.businessId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
              fieldWithPath("data.name").type(JsonFieldType.STRING).description("?????? ??????"),
              fieldWithPath("data.introduction").type(JsonFieldType.STRING).description("?????? ?????????"),
              fieldWithPath("data.openTime").type(JsonFieldType.STRING).description("?????? ??????"),
              fieldWithPath("data.holiday").type(JsonFieldType.STRING).description("?????????"),
              fieldWithPath("data.address").type(JsonFieldType.STRING).description("??????"),
              fieldWithPath("data.phone").type(JsonFieldType.STRING).description("?????????"),
              fieldWithPath("data.lon").type(JsonFieldType.NUMBER).description("??????"),
              fieldWithPath("data.lat").type(JsonFieldType.NUMBER).description("??????"),
              fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????????")
            )
          )
        )
      );
  }
}
