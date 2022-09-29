package com.project.QR.slice;

import com.google.gson.Gson;
import com.project.QR.dto.MessageResponseDto;
import com.project.QR.dto.MultiResponseWithPageInfoDto;
import com.project.QR.helper.WithMockCustomUser;
import com.project.QR.helper.page.RestPage;
import com.project.QR.reservation.controller.ReservationAdminController;
import com.project.QR.reservation.dto.ReservationResponseDto;
import com.project.QR.reservation.entity.Reservation;
import com.project.QR.reservation.mapper.ReservationMapper;
import com.project.QR.reservation.service.ReservationService;
import com.project.QR.security.MemberDetails;
import com.project.QR.stub.ReservationStubData;
import com.project.QR.stub.StatisticsStubData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.Positive;
import javax.websocket.server.PathParam;
import java.time.LocalDateTime;
import java.util.List;

import static com.project.QR.util.ApiDocumentUtils.getRequestPreProcessor;
import static com.project.QR.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockCustomUser
@WebMvcTest(ReservationAdminController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class ReservationAdminControllerTest {
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
      get("/api/v1/business/{business-id}/reservation/qr-code/{qr-code-id}/statistics?date={date}", businessId, qrCodeId, date)
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
            parameterWithName("business-id").description("매장 식별자"),
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

  @Test
  @DisplayName("예약 리스트 조회 테스트")
  public void getReservationListTest() throws Exception {
    // given
    long businessId = 1L, qrCodeId = 1L;
    int page = 1;
    int size = 10;

    RestPage<Reservation> pageOfReservation = ReservationStubData.getReservationPage(page - 1, size);
    List<Reservation> reservationList = pageOfReservation.getContent();
    List<ReservationResponseDto.ReservationInfoDto> reservationInfoDtoList
      = ReservationStubData.reservationInfoDtoList(reservationList);

    given(reservationService.getAdminReservationList(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(),
      Mockito.anyInt(), Mockito.anyInt())).willReturn(pageOfReservation);
    given(mapper.reservationListToReservationInfoDtoList(Mockito.anyList())).willReturn(reservationInfoDtoList);

    // when
    ResultActions actions = mockMvc.perform(
      get("/api/v1/business/{business-id}/reservation/qr-code/{qr-code-id}?page={page}&size={size}",
        businessId, qrCodeId, page, size)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer {ACCESS_TOKEN}")
    );

    // then
    actions
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.pageInfo.page").value(page))
      .andExpect(jsonPath("$.pageInfo.size").value(size))
      .andExpect(jsonPath("$.pageInfo.totalElements").value(pageOfReservation.getTotalElements()))
      .andDo(
        document(
          "reservation-list-get-admin",
          getRequestPreProcessor(),
          getResponsePreProcessor(),
          requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
          pathParameters(
            parameterWithName("business-id").description("매장 식별자"),
            parameterWithName("qr-code-id").description("QR 코드 식별자")
          ),
          requestParameters(
            parameterWithName("page").description("페이지 수"),
            parameterWithName("size").description("페이지 크기")
          ),
          responseFields(
            List.of(
              fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
              fieldWithPath("data[].reservationId").type(JsonFieldType.NUMBER).description("예약 식별자"),
              fieldWithPath("data[].name").type(JsonFieldType.STRING).description("예약자 이름"),
              fieldWithPath("data[].phone").type(JsonFieldType.STRING).description("예약자 연락처"),
              fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("예약 등록일"),
              fieldWithPath("data[].completed").type(JsonFieldType.STRING).description("예약 입장 완료 여부"),
              fieldWithPath("data[].count").type(JsonFieldType.NUMBER).description("예약자 수"),
              fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이지 정보"),
              fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("현재 페이지"),
              fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("현재 페이지 크기"),
              fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("총 데이터 수"),
              fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수")
            )
          )
        )
      );
  }

  @Test
  @DisplayName("예약 입장 테스트")
  public void enterReservationTest() throws Exception {
    // given
    long businessId = 1L, qrCodeId = 1L, reservationId = 1L;

    doNothing().when(reservationService).enterReservation(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong());

    // when
    ResultActions actions = mockMvc.perform(
      patch("/api/v1/business/{business-id}/reservation/qr-code/{qr-code-id}/info/{reservation-id}/enter",
        businessId, qrCodeId, reservationId)
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
          "reservation-enter",
          getRequestPreProcessor(),
          getResponsePreProcessor(),
          requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
          pathParameters(
            parameterWithName("business-id").description("매장 식별자"),
            parameterWithName("qr-code-id").description("QR 코드 식별자"),
            parameterWithName("reservation-id").description("예약 식별자")
          ),
          responseFields(
            List.of(
              fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지")
            )
          )
        )
      );
  }

  @Test
  @DisplayName("예약 취소 테스트")
  public void cancelReservationTest() throws Exception{
    // given
    long businessId = 1L, qrCodeId = 1L, reservationId = 1L;

    doNothing().when(reservationService).enterReservation(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong());

    // when
    ResultActions actions = mockMvc.perform(
      patch("/api/v1/business/{business-id}/reservation/qr-code/{qr-code-id}/info/{reservation-id}/cancel",
        businessId, qrCodeId, reservationId)
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
          "reservation-cancel",
          getRequestPreProcessor(),
          getResponsePreProcessor(),
          requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
          pathParameters(
            parameterWithName("business-id").description("매장 식별자"),
            parameterWithName("qr-code-id").description("QR 코드 식별자"),
            parameterWithName("reservation-id").description("예약 식별자")
          ),
          responseFields(
            List.of(
              fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지")
            )
          )
        )
      );
  }
}
