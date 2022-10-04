package com.project.QR.qrcode.mapper;

import com.project.QR.qrcode.dto.QrCodeResponseDto;
import com.project.QR.qrcode.entity.QrCode;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-29T20:29:33+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.16.1 (Azul Systems, Inc.)"
)
@Component
public class QrCodeMapperImpl implements QrCodeMapper {

    @Override
    public QrCodeResponseDto.ShortQrCodeInfoDto qrCodeToShortQrCodeInfoDto(QrCode qrCode) {
        if ( qrCode == null ) {
            return null;
        }

        QrCodeResponseDto.ShortQrCodeInfoDto.ShortQrCodeInfoDtoBuilder shortQrCodeInfoDto = QrCodeResponseDto.ShortQrCodeInfoDto.builder();

        if ( qrCode.getQrCodeId() != null ) {
            shortQrCodeInfoDto.qrCodeId( qrCode.getQrCodeId() );
        }
        shortQrCodeInfoDto.qrCodeImg( qrCode.getQrCodeImg() );
        shortQrCodeInfoDto.target( qrCode.getTarget() );
        shortQrCodeInfoDto.qrType( qrCode.getQrType() );

        return shortQrCodeInfoDto.build();
    }
}
