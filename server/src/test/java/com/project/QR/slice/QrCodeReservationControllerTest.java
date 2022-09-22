package com.project.QR.slice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.project.QR.business.service.BusinessService;
import com.project.QR.helper.WithMockCustomUser;
import com.project.QR.qrcode.controller.QrCodeReservationController;
import com.project.QR.qrcode.dto.QrCodeRequestDto;
import com.project.QR.qrcode.dto.QrCodeResponseDto;
import com.project.QR.qrcode.entity.QrCode;
import com.project.QR.qrcode.mapper.QrCodeMapper;
import com.project.QR.qrcode.service.QrCodeService;
import com.project.QR.stub.QrCodeStubData;
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
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockCustomUser
@WebMvcTest(QrCodeReservationController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class QrCodeReservationControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private Gson gson;

  @MockBean
  private QrCodeService qrCodeService;

  @MockBean
  private QrCodeMapper mapper;

  @MockBean
  private BusinessService businessService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @DisplayName("QR 코드 생성 테스트")
  public void createQrCode() throws Exception {
    // given
    long businessId = 1L;
    QrCodeRequestDto.CreateQrCodeDto createQrCodeDto = QrCodeStubData.createQrCodeDto(businessId);
    QrCode qrCode = QrCodeStubData.qrCode();
    QrCodeResponseDto.QrCodeInfoDto qrCodeInfoDto = QrCodeStubData.createQrCodeInfoDto(qrCode);

    given(mapper.createQrCodeDtoToQrCode(Mockito.any(QrCodeRequestDto.CreateQrCodeDto.class))).willReturn(new QrCode());
    given(qrCodeService.createQrCode(Mockito.any(QrCode.class))).willReturn(qrCode);
    given(mapper.qrCodeToQrCodeInfoDto(Mockito.any(QrCode.class))).willReturn(qrCodeInfoDto);

    // when
    ResultActions actions = mockMvc.perform(
      post("/api/v1/reservation/{business-id}/qr-code", businessId)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer {ACCESS_TOKEN}")
        .content(objectMapper.writeValueAsString(createQrCodeDto))
    );

    // then
    actions
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.data.qrCodeId").value(qrCodeInfoDto.getQrCodeId()))
      .andExpect(jsonPath("$.data.qrCodeImg").value(qrCodeInfoDto.getQrCodeImg()))
      .andExpect(jsonPath("$.data.target").value(qrCodeInfoDto.getTarget()))
      .andExpect(jsonPath("$.data.qrType").value(qrCodeInfoDto.getQrType().toString()))
      .andExpect(jsonPath("$.message").value("CREATED"))
      .andDo(
        document(
          "qr-code-create",
          getRequestPreProcessor(),
          getResponsePreProcessor(),
          requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
          pathParameters(
            parameterWithName("business-id").description("사업 식별자")
          ),
          requestFields(
            List.of(
              fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자").ignored(),
              fieldWithPath("target").type(JsonFieldType.STRING).description("관리 대상"),
              fieldWithPath("qrType").type(JsonFieldType.STRING).description("QR 코드 타입"),
              fieldWithPath("businessId").type(JsonFieldType.NUMBER).description("사업 식별자").ignored(),
              fieldWithPath("dueDate").type(JsonFieldType.STRING).description("QR 코드 만료 기간")
            )
          ),
          responseFields(
            List.of(
              fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
              fieldWithPath("data.qrCodeId").type(JsonFieldType.NUMBER).description("QR 코드 식별자"),
              fieldWithPath("data.qrCodeImg").type(JsonFieldType.STRING).description("QR 코드 이미지").optional(),
              fieldWithPath("data.target").type(JsonFieldType.STRING).description("관리 대상"),
              fieldWithPath("data.qrType").type(JsonFieldType.STRING).description("QR 코드 타입"),
              fieldWithPath("data.reservations").type(JsonFieldType.ARRAY).description("예약 목록"),
              fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지")
            )
          )
        )
      );
  }

  @Test
  @DisplayName("QR 코드 변경 테스트")
  public void updateQrCodeTest() throws Exception {
    // given
    long businessId = 1L;
    long qrCodeId = 1L;
    QrCodeRequestDto.UpdateQrCodeDto updateQrCodeDto = QrCodeStubData.updateQrCodeDto();
    QrCode updatedQrCode = QrCodeStubData.updatedQrCode();
    QrCodeResponseDto.QrCodeInfoDto qrCodeInfoDto = QrCodeStubData.createQrCodeInfoDto(updatedQrCode);
    MockMultipartFile dataJson = new MockMultipartFile("data", null,
      "application/json", objectMapper.writeValueAsString(updateQrCodeDto).getBytes());
    MockMultipartFile fileData = new MockMultipartFile("file", "qr-code.png", "image/png",
      "qr-code".getBytes());

    given(mapper.updateQrCodeDtoToQrCode(Mockito.any(QrCodeRequestDto.UpdateQrCodeDto.class))).willReturn(new QrCode());
    given(qrCodeService.updateQrCode(Mockito.any(QrCode.class), Mockito.any(MultipartFile.class))).willReturn(updatedQrCode);
    given(mapper.qrCodeToQrCodeInfoDto(Mockito.any(QrCode.class))).willReturn(qrCodeInfoDto);

    // when
    ResultActions actions = mockMvc.perform(
      multipart("/api/v1/reservation/{business-id}/qr-code/{qr-code-id}/update", businessId, qrCodeId)
        .file(dataJson)
        .file(fileData)
        .accept(MediaType.APPLICATION_JSON, MediaType.MULTIPART_FORM_DATA)
        .header("Authorization", "Bearer {ACCESS_TOKEN}")
    );

    // then
    actions
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.qrCodeId").value(qrCodeInfoDto.getQrCodeId()))
      .andExpect(jsonPath("$.data.qrCodeImg").value(qrCodeInfoDto.getQrCodeImg()))
      .andExpect(jsonPath("$.data.target").value(qrCodeInfoDto.getTarget()))
      .andExpect(jsonPath("$.data.qrType").value(qrCodeInfoDto.getQrType().toString()))
      .andExpect(jsonPath("$.message").value("SUCCESS"))
      .andDo(
        document(
          "qr-code-update",
          getRequestPreProcessor(),
          getResponsePreProcessor(),
          requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
          pathParameters(
            parameterWithName("business-id").description("사업 식별자"),
            parameterWithName("qr-code-id").description("QR 코드 식별자")
          ),
          requestParts(
            partWithName("data").description("QR 코드 업데이트 정보").optional(),
            partWithName("file").description("QR 코드 이미지 파일").optional()
          ),
          requestPartFields("data", List.of(
            fieldWithPath("qrCodeId").description("QR 코드 식별자").ignored(),
            fieldWithPath("memberId").description("회원 식별자").ignored(),
            fieldWithPath("target").description("관리 대상").optional(),
            fieldWithPath("qrType").description("QR 코드 타입"),
            fieldWithPath("dueDate").description("QR 코드 만료 기간").optional(),
            fieldWithPath("businessId").description("지역").ignored()
          )),
          responseFields(
            List.of(
              fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
              fieldWithPath("data.qrCodeId").type(JsonFieldType.NUMBER).description("QR 코드 식별자"),
              fieldWithPath("data.qrCodeImg").type(JsonFieldType.STRING).description("QR 코드 이미지").optional(),
              fieldWithPath("data.target").type(JsonFieldType.STRING).description("관리 대상"),
              fieldWithPath("data.qrType").type(JsonFieldType.STRING).description("QR 코드 타입"),
              fieldWithPath("data.reservations").type(JsonFieldType.ARRAY).description("예약 목록"),
              fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지")
            )
          )
        )
      );
  }

  @Test
  @DisplayName("특정 QR 코드 조회 테스트")
  public void getQrCodeTest() throws Exception {
    // given
    long businessId = 1L;
    long qrCodeId = 1L;
    QrCode qrCode = QrCodeStubData.getQrCode();
    QrCodeResponseDto.QrCodeInfoDto qrCodeInfoDto = QrCodeStubData.getQrCodeInfoDto(qrCode);

    given(qrCodeService.getQrCode(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong())).willReturn(new QrCode());
    given(mapper.qrCodeToQrCodeInfoDto(Mockito.any(QrCode.class))).willReturn(qrCodeInfoDto);

    // when
    ResultActions actions = mockMvc.perform(
      get("/api/v1/reservation/{business-id}/qr-code/{qr-code-id}", businessId, qrCodeId)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer {ACCESS_TOKEN}")
    );

    // then
    actions
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.qrCodeId").value(qrCodeInfoDto.getQrCodeId()))
      .andExpect(jsonPath("$.data.qrCodeImg").value(qrCodeInfoDto.getQrCodeImg()))
      .andExpect(jsonPath("$.data.target").value(qrCodeInfoDto.getTarget()))
      .andExpect(jsonPath("$.data.qrType").value(qrCodeInfoDto.getQrType().toString()))
      .andExpect(jsonPath("$.message").value("SUCCESS"))
      .andDo(
        document(
          "qr-code-get",
          getRequestPreProcessor(),
          getResponsePreProcessor(),
          requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
          pathParameters(
            parameterWithName("business-id").description("사업 식별자"),
            parameterWithName("qr-code-id").description("QR 코드 식별자")
          ),
          responseFields(
            List.of(
              fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
              fieldWithPath("data.qrCodeId").type(JsonFieldType.NUMBER).description("QR 코드 식별자"),
              fieldWithPath("data.qrCodeImg").type(JsonFieldType.STRING).description("QR 코드 이미지").optional(),
              fieldWithPath("data.target").type(JsonFieldType.STRING).description("관리 대상"),
              fieldWithPath("data.qrType").type(JsonFieldType.STRING).description("QR 코드 타입"),
              fieldWithPath("data.reservations").type(JsonFieldType.ARRAY).description("예약 목록"),
              fieldWithPath("data.reservations[].reservationId").type(JsonFieldType.NUMBER).description("예약 식별자"),
              fieldWithPath("data.reservations[].name").type(JsonFieldType.STRING).description("예약자 이름"),
              fieldWithPath("data.reservations[].phone").type(JsonFieldType.STRING).description("예약자 연락처"),
              fieldWithPath("data.reservations[].createdAt").type(JsonFieldType.STRING).description("예약 등록 일자"),
              fieldWithPath("data.reservations[].completed").type(JsonFieldType.STRING).description("예약 입장 완료 여부"),
              fieldWithPath("data.reservations[].count").type(JsonFieldType.NUMBER).description("예약 식별자"),
              fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지")
            )
          )
        )
      );
  }

  @Test
  @DisplayName("QR 코드(예약) 리스트 조회 테스트")
  public void getQrCodesTest() throws Exception {
    // given
    long businessId = 1L;
    int page = 1;
    int size = 10;

    Page<QrCode> pageOfQrCode = QrCodeStubData.getQrCodePage(page - 1, size);
    List<QrCode> qrCodeList = pageOfQrCode.getContent();
    List<QrCodeResponseDto.ShortQrCodeInfoDto> qrCodeInfoDtoList = QrCodeStubData.getQrCodeInfoDtoList(qrCodeList);

    given(qrCodeService.getQrCodes( Mockito.anyInt(), Mockito.anyInt(), Mockito.anyLong(), Mockito.anyLong())).willReturn(pageOfQrCode);
    given(mapper.qrCodeListToQrCodeInfoDtoList(Mockito.anyList())).willReturn(qrCodeInfoDtoList);

    // when
    ResultActions actions = mockMvc.perform(
      get("/api/v1/reservation/{business-id}/qr-code?page={page}&size={size}", businessId, page, size)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer {ACCESS_TOKEN}")
    );

    // then
    actions
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.pageInfo.page").value(page))
      .andExpect(jsonPath("$.pageInfo.size").value(size))
      .andExpect(jsonPath("$.pageInfo.totalElements").value(pageOfQrCode.getTotalElements()))
      .andDo(
        document(
          "qr-code-list-get",
          getRequestPreProcessor(),
          getResponsePreProcessor(),
          requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
          pathParameters(
            parameterWithName("business-id").description("사업 식별자")
          ),
          requestParameters(
            parameterWithName("page").description("페이지 수"),
            parameterWithName("size").description("페이지 크기")
          ),
          responseFields(
            List.of(
              fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
              fieldWithPath("data[].qrCodeId").type(JsonFieldType.NUMBER).description("QR 코드 식별자"),
              fieldWithPath("data[].target").type(JsonFieldType.STRING).description("관리 대상"),
              fieldWithPath("data[].qrType").type(JsonFieldType.STRING).description("QR 코드 타입"),
              fieldWithPath("data[].qrCodeImg").type(JsonFieldType.STRING).description("QR 코드 이미지").optional(),
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
  @DisplayName("QR 코드 삭제 테스트")
  public void deleteQrCodeTest() throws Exception {
    // given
    long businessId = 1L;
    long qrCodeId = 1L;

    doNothing().when(qrCodeService).deleteQrCode(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong());

    // when
    ResultActions actions = mockMvc.perform(
      delete("/api/v1/reservation/{business-id}/qr-code/{qr-code-id}", businessId, qrCodeId)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer {ACCESS_TOKEN}")
    );

    // then
    actions
      .andExpect(status().isNoContent())
      .andDo(
        document(
          "qr-code-delete",
          getRequestPreProcessor(),
          getResponsePreProcessor(),
          requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
          pathParameters(
            parameterWithName("business-id").description("사업 식별자"),
            parameterWithName("qr-code-id").description("QR 코드 식별자")
          )
        )
      );
  }
}
