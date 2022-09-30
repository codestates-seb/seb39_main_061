package com.project.QR.slice;

import com.google.gson.Gson;
import com.project.QR.helper.WithMockCustomUser;
import com.project.QR.review.controller.ReviewAdminController;
import com.project.QR.review.dto.ReviewResponseDto;
import com.project.QR.review.entity.Review;
import com.project.QR.review.mapper.ReviewMapper;
import com.project.QR.review.service.ReviewService;
import com.project.QR.stub.ReviewStubData;
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
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockCustomUser
@WebMvcTest(ReviewAdminController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class ReviewAdminControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private Gson gson;

  @MockBean
  private ReviewService reviewService;

  @MockBean
  private ReviewMapper mapper;

  @Test
  @DisplayName("리뷰 리스트 조회 테스트")
  public void getReviewListTest() throws Exception {
    // given
    long businessId = 1L;
    int page = 1;
    int size = 110;
    Page<Review> pageOfReview = ReviewStubData.getReviewList(page - 1, size);
    List<Review> reviewList = pageOfReview.getContent();
    List<ReviewResponseDto.ReviewInfoDto> reviewInfoDtoList = ReviewStubData.reviewInfoDtoList(reviewList);

    given(reviewService.getAdminReviewList(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt()))
      .willReturn(pageOfReview);
    given(mapper.reviewListToReviewInfoDtoList(Mockito.anyList())).willReturn(reviewInfoDtoList);

    // when
    ResultActions actions = mockMvc.perform(
      get("/api/v1/business/{business-id}/review?page={page}&size={size}", businessId, page, size)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer {ACCESS_TOKEN}")
    );

    // then
    actions
      .andExpect(status().isOk())
      .andDo(
        document(
          "review-list-get-admin",
          getRequestPreProcessor(),
          getResponsePreProcessor(),
          requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
          pathParameters(
            parameterWithName("business-id").description("매장 식별자")
          ),
          requestParameters(
            parameterWithName("page").description("페이지 수"),
            parameterWithName("size").description("페이지 크기")
          ),
          responseFields(
            List.of(
              fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
              fieldWithPath("data[].reviewId").type(JsonFieldType.NUMBER).description("리뷰 식별자"),
              fieldWithPath("data[].contents").type(JsonFieldType.STRING).description("리뷰 내용"),
              fieldWithPath("data[].score").type(JsonFieldType.NUMBER).description("리뷰 점수"),
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
  @DisplayName("리뷰 조회 테스트")
  public void getReviewTest() throws Exception {
    // given
    long businessId = 1L, reviewId = 1L;

    Review review = ReviewStubData.review(1L, "review", 5);
    ReviewResponseDto.ReviewInfoDto reviewInfoDto = ReviewStubData.reviewInfoDto(review);

    given(reviewService.getAdminReview(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong())).willReturn(review);
    given(mapper.reviewToReviewInfoDto(Mockito.any(Review.class))).willReturn(reviewInfoDto);

    // when
    ResultActions actions = mockMvc.perform(
      get("/api/v1/business/{business-id}/review/{review-id}", businessId, reviewId)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer {ACCESS_TOKEN}")
    );

    // then
    actions
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.reviewId").value(reviewInfoDto.getReviewId()))
      .andExpect(jsonPath("$.data.contents").value(reviewInfoDto.getContents()))
      .andExpect(jsonPath("$.data.score").value(reviewInfoDto.getScore()))
      .andExpect(jsonPath("$.message").value("SUCCESS"))
      .andDo(
        document(
          "review-get-admin",
          getRequestPreProcessor(),
          getResponsePreProcessor(),
          requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
          pathParameters(
            parameterWithName("business-id").description("매장 식별자"),
            parameterWithName("review-id").description("리뷰 식별자")
          ),
          responseFields(
            List.of(
              fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
              fieldWithPath("data.reviewId").type(JsonFieldType.NUMBER).description("리뷰 식별자"),
              fieldWithPath("data.contents").type(JsonFieldType.STRING).description("리뷰 내용"),
              fieldWithPath("data.score").type(JsonFieldType.NUMBER).description("리뷰 점수"),
              fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지")
            )
          )
        )
      );
  }
}
