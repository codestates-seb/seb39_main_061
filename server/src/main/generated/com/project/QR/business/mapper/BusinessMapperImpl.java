package com.project.QR.business.mapper;

import com.project.QR.business.dto.BusinessResponseDto;
import com.project.QR.business.entity.Business;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-29T20:29:33+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.16.1 (Azul Systems, Inc.)"
)
@Component
public class BusinessMapperImpl implements BusinessMapper {

    @Override
    public BusinessResponseDto.BusinessInfoDto businessToBusinessInfoDto(Business business) {
        if ( business == null ) {
            return null;
        }

        BusinessResponseDto.BusinessInfoDto.BusinessInfoDtoBuilder businessInfoDto = BusinessResponseDto.BusinessInfoDto.builder();

        businessInfoDto.businessId( business.getBusinessId() );
        businessInfoDto.name( business.getName() );
        businessInfoDto.introduction( business.getIntroduction() );
        businessInfoDto.openTime( business.getOpenTime() );
        businessInfoDto.holiday( business.getHoliday() );
        businessInfoDto.address( business.getAddress() );
        businessInfoDto.phone( business.getPhone() );
        businessInfoDto.lon( business.getLon() );
        businessInfoDto.lat( business.getLat() );

        return businessInfoDto.build();
    }
}
