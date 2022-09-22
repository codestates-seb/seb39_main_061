package com.project.QR.stub;

import com.project.QR.business.entity.Business;
import com.project.QR.member.entity.Member;
import com.project.QR.memu.dto.MenuRequestDto;
import com.project.QR.memu.dto.MenuResponseDto;
import com.project.QR.memu.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

public class MenuStubData {
  private static final Member member = MemberStubData.member();
  private static final Business business = BusinessStubData.business();

  public static MenuRequestDto.CreateMenuDto createMenuDto() {
    return MenuRequestDto.CreateMenuDto.builder()
      .name("menu")
      .price(10000)
      .businessId(business.getBusinessId())
      .memberId(member.getMemberId())
      .build();
  }

  public static Menu menu(long menuId, String name, int price, String img) {
    Menu menu = new Menu();
    menu.setMenuId(menuId);
    menu.setBusiness(business);
    menu.setName(name);
    menu.setPrice(price);
    menu.setImg(img);
    return menu;
  }

  public static MenuResponseDto.MenuInfoDto menuInfoDto(Menu menu) {
    return MenuResponseDto.MenuInfoDto.builder()
      .menuId(menu.getMenuId())
      .img(menu.getImg())
      .name(menu.getName())
      .price(menu.getPrice())
      .build();
  }

  public static MenuRequestDto.UpdateMenuDto updateMenuDto() {
    return MenuRequestDto.UpdateMenuDto.builder()
      .businessId(business.getBusinessId())
      .menuId(1L)
      .memberId(member.getMemberId())
      .name("update")
      .price(1000)
      .build();
  }

  public static Page<Menu> getMenuList(int page, int size) {
    return new PageImpl<>(List.of(
      menu(1L, "menu1", 5000, "menu1.png"),
      menu(2L, "menu2", 15000, "menu2.png"),
      menu(3L, "menu3", 25000, "menu3.png")
    ), PageRequest.of(page, size, Sort.by("MENU_ID").descending()), 3);
  }

  public static List<MenuResponseDto.MenuInfoDto> menuInfoDtoList(List<Menu> menuList) {
    return menuList.stream()
      .map(MenuStubData::menuInfoDto)
      .collect(Collectors.toList());
  }
}
