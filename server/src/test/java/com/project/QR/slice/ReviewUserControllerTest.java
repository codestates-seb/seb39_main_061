package com.project.QR.slice;

import com.google.gson.Gson;
import com.project.QR.review.controller.ReviewUserController;
import com.project.QR.review.dto.ReviewRequestDto;
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
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewUserController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class ReviewUserControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private Gson gson;

  @MockBean
  private ReviewService reviewService;

  @MockBean
  private ReviewMapper mapper;

  @Test
  @DisplayName("?????? ?????? ?????????")
  public void createReviewTest() throws Exception {
    // given
    long businessId = 1L;
    ReviewRequestDto.CreateReviewDto createReviewDto = ReviewStubData.createReviewDto();
    Review review = ReviewStubData.review(1L, "review", 5);
    ReviewResponseDto.ReviewInfoDto reviewInfoDto = ReviewStubData.reviewInfoDto(review);
    String content = gson.toJson(createReviewDto);

    given(mapper.createReviewDtoToReview(Mockito.any(ReviewRequestDto.CreateReviewDto.class))).willReturn(new Review());
    given(reviewService.createReview(Mockito.any(Review.class))).willReturn(review);
    given(mapper.reviewToReviewInfoDto(Mockito.any(Review.class))).willReturn(reviewInfoDto);

    // when
    ResultActions actions = mockMvc.perform(
      post("/business/{business-id}/review", businessId)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(content)
    );

    // then
    actions
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.data.reviewId").value(reviewInfoDto.getReviewId()))
      .andExpect(jsonPath("$.data.contents").value(reviewInfoDto.getContents()))
      .andExpect(jsonPath("$.data.score").value(reviewInfoDto.getScore()))
      .andExpect(jsonPath("$.message").value("CREATED"))
      .andDo(
        document(
          "review-create",
          getRequestPreProcessor(),
          getResponsePreProcessor(),
          pathParameters(
            parameterWithName("business-id").description("?????? ?????????")
          ),
          requestFields(
            List.of(
              fieldWithPath("businessId").type(JsonFieldType.NUMBER).description("?????? ?????????").ignored(),
              fieldWithPath("contents").type(JsonFieldType.STRING).description("?????? ??????"),
              fieldWithPath("score").type(JsonFieldType.NUMBER).description("?????? ??????")
            )
          ),
          responseFields(
            List.of(
              fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
              fieldWithPath("data.reviewId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
              fieldWithPath("data.contents").type(JsonFieldType.STRING).description("?????? ??????"),
              fieldWithPath("data.score").type(JsonFieldType.NUMBER).description("?????? ??????"),
              fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????????")
            )
          )
        )
      );
  }

  @Test
  @DisplayName("?????? ????????? ?????? ?????????")
  public void getReviewListTest() throws Exception {
    // given
    long businessId = 1L;
    int page = 1;
    int size = 110;
    Page<Review> pageOfReview = ReviewStubData.getReviewList(page - 1, size);
    List<Review> reviewList = pageOfReview.getContent();
    List<ReviewResponseDto.ReviewInfoDto> reviewInfoDtoList = ReviewStubData.reviewInfoDtoList(reviewList);

    given(reviewService.getUserReviewList(Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt())).willReturn(pageOfReview);
    given(mapper.reviewListToReviewInfoDtoList(Mockito.anyList())).willReturn(reviewInfoDtoList);

    // when
    ResultActions actions = mockMvc.perform(
      get("/business/{business-id}/review?page={page}&size={size}", businessId, page, size)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
    );

    // then
    actions
      .andExpect(status().isOk())
      .andDo(
        document(
          "review-list-get-user",
          getRequestPreProcessor(),
          getResponsePreProcessor(),
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
              fieldWithPath("data[].reviewId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
              fieldWithPath("data[].contents").type(JsonFieldType.STRING).description("?????? ??????"),
              fieldWithPath("data[].score").type(JsonFieldType.NUMBER).description("?????? ??????"),
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
  @DisplayName("?????? ?????? ?????????")
  public void getReviewTest() throws Exception {
    // given
    long businessId = 1L, reviewId = 1L;

    Review review = ReviewStubData.review(1L, "review", 5);
    ReviewResponseDto.ReviewInfoDto reviewInfoDto = ReviewStubData.reviewInfoDto(review);

    given(reviewService.getUserReview(Mockito.anyLong(), Mockito.anyLong())).willReturn(review);
    given(mapper.reviewToReviewInfoDto(Mockito.any(Review.class))).willReturn(reviewInfoDto);

    // when
    ResultActions actions = mockMvc.perform(
      get("/business/{business-id}/review/{review-id}", businessId, reviewId)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
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
          "review-get-user",
          getRequestPreProcessor(),
          getResponsePreProcessor(),
          pathParameters(
            parameterWithName("business-id").description("?????? ?????????"),
            parameterWithName("review-id").description("?????? ?????????")
          ),
          responseFields(
            List.of(
              fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
              fieldWithPath("data.reviewId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
              fieldWithPath("data.contents").type(JsonFieldType.STRING).description("?????? ??????"),
              fieldWithPath("data.score").type(JsonFieldType.NUMBER).description("?????? ??????"),
              fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????????")
            )
          )
        )
      );
  }
}
