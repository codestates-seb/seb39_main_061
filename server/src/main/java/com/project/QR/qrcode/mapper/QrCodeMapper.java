package com.project.QR.qrcode.mapper;

import com.project.QR.qrcode.dto.QrCodeRequestDto;
import com.project.QR.qrcode.dto.QrCodeResponseDto;
import com.project.QR.qrcode.entity.QrCode;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface QrCodeMapper {
  default QrCode createQrCodeDtoToQrCode (QrCodeRequestDto.CreateQrCodeDto createQrCodeDto) {
    QrCode qrCode = new QrCode();
    qrCode.setDueDate(createQrCodeDto.getDueDate());
    qrCode.setTarget(createQrCodeDto.getTarget());
    qrCode.setQrType(QrCode.QrType.valueOf(createQrCodeDto.getQrType().toUpperCase()));
    return qrCode;
  }
  QrCodeResponseDto.QrCodeInfoDto qrCodeToQrCodeInfoDto(QrCode qrCode);
  QrCode updateQrCodeDtoToQrCode(QrCodeRequestDto.UpdateQrCodeDto updateQrCodeDto);


}