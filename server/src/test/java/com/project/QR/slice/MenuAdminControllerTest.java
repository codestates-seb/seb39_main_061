package com.project.QR.slice;

import com.google.gson.Gson;
import com.project.QR.dto.MultiResponseWithPageInfoDto;
import com.project.QR.dto.SingleResponseWithMessageDto;
import com.project.QR.helper.WithMockCustomUser;
import com.project.QR.memu.controller.MenuAdminController;
import com.project.QR.memu.dto.MenuRequestDto;
import com.project.QR.memu.dto.MenuResponseDto;
import com.project.QR.memu.entity.Menu;
import com.project.QR.memu.mapper.MenuMapper;
import com.project.QR.memu.service.MenuService;
import com.project.QR.qrcode.controller.QrCodeReservationController;
import com.project.QR.security.MemberDetails;
import com.project.QR.stub.MemberStubData;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.websocket.server.PathParam;
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
@WebMvcTest(MenuAdminController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class MenuAdminControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private Gson gson;

  @MockBean
  private MenuService menuService;

  @MockBean
  private MenuMapper mapper;

  @Test
  @DisplayName("메뉴 등록 테스트")
  public void createMenuTest() throws Exception {
    // given
    long businessId = 1L;
    MenuRequestDto.CreateMenuDto createMenuDto = MenuStubData.createMenuDto();
    Menu menu = MenuStubData.menu(1L, "menu", 10000, "menu.png");
    MenuResponseDto.MenuInfoDto menuInfoDto = MenuStubData.menuInfoDto(menu);
    String content = gson.toJson(createMenuDto);
    MockMultipartFile dataJson = new MockMultipartFile("data", null,
      "application/json", content.getBytes());
    MockMultipartFile fileData = new MockMultipartFile("file", "menu.jpg", "image/jpeg",
      "menu".getBytes());

    given(mapper.createMenuDtoToMenu(Mockito.any(MenuRequestDto.CreateMenuDto.class))).willReturn(new Menu());
    given(menuService.createMenu(Mockito.any(Menu.class), Mockito.any(MultipartFile.class))).willReturn(menu);
    given(mapper.menuToMenuInfoDto(Mockito.any(Menu.class))).willReturn(menuInfoDto);

    // when
    ResultActions actions = mockMvc.perform(
      multipart("/api/v1/business/{business-id}/menu", businessId)
        .file(dataJson)
        .file(fileData)
        .accept(MediaType.APPLICATION_JSON, MediaType.MULTIPART_FORM_DATA)
        .header("Authorization", "Bearer {ACCESS_TOKEN}")
    );

    // then
    actions
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.data.menuId").value(menuInfoDto.getMenuId()))
      .andExpect(jsonPath("$.data.name").value(menuInfoDto.getName()))
      .andExpect(jsonPath("$.data.price").value(menuInfoDto.getPrice()))
      .andExpect(jsonPath("$.data.img").value(menuInfoDto.getImg()))
      .andExpect(jsonPath("$.message").value("CREATED"))
      .andDo(
        document(
          "menu-create",
          getRequestPreProcessor(),
          getResponsePreProcessor(),
          requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
          pathParameters(
            parameterWithName("business-id").description("사업 식별자")
          ),
          requestParts(
            partWithName("data").description("메뉴 등록 정보").optional(),
            partWithName("file").description("음식 이미지 파일").optional()
          ),
          requestPartFields("data", List.of(
            fieldWithPath("name").description("음식명"),
            fieldWithPath("price").description("가격").optional(),
            fieldWithPath("memberId").description("회원 식별자").ignored(),
            fieldWithPath("businessId").description("사업 식별자").ignored()
          )),
          responseFields(
            List.of(
              fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
              fieldWithPath("data.menuId").type(JsonFieldType.NUMBER).description("음식 식별자"),
              fieldWithPath("data.name").type(JsonFieldType.STRING).description("음식명"),
              fieldWithPath("data.price").type(JsonFieldType.NUMBER).description("가격"),
              fieldWithPath("data.img").type(JsonFieldType.STRING).description("음식 이미지"),
              fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지")
            )
          )
        )
      );
  }

  @Test
  @DisplayName("메뉴 변경 테스트")
  public void updateMenuTest() throws Exception {
    // given
    long businessId = 1L, menuId = 1L;
    MenuRequestDto.UpdateMenuDto updateMenuDto = MenuStubData.updateMenuDto();
    Menu menu = MenuStubData.menu(1L, "update", 1000, "change.png");
    MenuResponseDto.MenuInfoDto menuInfoDto = MenuStubData.menuInfoDto(menu);
    String content = gson.toJson(updateMenuDto);

    MockMultipartFile dataJson = new MockMultipartFile("data", null,
      "application/json", content.getBytes());
    MockMultipartFile fileData = new MockMultipartFile("file", "change.png", "image/png",
      "change".getBytes());

    given(mapper.updateMenuDtoToMenu(Mockito.any(MenuRequestDto.UpdateMenuDto.class))).willReturn(new Menu());
    given(menuService.updateMenu(Mockito.any(Menu.class), Mockito.any(MultipartFile.class))).willReturn(menu);
    given(mapper.menuToMenuInfoDto(Mockito.any(Menu.class))).willReturn(menuInfoDto);

    // when
    ResultActions actions = mockMvc.perform(
      multipart("/api/v1/business/{business-id}/menu/{menu-id}", businessId, menuId)
        .file(dataJson)
        .file(fileData)
        .accept(MediaType.APPLICATION_JSON, MediaType.MULTIPART_FORM_DATA)
        .header("Authorization", "Bearer {ACCESS_TOKEN}")
    );

    // then
    actions
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.menuId").value(menuInfoDto.getMenuId()))
      .andExpect(jsonPath("$.data.name").value(menuInfoDto.getName()))
      .andExpect(jsonPath("$.data.price").value(menuInfoDto.getPrice()))
      .andExpect(jsonPath("$.data.img").value(menuInfoDto.getImg()))
      .andExpect(jsonPath("$.message").value("SUCCESS"))
      .andDo(
        document(
          "menu-update",
          getRequestPreProcessor(),
          getResponsePreProcessor(),
          requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
          pathParameters(
            parameterWithName("business-id").description("사업 식별자"),
            parameterWithName("menu-id").description("메뉴 식별자")
          ),
          requestParts(
            partWithName("data").description("메뉴 등록 정보").optional(),
            partWithName("file").description("음식 이미지 파일").optional()
          ),
          requestPartFields("data", List.of(
            fieldWithPath("name").description("음식명"),
            fieldWithPath("price").description("가격").optional(),
            fieldWithPath("menuId").description("메뉴 식별자").ignored(),
            fieldWithPath("memberId").description("회원 식별자").ignored(),
            fieldWithPath("businessId").description("사업 식별자").ignored()
          )),
          responseFields(
            List.of(
              fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
              fieldWithPath("data.menuId").type(JsonFieldType.NUMBER).description("음식 식별자"),
              fieldWithPath("data.name").type(JsonFieldType.STRING).description("음식명"),
              fieldWithPath("data.price").type(JsonFieldType.NUMBER).description("가격"),
              fieldWithPath("data.img").type(JsonFieldType.STRING).description("음식 이미지"),
              fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지")
            )
          )
        )
      );

  }

  @Test
  @DisplayName("메뉴 리스트 조회")
  public void getMenuListTest() throws Exception {
    // given
    long businessId = 1L;
    int page = 1, size = 10;
    Page<Menu> pageOfMenu = MenuStubData.getMenuList(page - 1, size);
    List<Menu> menuList = pageOfMenu.getContent();
    List<MenuResponseDto.MenuInfoDto> menuInfoDtoList = MenuStubData.menuInfoDtoList(menuList);

    given(menuService.getAdminMenuList(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt()))
      .willReturn(pageOfMenu);
    given(mapper.menuListToMenuInfoDtoList(Mockito.anyList())).willReturn(menuInfoDtoList);

    // when
    ResultActions actions = mockMvc.perform(
      get("/api/v1/business/{business-id}/menu?page={page}&size={size}", businessId, page, size)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer {ACCESS_TOKEN}")
    );

    // then
    actions
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.pageInfo.page").value(page))
      .andExpect(jsonPath("$.pageInfo.size").value(size))
      .andExpect(jsonPath("$.pageInfo.totalElements").value(pageOfMenu.getTotalElements()))
      .andDo(
        document(
          "get-menu-list-admin",
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

  @Test
  @DisplayName("메뉴 조회 테스트")
  public void getMenuTest() throws Exception {
    // given
    long businessId = 1L, menuId = 1L;
    Menu menu = MenuStubData.menu(menuId, "menu", 1000, "menu.png");
    MenuResponseDto.MenuInfoDto menuInfoDto = MenuStubData.menuInfoDto(menu);

    given(menuService.findMenu(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong())).willReturn(new Menu());
    given(mapper.menuToMenuInfoDto(Mockito.any(Menu.class))).willReturn(menuInfoDto);

    // when
    ResultActions actions = mockMvc.perform(
      get("/api/v1/business/{business-id}/menu/{menu-id}", businessId, menuId)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer {ACCESS_TOKEN}")
    );

    // then
    actions
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.menuId").value(menuInfoDto.getMenuId()))
      .andExpect(jsonPath("$.data.name").value(menuInfoDto.getName()))
      .andExpect(jsonPath("$.data.price").value(menuInfoDto.getPrice()))
      .andExpect(jsonPath("$.data.img").value(menuInfoDto.getImg()))
      .andExpect(jsonPath("$.message").value("SUCCESS"))
      .andDo(
        document(
          "menu-get",
          getRequestPreProcessor(),
          getResponsePreProcessor(),
          requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
          pathParameters(
            parameterWithName("business-id").description("사업 식별자"),
            parameterWithName("menu-id").description("메뉴 식별자")
          ),
          responseFields(
            List.of(
              fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
              fieldWithPath("data.menuId").type(JsonFieldType.NUMBER).description("음식 식별자"),
              fieldWithPath("data.name").type(JsonFieldType.STRING).description("음식명"),
              fieldWithPath("data.price").type(JsonFieldType.NUMBER).description("가격"),
              fieldWithPath("data.img").type(JsonFieldType.STRING).description("음식 이미지"),
              fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지")
            )
          )
        )
      );
  }

  @Test
  @DisplayName("메뉴 삭제 테스트")
  public void deleteMenuTest() throws Exception {
    // given
    long businessId = 1L, menuId = 1L;
    doNothing().when(menuService).deleteMenu(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong());

    // when
    ResultActions actions = mockMvc.perform(
      delete("/api/v1/business/{business-id}/menu/{menu-id}", businessId, menuId)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer {ACCESS_TOKEN}")
    );

    // then
    actions
      .andExpect(status().isNoContent())
      .andDo(
        document(
          "menu-delete",
          getRequestPreProcessor(),
          getResponsePreProcessor(),
          requestHeaders(headerWithName("Authorization").description("Bearer AccessToken")),
          pathParameters(
            parameterWithName("business-id").description("사업 식별자"),
            parameterWithName("menu-id").description("메뉴 식별자")
          )
        )
      );
  }
}
