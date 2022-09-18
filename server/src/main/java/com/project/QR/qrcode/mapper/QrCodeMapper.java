package com.project.QR.qrcode.mapper;

import com.project.QR.member.entity.Member;
import com.project.QR.qrcode.dto.QrCodeRequestDto;
import com.project.QR.qrcode.dto.QrCodeResponseDto;
import com.project.QR.qrcode.entity.QrCode;
import com.project.QR.qrcode.entity.QrType;
import com.project.QR.reservation.dto.ReservationResponseDto;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface QrCodeMapper {
  default QrCode createQrCodeDtoToQrCode (QrCodeRequestDto.CreateQrCodeDto createQrCodeDto) {
    Member member = new Member();
    member.setMemberId(createQrCodeDto.getMemberId());
    QrCode qrCode = new QrCode();
    qrCode.setMember(member);
    qrCode.setDueDate(createQrCodeDto.getDueDate());
    qrCode.setTarget(createQrCodeDto.getTarget());
    qrCode.setQrType(QrType.valueOf(createQrCodeDto.getQrType().toUpperCase()));
    return qrCode;
  }
  default QrCodeResponseDto.QrCodeInfoDto qrCodeToQrCodeInfoDto(QrCode qrCode) {
    return QrCodeResponseDto.QrCodeInfoDto.builder()
      .qrCodeId(qrCode.getQrCodeId())
      .qrCodeImg(qrCode.getQrCodeImg())
      .qrType(qrCode.getQrType())
      .target(qrCode.getTarget())
      .reservations(qrCode.getReservations().stream()
        .map(reservation ->  ReservationResponseDto.reservationInfoDto.builder()
          .complete(reservation.isComplete())
          .count(reservation.getCount())
          .createdAt(reservation.getCreatedAt())
          .name(reservation.getName())
          .phone(reservation.getPhone())
          .reserveId(reservation.getReservationId())
          .build())
        .collect(Collectors.toList())
      )
      .build();
  }
  default QrCode updateQrCodeDtoToQrCode(QrCodeRequestDto.UpdateQrCodeDto updateQrCodeDto) {
    Member member = new Member();
    member.setMemberId(updateQrCodeDto.getMemberId());
    QrCode qrCode = new QrCode();
    qrCode.setMember(member);
    qrCode.setDueDate(updateQrCodeDto.getDueDate());
    qrCode.setTarget(updateQrCodeDto.getTarget());
    qrCode.setQrCodeId(updateQrCodeDto.getQrCodeId());
    qrCode.setQrType(QrType.valueOf(updateQrCodeDto.getQrType().toUpperCase()));
    return qrCode;
  }
}