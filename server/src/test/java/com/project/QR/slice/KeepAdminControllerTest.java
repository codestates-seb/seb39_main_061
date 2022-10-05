package com.project.QR.slice;

import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import com.project.QR.helper.WithMockCustomUser;
import com.project.QR.keep.dto.KeepRequestDto;
import com.project.QR.keep.dto.KeepResponseDto;
import com.project.QR.keep.entity.Keep;
import com.project.QR.keep.mapper.KeepMapper;
import com.project.QR.keep.service.KeepService;
import com.project.QR.qrcode.dto.QrCodeRequestDto;
import com.project.QR.qrcode.dto.QrCodeResponseDto;
import com.project.QR.qrcode.entity.QrCode;
import com.project.QR.stub.KeepStubData;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WithMockCustomUser
@WebMvcTest(KeepAdminControllerTest.class)
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class KeepAdminControllerTest {
  @Autowired
  private MockMvc mockmvc;

  @Autowired
  private Gson gson;

  @MockBean
  KeepService keepService;

  @MockBean
  KeepMapper mapper;

  @Test
  @DisplayName("자재 등록 테스트")
  public void createKeepTest() throws Exception {
    //given
    long businessId = 1L, qrCodeId = 1L;
    KeepRequestDto.CreateKeepDto createKeepDto = KeepStubData.createKeepDto();
    Keep keep = KeepStubData.keep(1L, "apple", "충주산", 10);
    KeepResponseDto.KeepInfoDto keepInfoDto = KeepStubData.keepInfoDto(keep);
    String content = gson.toJson(createKeepDto);

    given(mapper.createKeepDtoToKeep(Mockito.any(KeepRequestDto.CreateKeepDto.class))).willReturn(new Keep());
    given(keepService.createKeep(Mockito.any(Keep.class))).willReturn(keep);
    given(mapper.keepToKeepInfoDto(Mockito.any(Keep.class))).willReturn(keepInfoDto);

    //when
    ResultActions actions = mockmvc.perform(
            post("/api/v1/business/{business-id}/keep/qr-code/{qr-code-id}", businessId, qrCodeId)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer {ACCESS_TOKEN}")
                  //  .content(objectMapper.writeValueAsString(createKeepDto))
    );

    //then
    actions
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.data.keepId").value(keepInfoDto.getKeepId()))
            .andExpect(jsonPath("$.data.target").value(keepInfoDto.getTarget()))
            .andExpect(jsonPath("$.data.info").value(keepInfoDto.getInfo()))
            .andExpect(jsonPath("$.data.count").value(keepInfoDto.getCount()))
            .andExpect(jsonPath("$.message").value("CREATED"))
            .andDo(
                    document(
                            "keep-create",
                            getRequestPreProcessor(),
                            getResponsePreProcessor(),
                            requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
                            pathParameters(
                                    parameterWithName("business-id").description("매장 식별자"),
                                    parameterWithName("qrCode-id").description("QR 코드 식별자")
                            ),
                            requestFields(
                                    List.of(
                                            fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자").ignored(),
                                            fieldWithPath("businessId").type(JsonFieldType.NUMBER).description("매장 식별자").ignored(),
                                            fieldWithPath("qrCodeId").type(JsonFieldType.NUMBER).description("QR 코드 식별자").ignored(),
                                            fieldWithPath("target").type(JsonFieldType.STRING).description("관리 대상"),
                                            fieldWithPath("info").type(JsonFieldType.STRING).description("자재 정보"),
                                            fieldWithPath("count").type(JsonFieldType.NUMBER).description("자재 개수")
                                    )
                            ),
                            responseFields(
                                    List.of(
                                            fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                            fieldWithPath("data.keepId").type(JsonFieldType.NUMBER).description("자재 식별자"),
                                            fieldWithPath("data.target").type(JsonFieldType.STRING).description("관리 대상"),
                                            fieldWithPath("data.info").type(JsonFieldType.STRING).description("자재 정보"),
                                            fieldWithPath("data.count").type(JsonFieldType.NUMBER).description("자재 개수"),
                                            fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("자재 등록일"),
                                            fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("자재 수정일"),
                                            fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지")
                                    )
                            )
                    )
            );
  }
  @Test
  @DisplayName(" 자재 정보 변경 테스트")
  public void updateKeepTest() throws Exception {
    // given
    long businessId = 1L, qrCodeId = 1L, keepId = 1L;
    KeepRequestDto.UpdateKeepDto updateKeepDto = KeepStubData.updateKeepDto();
    Keep keep = KeepStubData.keep(1L,   "사과", "원산지: 충주", 10);
    KeepResponseDto.KeepInfoDto keepInfoDto = KeepStubData.keepInfoDto(keep);
    String content = gson.toJson(updateKeepDto);

    given(mapper.updateKeepDtoToKeep(Mockito.any(KeepRequestDto.UpdateKeepDto.class))).willReturn(new Keep());
    given(keepService.updateKeep(Mockito.any(Keep.class))).willReturn(keep);
    given(mapper.keepToKeepInfoDto(Mockito.any(Keep.class))).willReturn(keepInfoDto);

    // when
    ResultActions actions = mockmvc.perform(
            patch("/api/v1/business/{business-id}/keep/qr-code/{qr-code-id}/{keep-id}", businessId, qrCodeId)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer {ACCESS_TOKEN}")
    );

    // then
    actions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.keepId").value(keepInfoDto.getKeepId()))
            .andExpect(jsonPath("$.data.target").value(keepInfoDto.getTarget()))
            .andExpect(jsonPath("$.data.info").value(keepInfoDto.getInfo()))
            .andExpect(jsonPath("$.data.count").value(keepInfoDto.getCount()))
            .andExpect(jsonPath("$.message").value("SUCCESS"))
            .andDo(
                    document(
                            "keep-update",
                            getRequestPreProcessor(),
                            getResponsePreProcessor(),
                            requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
                            pathParameters(
                                    parameterWithName("business-id").description("매장 식별자"),
                                    parameterWithName("qr-code-id").description("QR 코드 식별자"),
                                    parameterWithName("keep-id").description("자재 식별자")
                            ),
                            requestFields(
                                    List.of(
                                            fieldWithPath("keepId").type(JsonFieldType.NUMBER).description("자재 식별자").ignored(),
                                            fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자").ignored(),
                                            fieldWithPath("businessId").type(JsonFieldType.NUMBER).description("매장 식별자").ignored(),
                                            fieldWithPath("qrCodeId").type(JsonFieldType.NUMBER).description("QR 코드 식별자").ignored(),
                                            fieldWithPath("target").type(JsonFieldType.STRING).description("관리 대상"),
                                            fieldWithPath("info").type(JsonFieldType.STRING).description("자재 정보"),
                                            fieldWithPath("count").type(JsonFieldType.NUMBER).description("자재 개수")
                                    )
                            ),
                            responseFields(
                                    List.of(
                                            fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                            fieldWithPath("data.keepId").type(JsonFieldType.NUMBER).description("자재 식별자"),
                                            fieldWithPath("data.target").type(JsonFieldType.STRING).description("관리 대상"),
                                            fieldWithPath("data.info").type(JsonFieldType.STRING).description("자재 정보"),
                                            fieldWithPath("data.count").type(JsonFieldType.NUMBER).description("자재 개수"),
                                            fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("자재 등록일"),
                                            fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("자재 수정일"),
                                            fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지")
                                    )
                            )
                    )
            );
  }

  @Test
  @DisplayName("자재 리스트 조회 테스트")
  public void getKeepTest() throws Exception {
    // given
    long businessId = 1L, qrCodeId = 1L;
    int page = 1;
    int size = 10;

    Page<Keep> pageOfKeep = KeepStubData.getKeepPage(page - 1, size);
    List<Keep> keepList = pageOfKeep.getContent();
    List<KeepResponseDto.KeepInfoDto> keepInfoDtoList = KeepStubData.keepInfoDtoList(keepList);

    given(keepService.getAdminKeepList(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt())).willReturn(pageOfKeep);
    given(mapper.keepListToKeepInfoDtoList(Mockito.anyList())).willReturn(keepInfoDtoList);

    // when
    ResultActions actions = mockmvc.perform(
            get("/api/v1/business/{business-id}/keep/qr-code/{qr-code-id}?page={page}&size={size}", businessId, qrCodeId, page, size)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer {ACCESS_TOKEN}")
    );

    // then
    actions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.pageInfo.page").value(page))
            .andExpect(jsonPath("$.pageInfo.size").value(size))
            .andExpect(jsonPath("$.pageInfo.totalElements").value(pageOfKeep.getTotalElements()))
            .andDo(
                    document(
                            "keep-list-get-admin",
                            getRequestPreProcessor(),
                            getResponsePreProcessor(),
                            requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
                            pathParameters(
                                    parameterWithName("business-id").description("매장 식별자"),
                                    parameterWithName("qrCode-id").description("QR 코드 식별자")
                            ),
                            requestParameters(
                                    parameterWithName("page").description("페이지 수"),
                                    parameterWithName("size").description("페이지 크기")
                            ),
                            responseFields(
                                    List.of(
                                            fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                            fieldWithPath("data.keepId").type(JsonFieldType.NUMBER).description("자재 식별자"),
                                            fieldWithPath("data.target").type(JsonFieldType.STRING).description("관리 대상"),
                                            fieldWithPath("data.info").type(JsonFieldType.STRING).description("자재 정보"),
                                            fieldWithPath("data.count").type(JsonFieldType.NUMBER).description("자재 개수"),
                                            fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("자재 등록일"),
                                            fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("자재 수정일"),
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
  @DisplayName("자재 삭제 테스트")
  public void deleteKeepTest() throws Exception {
    // given
    long businessId = 1L, qrCodeId = 1L, keepId = 1L;

    doNothing().when(keepService).deleteKeep(Mockito.any());
    // when
    ResultActions actions = mockmvc.perform(
            patch("/api/v1/business/{business-id}/keep/qr-code/{qr-code-id}/{keep-id}/delete", businessId, qrCodeId, keepId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer {ACCESS_TOKEN}")
    );

    // then
    actions
            .andExpect(status().isNoContent())
            .andDo(
                    document(
                            "keep-delete",
                            getRequestPreProcessor(),
                            getResponsePreProcessor(),
                            requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
                            pathParameters(
                                    parameterWithName("business-id").description("매장 식별자"),
                                    parameterWithName("qr-code-id").description("QR 코드 식별자"),
                                    parameterWithName("keep-id").description("자재 식별자")
                            )
                    )
            );
  }
  }