package com.project.QR.slice;

import com.google.gson.Gson;
import com.project.QR.helper.WithMockCustomUser;
import com.project.QR.reservation.controller.ReservationStatisticsController;
import com.project.QR.reservation.dto.ReservationResponseDto;
import com.project.QR.reservation.mapper.ReservationMapper;
import com.project.QR.reservation.service.ReservationService;
import com.project.QR.stub.StatisticsStubData;
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

import java.time.LocalDateTime;
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
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockCustomUser
@WebMvcTest(ReservationStatisticsController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class ReservationStatisticsControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private Gson gson;

  @MockBean
  ReservationService reservationService;

  @MockBean
  ReservationMapper mapper;

  @Test
  @DisplayName("통계 데이터 테스트")
  public void getStatisticsTest() throws Exception {
    // given
    long businessId = 1L;
    long qrCodeId = 1L;
    String date = "20220922";
    ReservationResponseDto.StatisticsInfoDto statisticsInfoDto = StatisticsStubData.statisticsInfoDto();

    given(reservationService.getStatistics(Mockito.anyLong(), Mockito.anyLong(), Mockito.any(LocalDateTime.class), Mockito.anyLong()))
      .willReturn(statisticsInfoDto);

    // when
    ResultActions actions = mockMvc.perform(
      get("/api/v1/reservation/{business-id}/qr-code/{qr-code-id}/statistics?date={date}", businessId, qrCodeId, date)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer {ACCESS_TOKEN}")
    );

    // then
    actions
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.message").value("SUCCESS"))
      .andDo(
        document(
          "reservation-statistics-get",
          getRequestPreProcessor(),
          getResponsePreProcessor(),
          requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
          pathParameters(
            parameterWithName("business-id").description("사업 식별자"),
            parameterWithName("qr-code-id").description("QR 코드 식별자")
          ),
          requestParameters(
            parameterWithName("date").description("기준 날짜")
          ),
          responseFields(
            List.of(
              fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
              fieldWithPath("data.month").type(JsonFieldType.ARRAY).description("월별 통계 데이터"),
              fieldWithPath("data.month[].deleted").type(JsonFieldType.STRING).description("예약 취소 여부"),
              fieldWithPath("data.month[].date").type(JsonFieldType.STRING).description("년-월"),
              fieldWithPath("data.month[].count").type(JsonFieldType.NUMBER).description("예약 총 합"),
              fieldWithPath("data.week").type(JsonFieldType.ARRAY).description("주간 통계 데이터"),
              fieldWithPath("data.week[].deleted").type(JsonFieldType.STRING).description("예약 취소 여부"),
              fieldWithPath("data.week[].date").type(JsonFieldType.STRING).description("년-월-일"),
              fieldWithPath("data.week[].count").type(JsonFieldType.NUMBER).description("예약 총 합"),
              fieldWithPath("data.time").type(JsonFieldType.ARRAY).description("시간별 통계 데이터"),
              fieldWithPath("data.time[].deleted").type(JsonFieldType.STRING).description("예약 취소 여부"),
              fieldWithPath("data.time[].date").type(JsonFieldType.STRING).description("시간대"),
              fieldWithPath("data.time[].count").type(JsonFieldType.NUMBER).description("예약 총 합"),
              fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지")
            )
          )
        )
      );
  }
}
