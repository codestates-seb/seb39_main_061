package com.project.QR.slice;

import com.google.gson.Gson;
import com.project.QR.helper.WithMockCustomUser;
import com.project.QR.memu.controller.MenuAdminController;
import com.project.QR.memu.controller.MenuUserController;
import com.project.QR.memu.dto.MenuResponseDto;
import com.project.QR.memu.entity.Menu;
import com.project.QR.memu.mapper.MenuMapper;
import com.project.QR.memu.service.MenuService;
import com.project.QR.stub.MenuStubData;
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

@WebMvcTest(MenuUserController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class MenuUserControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private Gson gson;

  @MockBean
  private MenuService menuService;

  @MockBean
  private MenuMapper mapper;

  @Test
  @DisplayName("메뉴 리스트 조회")
  public void getMenuListTest() throws Exception {
    // given
    long businessId = 1L;
    int page = 1, size = 10;
    Page<Menu> pageOfMenu = MenuStubData.getMenuList(page - 1, size);
    List<Menu> menuList = pageOfMenu.getContent();
    List<MenuResponseDto.MenuInfoDto> menuInfoDtoList = MenuStubData.menuInfoDtoList(menuList);

    given(menuService.getUserMenuList(Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt()))
      .willReturn(pageOfMenu);
    given(mapper.menuListToMenuInfoDtoList(Mockito.anyList())).willReturn(menuInfoDtoList);

    // when
    ResultActions actions = mockMvc.perform(
      get("/business/{business-id}/menu?page={page}&size={size}", businessId, page, size)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
    );

    // then
    actions
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.pageInfo.page").value(page))
      .andExpect(jsonPath("$.pageInfo.size").value(size))
      .andExpect(jsonPath("$.pageInfo.totalElements").value(pageOfMenu.getTotalElements()))
      .andDo(
        document(
          "get-menu-list-user",
          getRequestPreProcessor(),
          getResponsePreProcessor(),
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
              fieldWithPath("data[].menuId").type(JsonFieldType.NUMBER).description("음식 식별자"),
              fieldWithPath("data[].name").type(JsonFieldType.STRING).description("음식명"),
              fieldWithPath("data[].price").type(JsonFieldType.NUMBER).description("가격"),
              fieldWithPath("data[].img").type(JsonFieldType.STRING).description("음식 이미지"),
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
}
