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
  @DisplayName("QR ?????? ?????? ?????????")
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
      post("/api/v1/business/{business-id}/type/reservation/qr-code", businessId)
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
            parameterWithName("business-id").description("?????? ?????????")
          ),
          requestFields(
            List.of(
              fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("?????? ?????????").ignored(),
              fieldWithPath("target").type(JsonFieldType.STRING).description("?????? ??????"),
              fieldWithPath("qrType").type(JsonFieldType.STRING).description("QR ?????? ??????"),
              fieldWithPath("businessId").type(JsonFieldType.NUMBER).description("?????? ?????????").ignored(),
              fieldWithPath("dueDate").type(JsonFieldType.STRING).description("QR ?????? ?????? ??????")
            )
          ),
          responseFields(
            List.of(
              fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
              fieldWithPath("data.qrCodeId").type(JsonFieldType.NUMBER).description("QR ?????? ?????????"),
              fieldWithPath("data.qrCodeImg").type(JsonFieldType.STRING).description("QR ?????? ?????????").optional(),
              fieldWithPath("data.target").type(JsonFieldType.STRING).description("?????? ??????"),
              fieldWithPath("data.qrType").type(JsonFieldType.STRING).description("QR ?????? ??????"),
              fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????????")
            )
          )
        )
      );
  }

  @Test
  @DisplayName("QR ?????? ?????? ?????????")
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
      multipart("/api/v1/business/{business-id}/type/reservation/qr-code/{qr-code-id}/update", businessId, qrCodeId)
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
            parameterWithName("business-id").description("?????? ?????????"),
            parameterWithName("qr-code-id").description("QR ?????? ?????????")
          ),
          requestParts(
            partWithName("data").description("QR ?????? ???????????? ??????").optional(),
            partWithName("file").description("QR ?????? ????????? ??????").optional()
          ),
          requestPartFields("data", List.of(
            fieldWithPath("qrCodeId").description("QR ?????? ?????????").ignored(),
            fieldWithPath("memberId").description("?????? ?????????").ignored(),
            fieldWithPath("target").description("?????? ??????").optional(),
            fieldWithPath("qrType").description("QR ?????? ??????"),
            fieldWithPath("dueDate").description("QR ?????? ?????? ??????").optional(),
            fieldWithPath("businessId").description("??????").ignored()
          )),
          responseFields(
            List.of(
              fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
              fieldWithPath("data.qrCodeId").type(JsonFieldType.NUMBER).description("QR ?????? ?????????"),
              fieldWithPath("data.qrCodeImg").type(JsonFieldType.STRING).description("QR ?????? ?????????").optional(),
              fieldWithPath("data.target").type(JsonFieldType.STRING).description("?????? ??????"),
              fieldWithPath("data.qrType").type(JsonFieldType.STRING).description("QR ?????? ??????"),
              fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????????")
            )
          )
        )
      );
  }

  @Test
  @DisplayName("?????? QR ?????? ?????? ?????????")
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
      get("/api/v1/business/{business-id}/type/reservation/qr-code/{qr-code-id}", businessId, qrCodeId)
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
            parameterWithName("business-id").description("?????? ?????????"),
            parameterWithName("qr-code-id").description("QR ?????? ?????????")
          ),
          responseFields(
            List.of(
              fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
              fieldWithPath("data.qrCodeId").type(JsonFieldType.NUMBER).description("QR ?????? ?????????"),
              fieldWithPath("data.qrCodeImg").type(JsonFieldType.STRING).description("QR ?????? ?????????").optional(),
              fieldWithPath("data.target").type(JsonFieldType.STRING).description("?????? ??????"),
              fieldWithPath("data.qrType").type(JsonFieldType.STRING).description("QR ?????? ??????"),
              fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????????")
            )
          )
        )
      );
  }

  @Test
  @DisplayName("QR ??????(??????) ????????? ?????? ?????????")
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
      get("/api/v1/business/{business-id}/type/reservation/qr-code?page={page}&size={size}", businessId, page, size)
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
            parameterWithName("business-id").description("?????? ?????????")
          ),
          requestParameters(
            parameterWithName("page").description("????????? ???"),
            parameterWithName("size").description("????????? ??????")
          ),
          responseFields(
            List.of(
              fieldWithPath("data").type(JsonFieldType.ARRAY).description("?????? ?????????"),
              fieldWithPath("data[].qrCodeId").type(JsonFieldType.NUMBER).description("QR ?????? ?????????"),
              fieldWithPath("data[].target").type(JsonFieldType.STRING).description("?????? ??????"),
              fieldWithPath("data[].qrType").type(JsonFieldType.STRING).description("QR ?????? ??????"),
              fieldWithPath("data[].qrCodeImg").type(JsonFieldType.STRING).description("QR ?????? ?????????").optional(),
              fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("????????? ??????"),
              fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("?????? ?????????"),
              fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("?????? ????????? ??????"),
              fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("??? ????????? ???"),
              fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("??? ????????? ???")
            )
          )
        )
      );
  }

  @Test
  @DisplayName("QR ?????? ?????? ?????????")
  public void deleteQrCodeTest() throws Exception {
    // given
    long businessId = 1L;
    long qrCodeId = 1L;

    doNothing().when(qrCodeService).deleteQrCode(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong());

    // when
    ResultActions actions = mockMvc.perform(
      delete("/api/v1/business/{business-id}/type/reservation/qr-code/{qr-code-id}", businessId, qrCodeId)
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
            parameterWithName("business-id").description("?????? ?????????"),
            parameterWithName("qr-code-id").description("QR ?????? ?????????")
          )
        )
      );
  }
}
