package com.project.QR.memu.controller;

import com.project.QR.dto.MultiResponseWithPageInfoDto;
import com.project.QR.dto.SingleResponseWithMessageDto;
import com.project.QR.memu.dto.MenuRequestDto;
import com.project.QR.memu.entity.Menu;
import com.project.QR.memu.mapper.MenuMapper;
import com.project.QR.memu.service.MenuService;
import com.project.QR.security.MemberDetails;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.websocket.server.PathParam;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/business/{business-id}/menu")
@AllArgsConstructor
public class MenuAdminController {
  private final MenuService menuService;
  private final MenuMapper mapper;

  /**
   * 메뉴 등록 API
   */
  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity createMenu(@AuthenticationPrincipal MemberDetails memberDetails,
                                   @Positive @PathVariable("business-id") long businessId,
                                   @Valid @RequestPart("data") MenuRequestDto.CreateMenuDto createMenuDto,
                                   @RequestPart(value = "file", required = false) MultipartFile multipartFile) {
    createMenuDto.setMemberId(memberDetails.getMember().getMemberId());
    createMenuDto.setBusinessId(businessId);

    Menu menu = menuService.createMenu(mapper.createMenuDtoToMenu(createMenuDto), multipartFile);

    return new ResponseEntity(new SingleResponseWithMessageDto<>(mapper.menuToMenuInfoDto(menu),
      "CREATED"),
      HttpStatus.CREATED);
  }

  /**
   * 메뉴 변경 API
   */
  @PostMapping(value = "/{menu-id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity updateMenu(@AuthenticationPrincipal MemberDetails memberDetails,
                                   @Positive @PathVariable("business-id") long businessId,
                                   @Positive @PathVariable("menu-id") long menuId,
                                   @Valid @RequestPart("data") MenuRequestDto.UpdateMenuDto updateMenuDto,
                                   @RequestPart(value = "file", required = false) MultipartFile multipartFile) {
    updateMenuDto.setMemberId(memberDetails.getMember().getMemberId());
    updateMenuDto.setBusinessId(businessId);
    updateMenuDto.setMenuId(menuId);
    Menu menu = menuService.updateMenu(mapper.updateMenuDtoToMenu(updateMenuDto), multipartFile);

    return new ResponseEntity(new SingleResponseWithMessageDto<>(mapper.menuToMenuInfoDto(menu),
      "SUCCESS"),
      HttpStatus.CREATED);
  }

  /**
   * 메뉴 리스트 조회
   */
  @GetMapping
  public ResponseEntity getMenuList(@AuthenticationPrincipal MemberDetails memberDetails,
                                    @Positive @PathVariable("business-id") long businessId,
                                    @Positive @PathParam("page") int page,
                                    @Positive @PathParam("size") int size) {
    Page<Menu> pageOfMenu = menuService.getAdminMenuList(businessId, memberDetails.getMember().getMemberId(),
      page - 1, size);
    List<Menu> menuList = pageOfMenu.getContent();

    return new ResponseEntity(new MultiResponseWithPageInfoDto<>(mapper.menuListToMenuInfoDtoList(menuList),
      pageOfMenu),
      HttpStatus.OK);
  }

  /**
   * 특정 메뉴 조회
   */
  @GetMapping("/{menu-id}")
  public ResponseEntity getMenu(@AuthenticationPrincipal MemberDetails memberDetails,
                                @Positive @PathVariable("business-id") long businessId,
                                @Positive @PathVariable("menu-id") long menuId) {
    Menu menu = menuService.findMenu(menuId, businessId, memberDetails.getMember().getMemberId());

    return new ResponseEntity(new SingleResponseWithMessageDto<>(mapper.menuToMenuInfoDto(menu),
      "SUCCESS"),
      HttpStatus.OK);
  }

  /**
   * 메뉴 삭제
   */
  @DeleteMapping("/{menu-id}")
  public ResponseEntity deleteMenu(@AuthenticationPrincipal MemberDetails memberDetails,
                                @Positive @PathVariable("business-id") long businessId,
                                @Positive @PathVariable("menu-id") long menuId) {
    menuService.deleteMenu(menuId, businessId, memberDetails.getMember().getMemberId());

    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }
}
