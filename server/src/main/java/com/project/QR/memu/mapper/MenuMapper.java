package com.project.QR.memu.mapper;

import com.project.QR.business.entity.Business;
import com.project.QR.member.entity.Member;
import com.project.QR.memu.dto.MenuRequestDto;
import com.project.QR.memu.dto.MenuResponseDto;
import com.project.QR.memu.entity.Menu;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MenuMapper {
  default Menu createMenuDtoToMenu(MenuRequestDto.CreateMenuDto createMenuDto) {
    Member member = new Member();
    member.setMemberId(createMenuDto.getMemberId());
    Business business = new Business();
    business.setBusinessId(createMenuDto.getBusinessId());
    member.setBusiness(business);
    business.setMember(member);
    Menu menu = new Menu();
    menu.setName(createMenuDto.getName());
    menu.setBusiness(business);
    menu.setPrice(createMenuDto.getPrice());
    return menu;
  }

  MenuResponseDto.MenuInfoDto menuToMenuInfoDto(Menu menu);

  default Menu updateMenuDtoToMenu(MenuRequestDto.UpdateMenuDto updateMenuDto) {
    Member member = new Member();
    member.setMemberId(updateMenuDto.getMemberId());
    Business business = new Business();
    business.setBusinessId(updateMenuDto.getBusinessId());
    member.setBusiness(business);
    business.setMember(member);
    Menu menu = new Menu();
    menu.setMenuId(updateMenuDto.getMenuId());
    menu.setName(updateMenuDto.getName());
    menu.setBusiness(business);
    menu.setPrice(updateMenuDto.getPrice());
    return menu;
  }

  default List<MenuResponseDto.MenuInfoDto> menuListToMenuInfoDtoList(List<Menu> menuList) {
    return menuList.stream()
      .map(this::menuToMenuInfoDto)
      .collect(Collectors.toList());
  }
}
