package com.project.QR.memu.mapper;

import com.project.QR.memu.dto.MenuResponseDto;
import com.project.QR.memu.entity.Menu;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-29T20:29:33+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.16.1 (Azul Systems, Inc.)"
)
@Component
public class MenuMapperImpl implements MenuMapper {

    @Override
    public MenuResponseDto.MenuInfoDto menuToMenuInfoDto(Menu menu) {
        if ( menu == null ) {
            return null;
        }

        MenuResponseDto.MenuInfoDto.MenuInfoDtoBuilder menuInfoDto = MenuResponseDto.MenuInfoDto.builder();

        menuInfoDto.menuId( menu.getMenuId() );
        menuInfoDto.name( menu.getName() );
        menuInfoDto.price( menu.getPrice() );
        menuInfoDto.img( menu.getImg() );

        return menuInfoDto.build();
    }
}
