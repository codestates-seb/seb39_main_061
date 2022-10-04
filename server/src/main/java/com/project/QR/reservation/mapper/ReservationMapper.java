package com.project.QR.reservation.mapper;

import com.project.QR.business.entity.Business;
import com.project.QR.qrcode.entity.QrCode;
import com.project.QR.reservation.dto.ReservationRequestDto;
import com.project.QR.reservation.dto.ReservationResponseDto;
import com.project.QR.reservation.dto.Statistics;
import com.project.QR.reservation.entity.Check;
import com.project.QR.reservation.entity.Reservation;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
  default Reservation createReservationToReservation(ReservationRequestDto.CreateReservationDto createReservationDto) {
    Business business = new Business();
    business.setBusinessId(createReservationDto.getBusinessId());
    QrCode qrCode = new QrCode();
    qrCode.setBusiness(business);
    qrCode.setQrCodeId(createReservationDto.getQrCodeId());
    Reservation reservation = new Reservation();
    reservation.setCount(createReservationDto.getCount());
    reservation.setName(createReservationDto.getName());
    reservation.setPhone(createReservationDto.getPhone());
    reservation.setDeleted(Check.N);
    reservation.setCompleted(Check.N);
    reservation.setQrCode(qrCode);
    return reservation;
  }

  default Reservation updateReservationToReservation(ReservationRequestDto.UpdateReservationDto updateReservationDto) {
    Business business = new Business();
    business.setBusinessId(updateReservationDto.getBusinessId());
    QrCode qrCode = new QrCode();
    qrCode.setBusiness(business);
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
      .completed(reservation.getCompleted())
      .reservationId(reservation.getReservationId())
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

//  default ReservationResponseDto.StatisticsInfoDto statisticsToStatistics(Statistics statistics) {
//    return ReservationResponseDto.StatisticsInfoDto.builder()
//      .count(statistics.getCount())
//      .date(statistics.getDate())
//      .deleted(statistics.getDeleted())
//      .build();
//  }
//
//  default List<ReservationResponseDto.StatisticsInfoDto> statisticsListToStatisticsInfoList(List<Statistics> statisticsList) {
//    return statisticsList.stream()
//      .map(this::statisticsToStatistics)
//      .collect(Collectors.toList());
//  }
}
