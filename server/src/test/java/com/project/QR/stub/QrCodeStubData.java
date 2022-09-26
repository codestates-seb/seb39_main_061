package com.project.QR.stub;

import com.project.QR.business.entity.Business;
import com.project.QR.member.entity.Member;
import com.project.QR.qrcode.dto.QrCodeRequestDto;
import com.project.QR.qrcode.dto.QrCodeResponseDto;
import com.project.QR.qrcode.entity.QrCode;
import com.project.QR.qrcode.entity.QrType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QrCodeStubData {
  private static final Member member = MemberStubData.member();
  private static final Business business = BusinessStubData.business();

  public static QrCodeRequestDto.CreateQrCodeDto createQrCodeDto(long businessId) throws ParseException {
    return QrCodeRequestDto.CreateQrCodeDto.builder()
      .businessId(businessId)
      .memberId(member.getMemberId())
      .qrType("reservation")
      .target("target")
      .dueDate(LocalDateTime.of(2022, 9, 22, 14, 23))
      .build();
  }

  public static QrCode qrCode() {
    QrCode qrCode = new QrCode();
    qrCode.setQrCodeId(1L);
    qrCode.setTarget("target");
    qrCode.setReservations(new ArrayList<>());
    qrCode.setKeep(new ArrayList<>());
    qrCode.setQrType(QrType.RESERVATION);
    qrCode.setDueDate(LocalDateTime.of(2022, 9, 22, 14, 23));
    qrCode.setBusiness(business);
    return qrCode;
  }

  public static QrCode qrCode(long qrCodeId) {
    QrCode qrCode = new QrCode();
    qrCode.setQrCodeId(qrCodeId);
    qrCode.setTarget("target");
    qrCode.setReservations(new ArrayList<>());
    qrCode.setKeep(new ArrayList<>());
    qrCode.setQrType(QrType.RESERVATION);
    qrCode.setDueDate(LocalDateTime.of(2022, 9, 22, 14, 23));
    qrCode.setBusiness(business);
    return qrCode;
  }

  public static QrCodeResponseDto.QrCodeInfoDto createQrCodeInfoDto(QrCode qrCode) {
    return QrCodeResponseDto.QrCodeInfoDto.builder()
      .qrCodeId(qrCode.getQrCodeId())
      .qrCodeImg(qrCode.getQrCodeImg())
      .target(qrCode.getTarget())
      .qrType(QrType.RESERVATION)
      .build();
  }

  public static QrCodeRequestDto.UpdateQrCodeDto updateQrCodeDto() {
    QrCode qrCode = updatedQrCode();
    return QrCodeRequestDto.UpdateQrCodeDto.builder()
      .businessId(business.getBusinessId())
      .qrCodeId(qrCode.getQrCodeId())
      .dueDate(LocalDateTime.of(2022, 9, 30, 14, 23))
      .memberId(member.getMemberId())
      .qrType("reservation")
      .target(qrCode.getTarget())
      .build();
  }

  public static QrCode updatedQrCode() {
    QrCode qrCode = new QrCode();
    qrCode.setQrCodeId(1L);
    qrCode.setTarget("update target");
    qrCode.setReservations(new ArrayList<>());
    qrCode.setKeep(new ArrayList<>());
    qrCode.setQrCodeImg("qr-code-img.png");
    qrCode.setQrType(QrType.RESERVATION);
    qrCode.setDueDate(LocalDateTime.of(2022, 9, 30, 14, 23));
    qrCode.setBusiness(business);
    return qrCode;
  }

  public static QrCode getQrCode() {
    QrCode qrCode = new QrCode();
    qrCode.setQrCodeId(1L);
    qrCode.setTarget("target");
    qrCode.setReservations(ReservationStubData.reservationList());
    qrCode.setKeep(new ArrayList<>());
    qrCode.setQrType(QrType.RESERVATION);
    qrCode.setDueDate(LocalDateTime.of(2022, 9, 22, 14, 23));
    qrCode.setBusiness(business);
    return qrCode;
  }

  public static QrCodeResponseDto.QrCodeInfoDto getQrCodeInfoDto(QrCode qrCode) {
    return QrCodeResponseDto.QrCodeInfoDto.builder()
      .qrCodeId(qrCode.getQrCodeId())
      .qrCodeImg(qrCode.getQrCodeImg())
      .target(qrCode.getTarget())
      .qrType(QrType.RESERVATION)
      .build();
  }

  public static Page<QrCode> getQrCodePage(int page, int size) {
    return new PageImpl<>(List.of(
      updatedQrCode(),
      qrCode(2L),
      qrCode(3L)
    ), PageRequest.of(page, size, Sort.by("QR_CODE_ID").descending()), 3);
  }

  public static QrCodeResponseDto.ShortQrCodeInfoDto shortQrCodeInfoDto(QrCode qrCode) {
    return QrCodeResponseDto.ShortQrCodeInfoDto.builder()
      .qrCodeId(qrCode.getQrCodeId())
      .qrCodeImg(qrCode.getQrCodeImg())
      .qrType(qrCode.getQrType())
      .target(qrCode.getTarget())
      .build();
  }

  public static List<QrCodeResponseDto.ShortQrCodeInfoDto> getQrCodeInfoDtoList(List<QrCode> qrCodeList) {
    return qrCodeList.stream()
      .map(QrCodeStubData::shortQrCodeInfoDto)
      .collect(Collectors.toList());
  }
}
