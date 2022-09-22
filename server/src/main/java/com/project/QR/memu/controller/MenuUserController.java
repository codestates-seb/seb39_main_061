package com.project.QR.memu.controller;

import com.project.QR.dto.MultiResponseWithPageInfoDto;
import com.project.QR.memu.entity.Menu;
import com.project.QR.memu.mapper.MenuMapper;
import com.project.QR.memu.service.MenuService;
import com.project.QR.security.MemberDetails;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/business/{business-id}/menu")
@AllArgsConstructor
public class MenuUserController {
  private final MenuService menuService;
  private final MenuMapper mapper;

  @GetMapping
  public ResponseEntity getMenuList(@Positive @PathVariable("business-id") long businessId,
                                    @Positive @PathParam("page") int page,
                                    @Positive @PathParam("size") int size) {
    Page<Menu> pageOfMenu = menuService.getUserMenuList(businessId, page - 1, size);
    List<Menu> menuList = pageOfMenu.getContent();

    return new ResponseEntity(new MultiResponseWithPageInfoDto<>(mapper.menuListToMenuInfoDtoList(menuList),
      pageOfMenu),
      HttpStatus.OK);
  }
}
