package com.project.QR.slice;

import com.google.gson.Gson;
import com.project.QR.business.controller.BusinessUserController;
import com.project.QR.business.dto.BusinessResponseDto;
import com.project.QR.business.entity.Business;
import com.project.QR.business.mapper.BusinessMapper;
import com.project.QR.business.service.BusinessService;
import com.project.QR.dto.SingleResponseWithMessageDto;
import com.project.QR.helper.WithMockCustomUser;
import com.project.QR.member.controller.MemberController;
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
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.Positive;
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
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BusinessUserController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class BusinessUserControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private Gson gson;

  @MockBean
  private BusinessService businessService;

  @MockBean
  private BusinessMapper mapper;

  @Test
  @DisplayName("매정 정보 조회 테스트")
  public void getBusinessTest() throws Exception {
    // given
    long businessId = 1L;
    Business business = BusinessStubData.business(1L, "business", "introduction",
      "매주 월요일", "11:00 ~ 22:00", "서울", 37.5, 128.7,
      "000-0000-0000");
    BusinessResponseDto.BusinessInfoDto businessInfoDto = BusinessStubData.businessInfoDto(business);

    given(businessService.getBusinessByBusinessId(Mockito.anyLong())).willReturn(business);
    given(mapper.businessToBusinessInfoDto(Mockito.any(Business.class))).willReturn(businessInfoDto);

    // when
    ResultActions actions = mockMvc.perform(
      get("/business/{business-id}", businessId)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
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
          "business-get-user",
          getRequestPreProcessor(),
          getResponsePreProcessor(),
          pathParameters(
            parameterWithName("business-id").description("매장 식별자")
          ),
          responseFields(
            List.of(
              fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
              fieldWithPath("data.businessId").type(JsonFieldType.NUMBER).description("매장 식별자"),
              fieldWithPath("data.name").type(JsonFieldType.STRING).description("매장 이름"),
              fieldWithPath("data.introduction").type(JsonFieldType.STRING).description("매장 소개글"),
              fieldWithPath("data.openTime").type(JsonFieldType.STRING).description("영업 시간"),
              fieldWithPath("data.holiday").type(JsonFieldType.STRING).description("휴무일"),
              fieldWithPath("data.address").type(JsonFieldType.STRING).description("주소"),
              fieldWithPath("data.phone").type(JsonFieldType.STRING).description("연락처"),
              fieldWithPath("data.lon").type(JsonFieldType.NUMBER).description("경도"),
              fieldWithPath("data.lat").type(JsonFieldType.NUMBER).description("위도"),
              fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지")
            )
          )
        )
      );
  }
}
