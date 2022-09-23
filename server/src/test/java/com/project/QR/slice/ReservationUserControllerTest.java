package com.project.QR.slice;

import com.google.gson.Gson;
import com.project.QR.reservation.controller.ReservationUserController;
import com.project.QR.reservation.dto.ReservationRequestDto;
import com.project.QR.reservation.dto.ReservationResponseDto;
import com.project.QR.reservation.entity.Reservation;
import com.project.QR.reservation.mapper.ReservationMapper;
import com.project.QR.reservation.service.ReservationService;
import com.project.QR.stub.ReservationStubData;
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
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.project.QR.util.ApiDocumentUtils.getRequestPreProcessor;
import static com.project.QR.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationUserController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class ReservationUserControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private Gson gson;

  @MockBean
  private ReservationService reservationService;

  @MockBean
  private ReservationMapper mapper;

  @Test
  @DisplayName("예약 등록 테스트")
  public void createReservationTest() throws Exception {
    // given
    long businessId = 1L, qrCodeId = 1L;
    ReservationRequestDto.CreateReservationDto createReservationDto = ReservationStubData.createReservationDto();
    Reservation reservation = ReservationStubData.reservation(1L, "000-0000-0000", "홍길동", 4);
    ReservationResponseDto.ReservationInfoDto reservationInfoDto = ReservationStubData.reservationInfoDto(reservation);
    String content = gson.toJson(createReservationDto);

    given(mapper.createReservationToReservation(Mockito.any(ReservationRequestDto.CreateReservationDto.class)))
      .willReturn(new Reservation());
    given(reservationService.createReservation(Mockito.any(Reservation.class))).willReturn(reservation);
    given(mapper.reservationToReservationInfoDto(Mockito.any(Reservation.class))).willReturn(reservationInfoDto);

    // when
    ResultActions actions = mockMvc.perform(
      post("/business/{business-id}/reservation/qr-code/{qr-code-id}", businessId, qrCodeId)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(content)
    );

    // then
    actions
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.data.reservationId").value(reservationInfoDto.getReservationId()))
      .andExpect(jsonPath("$.data.name").value(reservationInfoDto.getName()))
      .andExpect(jsonPath("$.data.phone").value(reservationInfoDto.getPhone()))
      .andExpect(jsonPath("$.data.completed").value(reservationInfoDto.getCompleted().toString()))
      .andExpect(jsonPath("$.data.count").value(reservationInfoDto.getCount()))
      .andExpect(jsonPath("$.message").value("CREATED"))
      .andDo(
        document(
          "reservation-create",
          getRequestPreProcessor(),
          getResponsePreProcessor(),
          pathParameters(
            parameterWithName("business-id").description("사업 식별자"),
            parameterWithName("qr-code-id").description("QR 코드 식별자")
          ),
          requestFields(
            List.of(
              fieldWithPath("businessId").type(JsonFieldType.NUMBER).description("사업 식별자").ignored(),
              fieldWithPath("qrCodeId").type(JsonFieldType.NUMBER).description("QR 코드 식별자").ignored(),
              fieldWithPath("name").type(JsonFieldType.STRING).description("예약자 이름"),
              fieldWithPath("phone").type(JsonFieldType.STRING).description("예약자 연락처"),
              fieldWithPath("count").type(JsonFieldType.NUMBER).description("예약자 수")
            )
          ),
          responseFields(
            List.of(
              fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
              fieldWithPath("data.reservationId").type(JsonFieldType.NUMBER).description("예약 식별자"),
              fieldWithPath("data.name").type(JsonFieldType.STRING).description("예약자 이름"),
              fieldWithPath("data.phone").type(JsonFieldType.STRING).description("예약자 연락처"),
              fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("예약 등록일"),
              fieldWithPath("data.completed").type(JsonFieldType.STRING).description("예약 입장 완료 여부"),
              fieldWithPath("data.count").type(JsonFieldType.NUMBER).description("예약자 수"),
              fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지")
            )
          )
        )
      );
  }

  @Test
  @DisplayName("예약 리스트 조회 테스트")
  public void getReservationsTest() throws Exception {
    // given
    long businessId = 1L, qrCodeId = 1L;
    int page = 1;
    int size = 10;

    Page<Reservation> pageOfReservation = ReservationStubData.getReservationPage(page - 1, size);
    List<Reservation> reservationList = pageOfReservation.getContent();
    List<ReservationResponseDto.ReservationInfoDto> reservationInfoDtoList
      = ReservationStubData.reservationInfoDtoList(reservationList);

    given(reservationService.getUserReservationList(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt()))
      .willReturn(pageOfReservation);
    given(mapper.reservationListToReservationInfoDtoList(Mockito.anyList())).willReturn(reservationInfoDtoList);

    // when
    ResultActions actions = mockMvc.perform(
      get("/business/{business-id}/reservation/qr-code/{qr-code-id}?page={page}&size={size}",
        businessId, qrCodeId, page, size)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
    );

    // then
    actions
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.pageInfo.page").value(page))
      .andExpect(jsonPath("$.pageInfo.size").value(size))
      .andExpect(jsonPath("$.pageInfo.totalElements").value(pageOfReservation.getTotalElements()))
      .andDo(
        document(
          "reservation-list-get-user",
          getRequestPreProcessor(),
          getResponsePreProcessor(),
          pathParameters(
            parameterWithName("business-id").description("사업 식별자"),
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
  @DisplayName("예약 변경 테스트")
  public void updateReservationTest() throws Exception {
    // given
    long businessId = 1L, qrCodeId = 1L, reservationId = 1L;
    ReservationRequestDto.UpdateReservationDto updateReservationDto = ReservationStubData.updateReservationDto();
    Reservation reservation = ReservationStubData.reservation(1L, "000-0000-0000", "홍길동", 1);
    ReservationResponseDto.ReservationInfoDto reservationInfoDto = ReservationStubData.reservationInfoDto(reservation);
    String content = gson.toJson(updateReservationDto);

    given(mapper.updateReservationToReservation(Mockito.any(ReservationRequestDto.UpdateReservationDto.class)))
      .willReturn(new Reservation());
    given(reservationService.updateReservation(Mockito.any(Reservation.class))).willReturn(reservation);
    given(mapper.reservationToReservationInfoDto(Mockito.any(Reservation.class))).willReturn(reservationInfoDto);

    // when
    ResultActions actions = mockMvc.perform(
      patch("/business/{business-id}/reservation/qr-code/{qr-code-id}/info/{reservation-id}",
        businessId, qrCodeId, reservationId)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(content)
    );

    // then
    actions
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.reservationId").value(reservationInfoDto.getReservationId()))
      .andExpect(jsonPath("$.data.name").value(reservationInfoDto.getName()))
      .andExpect(jsonPath("$.data.phone").value(reservationInfoDto.getPhone()))
      .andExpect(jsonPath("$.data.completed").value(reservationInfoDto.getCompleted().toString()))
      .andExpect(jsonPath("$.data.count").value(reservationInfoDto.getCount()))
      .andExpect(jsonPath("$.message").value("SUCCESS"))
      .andDo(
        document(
          "reservation-update",
          getRequestPreProcessor(),
          getResponsePreProcessor(),
          pathParameters(
            parameterWithName("business-id").description("사업 식별자"),
            parameterWithName("qr-code-id").description("QR 코드 식별자"),
            parameterWithName("reservation-id").description("예약 식별자")
          ),
          requestFields(
            List.of(
              fieldWithPath("reservationId").type(JsonFieldType.NUMBER).description("예약 식별자").ignored(),
              fieldWithPath("businessId").type(JsonFieldType.NUMBER).description("사업 식별자").ignored(),
              fieldWithPath("qrCodeId").type(JsonFieldType.NUMBER).description("QR 코드 식별자").ignored(),
              fieldWithPath("name").type(JsonFieldType.STRING).description("예약자 이름"),
              fieldWithPath("phone").type(JsonFieldType.STRING).description("예약자 연락처"),
              fieldWithPath("count").type(JsonFieldType.NUMBER).description("예약자 수")
            )
          ),
          responseFields(
            List.of(
              fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
              fieldWithPath("data.reservationId").type(JsonFieldType.NUMBER).description("예약 식별자"),
              fieldWithPath("data.name").type(JsonFieldType.STRING).description("예약자 이름"),
              fieldWithPath("data.phone").type(JsonFieldType.STRING).description("예약자 연락처"),
              fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("예약 등록일"),
              fieldWithPath("data.completed").type(JsonFieldType.STRING).description("예약 입장 완료 여부"),
              fieldWithPath("data.count").type(JsonFieldType.NUMBER).description("예약자 수"),
              fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지")
            )
          )
        )
      );
  }

  @Test
  @DisplayName("예약 취소 테스트")
  public void deleteReservationTest() throws Exception {
    // given
    long businessId = 1L, qrCodeId = 1L, reservationId = 1L;
    ReservationRequestDto.UpdateReservationDto updateReservationDto = ReservationStubData.updateReservationDto();
    String content = gson.toJson(updateReservationDto);

    given(mapper.updateReservationToReservation(Mockito.any(ReservationRequestDto.UpdateReservationDto.class)))
      .willReturn(new Reservation());
    doNothing().when(reservationService).deleteReservation(Mockito.any(Reservation.class));

    // when
    ResultActions actions = mockMvc.perform(
      patch("/business/{business-id}/reservation/qr-code/{qr-code-id}/cancel/{reservation-id}",
        businessId, qrCodeId, reservationId)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(content)
    );

    // then
    actions
      .andExpect(status().isNoContent())
      .andDo(
        document(
          "reservation-delete",
          getRequestPreProcessor(),
          getResponsePreProcessor(),
          pathParameters(
            parameterWithName("business-id").description("사업 식별자"),
            parameterWithName("qr-code-id").description("QR 코드 식별자"),
            parameterWithName("reservation-id").description("예약 식별자")
          ),
          requestFields(
            List.of(
              fieldWithPath("reservationId").type(JsonFieldType.NUMBER).description("예약 식별자").ignored(),
              fieldWithPath("businessId").type(JsonFieldType.NUMBER).description("사업 식별자").ignored(),
              fieldWithPath("qrCodeId").type(JsonFieldType.NUMBER).description("QR 코드 식별자").ignored(),
              fieldWithPath("name").type(JsonFieldType.STRING).description("예약자 이름"),
              fieldWithPath("phone").type(JsonFieldType.STRING).description("예약자 연락처"),
              fieldWithPath("count").type(JsonFieldType.NUMBER).description("예약자 수")
            )
          )
        )
      );
  }
}
