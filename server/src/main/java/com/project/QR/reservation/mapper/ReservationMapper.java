package com.project.QR.reservation.mapper;

import com.project.QR.qrcode.entity.QrCode;
import com.project.QR.reservation.dto.ReservationRequestDto;
import com.project.QR.reservation.dto.ReservationResponseDto;
import com.project.QR.reservation.entity.Reservation;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
  default Reservation createReservationToReservation(ReservationRequestDto.CreateReservationDto createReservationDto) {
    QrCode qrCode = new QrCode();
    qrCode.setQrCodeId(createReservationDto.getQrCodeId());
    Reservation reservation = new Reservation();
    reservation.setCount(createReservationDto.getCount());
    reservation.setName(createReservationDto.getName());
    reservation.setPhone(createReservationDto.getPhone());
    reservation.setQrCode(qrCode);
    return reservation;
  }

  default Reservation updateReservationToReservation(ReservationRequestDto.UpdateReservationDto updateReservationDto) {
    QrCode qrCode = new QrCode();
    qrCode.setQrCodeId(updateReservationDto.getQrCodeId());
    Reservation reservation = new Reservation();
    reservation.setReservationId(updateReservationDto.getReservationId());
    reservation.setCount(updateReservationDto.getCount());
    reservation.setName(updateReservationDto.getName());
    reservation.setPhone(updateReservationDto.getPhone());
    reservation.setQrCode(qrCode);
    return reservation;
  }

  default ReservationResponseDto.ReservationInfoDto reservationToReservationInfoDto(Reservation reservation) {
    return ReservationResponseDto.ReservationInfoDto.builder()
      .complete(reservation.isComplete())
      .reserveId(reservation.getReservationId())
      .count(reservation.getCount())
      .createdAt(reservation.getCreatedAt())
      .name(new StringBuffer(reservation.getName()).replace(1, 2, "*").toString())
      .phone(reservation.getPhone().replaceAll("-\\d{4}-", "-****-"))
      .build();
  }

  default List<ReservationResponseDto.ReservationInfoDto> reservationListToReservationInfoDtoList(List<Reservation> reservationList) {
    return reservationList.stream()
      .map(this::reservationToReservationInfoDto)
      .collect(Collectors.toList());
  }
}
