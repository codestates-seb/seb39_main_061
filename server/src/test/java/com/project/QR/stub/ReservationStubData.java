package com.project.QR.stub;

import com.project.QR.qrcode.entity.QrCode;
import com.project.QR.reservation.dto.ReservationRequestDto;
import com.project.QR.reservation.dto.ReservationResponseDto;
import com.project.QR.reservation.entity.Check;
import com.project.QR.reservation.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationStubData {
  private static final QrCode qrCode = QrCodeStubData.qrCode();

  public static ReservationResponseDto.ReservationInfoDto reservationInfoDto(String phone, String name) {
    return ReservationResponseDto.ReservationInfoDto.builder()
      .phone(phone)
      .name(name)
      .reservationId(1L)
      .createdAt(LocalDateTime.now())
      .count(1)
      .completed(Check.Y)
      .build();
  }

  public static ReservationResponseDto.ReservationInfoDto reservationInfoDto(Reservation reservation) {
    return ReservationResponseDto.ReservationInfoDto.builder()
      .name(new StringBuffer(reservation.getName()).replace(1, 2, "*").toString())
      .phone(reservation.getPhone().replaceAll("-\\d{4}-", "-****-"))
      .reservationId(1L)
      .createdAt(LocalDateTime.now())
      .count(reservation.getCount())
      .completed(Check.Y)
      .build();
  }

  public static List<ReservationResponseDto.ReservationInfoDto> reservationInfoDtoList() {
    List<ReservationResponseDto.ReservationInfoDto> reservationInfoDtoList = new ArrayList<>();
    reservationInfoDtoList.add(reservationInfoDto("000-0000-0000", "name1"));
    reservationInfoDtoList.add(reservationInfoDto("000-1234-0000", "name2"));
    reservationInfoDtoList.add(reservationInfoDto("000-0000-5678", "name3"));
    return reservationInfoDtoList;
  }

  public static Reservation reservation(long reservationId, String phone, String name, int count) {
    Reservation reservation = new Reservation();
    reservation.setReservationId(reservationId);
    reservation.setCompleted(Check.N);
    reservation.setDeleted(Check.N);
    reservation.setPhone(phone);
    reservation.setName(name);
    reservation.setCount(count);
    reservation.setQrCode(qrCode);
    return reservation;
  }

  public static List<Reservation> reservationList() {
    List<Reservation> reservationList = new ArrayList<>();
    reservationList.add(reservation(1L, "000-0000-0000", "name1", 1));
    reservationList.add(reservation(2L, "000-1234-0000", "name2", 1));
    reservationList.add(reservation(3L, "000-0000-5678", "name3", 3));
    return reservationList;
  }

  public static ReservationRequestDto.CreateReservationDto createReservationDto() {
    return ReservationRequestDto.CreateReservationDto.builder()
      .businessId(1L)
      .count(2)
      .name("홍길동")
      .phone("000-0000-0000")
      .qrCodeId(1L)
      .build();
  }

  public static Page<Reservation> getReservationPage(int page, int size) {
    return new PageImpl<>(List.of(
      reservation(1L, "000-0000-0000", "홍길동", 3),
      reservation(2L, "000-1234-0000", "이순신", 2),
      reservation(3L, "000-0000-5678", "세종대왕", 1)
    ), PageRequest.of(page, size, Sort.by("RESERVATION_ID").descending()), 3);
  }

  public static List<ReservationResponseDto.ReservationInfoDto> reservationInfoDtoList(List<Reservation> reservationList) {
    return reservationList.stream()
      .map(ReservationStubData::reservationInfoDto)
      .collect(Collectors.toList());
  }

  public static ReservationRequestDto.UpdateReservationDto updateReservationDto() {
    return ReservationRequestDto.UpdateReservationDto.builder()
      .businessId(1L)
      .reservationId(1L)
      .name("홍길동")
      .phone("000-0000-0000")
      .count(8)
      .build();
  }
}
